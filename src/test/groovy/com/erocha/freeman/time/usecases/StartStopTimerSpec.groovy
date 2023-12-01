package com.erocha.freeman.time.usecases

import com.erocha.freeman.commons.utils.UUIDGenerator
import com.erocha.freeman.management.domains.Project
import com.erocha.freeman.time.domains.Appointment
import com.erocha.freeman.time.domains.Status
import com.erocha.freeman.time.gateways.AppointmentGateway
import com.erocha.freeman.time.http.json.InputEntryTO
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

class StartStopTimerSpec extends Specification {

    AppointmentGateway appointmentGateway = Mock(AppointmentGateway.class)

    StartStopTimer startStopTimer = new StartStopTimer(appointmentGateway)

    def "When i try to start a timer "() {
        given: "I have a valid input entry"
        InputEntryTO inputEntryTO = new InputEntryTO(userId: UUIDGenerator.generate(), date: LocalDate.now(), hour: 3, minute: 30, project: getProject())
        appointmentGateway.persist(_ as Appointment) >> { Appointment appointment ->
            return appointment
        }
        when: " I call the execute method"
        Appointment appointment = startStopTimer.execute(inputEntryTO)
        then: "Just a new Appointment with status started"
        assert appointment.id != null
        assert appointment.status == Status.STARTED
        assert appointment.hour == 3
        assert appointment.minute == 30
    }

    def "When i try to start already started timer "() {
        given: "I have a valid input entry"
        InputEntryTO inputEntryTO = new InputEntryTO(id: "1", userId: UUIDGenerator.generate(), date: LocalDate.now(), hour: 3, minute: 30, project: getProject(), status: Status.STARTED)
        appointmentGateway.persist(_ as Appointment) >> { Appointment appointment ->
            return appointment
        }
        when: " I call the execute method"
        Appointment appointment = startStopTimer.execute(inputEntryTO)
        then: "Just a new Appointment with status started"
        assert appointment.id != null
        assert appointment.status == Status.PAUSED
        assert appointment.hour == 0
        assert appointment.minute == 0
    }

    def "When I try to start a paused timer "() {
        given: "I have a valid input entry"
        InputEntryTO inputEntryTO = new InputEntryTO(id: "1", userId: UUIDGenerator.generate(), date: LocalDate.now(),
                hour: 3, minute: 30, project: getProject(), status: Status.PAUSED, end: LocalDateTime.now())
        appointmentGateway.persist(_ as Appointment) >> { Appointment appointment ->
            return appointment
        }
        when: " I call the execute method"
        Appointment appointment = startStopTimer.execute(inputEntryTO)
        then: "Just a new Appointment with status started"
        assert appointment.id != null
        assert appointment.status == Status.STARTED
        assert appointment.hour == 0
        assert appointment.minute == 0
    }


    Project getProject() {
        return Project.builder().name("Project 1").code("P-1").phases(Set.of("Phase 1")).build()
    }
}
