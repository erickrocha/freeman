package com.erocha.freeman.time.usecases;

import com.erocha.freeman.time.gateways.AppointmentGateway;
import org.springframework.stereotype.Service;

@Service
public class DeleteAppointment {


    private AppointmentGateway gateway;

    public DeleteAppointment(AppointmentGateway gateway) {
        this.gateway = gateway;
    }

    public void execute(String id) {
        gateway.delete(id);
    }
}
