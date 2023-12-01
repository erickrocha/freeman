package com.erocha.freeman.time.usecases

import com.erocha.freeman.app.gateways.MenuGateway
import com.erocha.freeman.app.gateways.impl.MenuGatewayImpl
import com.erocha.freeman.app.gateways.repository.MenuRepository
import com.erocha.freeman.app.gateways.repository.MenuRepositoryImpl
import com.erocha.freeman.commons.utils.DateTools
import com.erocha.freeman.security.domains.User
import com.erocha.freeman.security.gateways.UserGateway
import com.erocha.freeman.security.usecases.GetUser
import com.erocha.freeman.time.domains.Appointment
import com.erocha.freeman.time.gateways.AppointmentGateway
import com.erocha.freeman.time.http.json.Dashboard
import org.springframework.data.mongodb.core.query.Query
import spock.lang.Specification

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.util.stream.Stream

class GetDashboardSpec extends Specification {

    UserGateway userGateway = Mock(UserGateway.class)
    MenuRepository menuRepository = new MenuRepositoryImpl()
    MenuGateway menuGateway = new MenuGatewayImpl(menuRepository)
    GetUser getUser = new GetUser(userGateway)

    AppointmentGateway gateway = Mock(AppointmentGateway.class)
    GetDashBoard getDashBoard = new GetDashBoard(gateway, getUser)

    def "When I try to get a time dashboard without start date and end date "() {
        String email = "John.smith@mail.com.br"
        given: "I have a valid username"
        userGateway.findByEmail(_ as String) >> {
            return Optional.of(User.builder().email(email).name("John Smith").build());
        }
        gateway.query(_ as Query) >> {
            return Stream.of(Appointment.builder().date(LocalDate.now()).build())
        }
        when: "I call the execute method"
        Dashboard dashboard = getDashBoard.execute(email, null, null)
        then: "A dashboard should be returned for de current week"
        assert dashboard != null
        assert dashboard.startDate == DateTools.findFirstDayOfWeek(LocalDate.now(), DayOfWeek.SUNDAY)
        assert dashboard.endDate == DateTools.findLastDayOfWeek(LocalDate.now(), DayOfWeek.SATURDAY)
    }

    def "When I try to get a time dashboard with start date"() {
        String email = "John.smith@mail.com.br"
        LocalDate fixedDate = LocalDate.of(2021, Month.APRIL, 8)
        given: "I have a valid username"
        userGateway.findByEmail(_ as String) >> {
            return Optional.of(User.builder().email(email).name("John Smith").build())
        }
        gateway.query(_ as Query) >> {
            return Stream.of(Appointment.builder().date(fixedDate).build())
        }
        when: "I call the execute method"
        Dashboard dashboard = getDashBoard.execute(email, fixedDate, null)
        then: "A dashboard should be returned for the start date and end date equals the last day the current week"
        assert dashboard != null
        assert dashboard.startDate == DateTools.findFirstDayOfWeek(fixedDate, DayOfWeek.SUNDAY)
        assert dashboard.endDate == DateTools.findLastDayOfWeek(LocalDate.now(), DayOfWeek.SATURDAY)
    }

    def "When I try to get a time dashboard with start date and an end date"() {
        String email = "John.smith@mail.com.br"
        LocalDate startDate = LocalDate.of(2021, Month.APRIL, 8)
        LocalDate endDate = LocalDate.of(2021, Month.APRIL, 25)
        given: "I have a valid username"
        userGateway.findByEmail(_ as String) >> {
            return Optional.of(User.builder().email(email).name("John Smith").build())
        }
        gateway.query(_ as Query) >> {
            return Stream.of(Appointment.builder().date(startDate).build(), Appointment.builder().date(endDate).build())
        }
        when: "I call the execute method"
        Dashboard dashboard = getDashBoard.execute(email, startDate, endDate)
        then: "A dashboard should be returned with start date equals de sunday before start date and end date equals de saturday after end date"
        assert dashboard != null
        assert dashboard.startDate == DateTools.findFirstDayOfWeek(startDate, DayOfWeek.SUNDAY)
        assert dashboard.endDate == DateTools.findLastDayOfWeek(endDate, DayOfWeek.SATURDAY)
    }
}
