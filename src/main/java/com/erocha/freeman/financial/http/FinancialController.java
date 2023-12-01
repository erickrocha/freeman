package com.erocha.freeman.financial.http;

import com.erocha.freeman.financial.http.json.FinancialParams;
import com.erocha.freeman.financial.http.json.OrderDashboard;
import com.erocha.freeman.financial.http.json.PaymentDashboard;
import com.erocha.freeman.financial.usescases.GetOrder;
import com.erocha.freeman.financial.usescases.GetPayments;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/financial")
public class FinancialController {

    private GetOrder getOrder;

    private GetPayments getPayments;

    public FinancialController(GetOrder getOrder, GetPayments getPayments) {
        this.getOrder = getOrder;
        this.getPayments = getPayments;
    }

    @GetMapping("/orders")
    public OrderDashboard getOrders(FinancialParams params) {
        return getOrder.execute(params.getStartDate(), params.getEndDate());
    }

    @GetMapping("/payments")
    public PaymentDashboard getPayments(FinancialParams params) {
        return getPayments.execute(params.getStartDate(), params.getEndDate());
    }
}
