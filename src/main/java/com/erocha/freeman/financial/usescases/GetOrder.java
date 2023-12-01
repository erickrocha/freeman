package com.erocha.freeman.financial.usescases;

import com.erocha.freeman.crm.domains.Customer;
import com.erocha.freeman.crm.gateways.CustomerGateway;
import com.erocha.freeman.financial.http.json.Order;
import com.erocha.freeman.financial.http.json.OrderDashboard;
import com.erocha.freeman.financial.http.json.ProjectAmount;
import com.erocha.freeman.management.domains.Project;
import com.erocha.freeman.management.gateways.ProjectGateway;
import com.erocha.freeman.management.gateways.query.builder.ProjectQueryBuilder;
import com.erocha.freeman.time.domains.Appointment;
import com.erocha.freeman.time.domains.Status;
import com.erocha.freeman.time.gateways.AppointmentGateway;
import com.erocha.freeman.time.gateways.query.builder.AppointmentQueryBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class GetOrder {


    private CustomerGateway customerGateway;

    private ProjectGateway projectGateway;

    private AppointmentGateway appointmentGateway;

    public GetOrder(CustomerGateway customerGateway, ProjectGateway projectGateway, AppointmentGateway appointmentGateway) {
        this.customerGateway = customerGateway;
        this.projectGateway = projectGateway;
        this.appointmentGateway = appointmentGateway;
    }

    public OrderDashboard execute(LocalDate startDate, LocalDate endDate) {
        OrderDashboard.OrderDashboardBuilder builder = OrderDashboard.builder();
        builder.startDate(startDate);
        builder.endDate(endDate);
        builder.orders(getOrders(startDate, endDate));
        return builder.build();
    }

    private List<Order> getOrders(LocalDate startDate, LocalDate endDate) {
        List<Order> orders = new ArrayList<>();
        List<Customer> customers = customerGateway.findAll();
        customers.forEach(customer -> projectGateway.query(ProjectQueryBuilder.queryByOwner(customer.getId())).forEach(project -> {
            ProjectAmount.ProjectAmountBuilder projectAmountBuilder = ProjectAmount.builder();
            projectAmountBuilder.projectId(project.getId());
            projectAmountBuilder.projectName(project.getName());
            int amountApproved = getAmountInMinutes(project, startDate, endDate, Status.APPROVED);
            projectAmountBuilder.amountApprovedInHour(amountApproved / 60);
            projectAmountBuilder.amountApprovedInMinutes(amountApproved % 60);

            int amountPending = getAmountInMinutes(project, startDate, endDate, Status.SAVED);
            projectAmountBuilder.amountPendingInHour(amountPending / 60);
            projectAmountBuilder.amountPendingInMinutes(amountPending % 60);

            int hundred = (amountPending + amountApproved);
            if (hundred > 0) {
                projectAmountBuilder.pendingPercentage((amountPending * 100) / hundred);
                projectAmountBuilder.approvedPercentage((amountApproved * 100) / hundred);
                Order.OrderBuilder orderTOBuilder = Order.builder();
                orderTOBuilder.project(projectAmountBuilder.build());
                orderTOBuilder.customerId(customer.getId()).customerNumber(customer.getLegalNumber());
                Optional.ofNullable(customer.getBusinessName()).ifPresentOrElse(businessName -> orderTOBuilder.customerName(businessName),
                        () -> orderTOBuilder.customerName(customer.getLegalName()));
                orders.add(orderTOBuilder.build());
            }
        }));
        return orders;
    }

    private int getAmountInMinutes(Project project, LocalDate startDate, LocalDate endDate, Status status) {
        Stream<Appointment> appointments = appointmentGateway
                .query(AppointmentQueryBuilder.queryByProjectAndDateBetweenAndStatus(project.getId(), startDate, endDate, status));
        return appointments.mapToInt(Appointment::getAmountInMinutes).sum();
    }
}
