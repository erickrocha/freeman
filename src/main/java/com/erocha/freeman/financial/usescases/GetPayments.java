package com.erocha.freeman.financial.usescases;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erocha.freeman.crm.gateways.CustomerGateway;
import com.erocha.freeman.crm.http.mapper.CustomerMapper;
import com.erocha.freeman.financial.http.json.Payment;
import com.erocha.freeman.financial.http.json.PaymentDashboard;
import com.erocha.freeman.financial.http.json.ProjectAmount;
import com.erocha.freeman.hr.domains.Person;
import com.erocha.freeman.hr.gateways.PersonGateway;
import com.erocha.freeman.management.gateways.ProjectGateway;
import com.erocha.freeman.time.domains.Appointment;
import com.erocha.freeman.time.domains.Status;
import com.erocha.freeman.time.gateways.AppointmentGateway;
import com.erocha.freeman.time.gateways.query.builder.AppointmentQueryBuilder;

@Service
public class GetPayments {

	
	private CustomerGateway customerGateway;

	private ProjectGateway projectGateway;

	private AppointmentGateway appointmentGateway;

	private PersonGateway personGateway;

	public GetPayments(CustomerGateway customerGateway, ProjectGateway projectGateway, AppointmentGateway appointmentGateway, PersonGateway personGateway) {
		this.customerGateway = customerGateway;
		this.projectGateway = projectGateway;
		this.appointmentGateway = appointmentGateway;
		this.personGateway = personGateway;
	}

	public PaymentDashboard execute(LocalDate startDate, LocalDate endDate) {
		PaymentDashboard.PaymentDashboardBuilder builder = PaymentDashboard.builder();
		builder.startDate(startDate);
		builder.endDate(endDate);
		builder.payments(getPayments(startDate, endDate));
		return builder.build();
	}

	private List<Payment> getPayments(LocalDate startDate, LocalDate endDate) {
		List<Payment> payments = new ArrayList<>();
		List<Person> people = personGateway.findAllWithoutSysAdmin();
		people.forEach(person -> {
			Payment.PaymentBuilder paymentBuilder = Payment.builder();

			projectGateway.findAll().forEach(project -> {
				ProjectAmount.ProjectAmountBuilder projectAmountBuilder = ProjectAmount.builder();
				projectAmountBuilder.projectId(project.getId());
				projectAmountBuilder.projectName(project.getName());

				int amountApproved = getAmountInMinutes(person.getId(), project.getId(), startDate, endDate, Status.APPROVED);
				projectAmountBuilder.amountApprovedInHour(amountApproved / 60);
				projectAmountBuilder.amountApprovedInMinutes(amountApproved % 60);

				int amountPending = getAmountInMinutes(person.getId(), project.getId(), startDate, endDate, Status.SAVED);
				projectAmountBuilder.amountPendingInHour(amountPending / 60);
				projectAmountBuilder.amountPendingInMinutes(amountPending % 60);
				if (amountApproved > 0 || amountPending > 0) {
					paymentBuilder.project(projectAmountBuilder.build());
				}
			});

			paymentBuilder.personId(person.getId());
			paymentBuilder.personName(person.getName());
			paymentBuilder.personSocialSecurity(person.getSocialSecurity());
			payments.add(paymentBuilder.build());
		});

		return payments;
	}

	private int getAmountInMinutes(String userId, String projectId, LocalDate startDate, LocalDate endDate, Status status) {
		Stream<Appointment> appointments = appointmentGateway.query(AppointmentQueryBuilder.queryByParams(startDate, endDate, userId, projectId, null, status));
		return appointments.mapToInt(Appointment::getAmountInMinutes).sum();
	}

}
