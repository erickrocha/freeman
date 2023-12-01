package com.erocha.freeman.time.http;

import com.erocha.freeman.time.domains.Appointment;
import com.erocha.freeman.time.http.json.Dashboard;
import com.erocha.freeman.time.http.json.InputEntryTO;
import com.erocha.freeman.time.usecases.DeleteAppointment;
import com.erocha.freeman.time.usecases.FinishAppointment;
import com.erocha.freeman.time.usecases.GetAppointment;
import com.erocha.freeman.time.usecases.GetDashBoard;
import com.erocha.freeman.time.usecases.StartStopTimer;
import com.erocha.freeman.time.usecases.UpdateAppointment;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class TimeController {


    private GetDashBoard getDashBoard;

    private GetAppointment getAppointment;

    private StartStopTimer startStopTimer;

    private FinishAppointment finishAppointment;

    private UpdateAppointment updateAppointment;

    private DeleteAppointment deleteAppointment;

    public TimeController(GetDashBoard getDashBoard, GetAppointment getAppointment, StartStopTimer startStopTimer,
                          FinishAppointment finishAppointment, UpdateAppointment updateAppointment, DeleteAppointment deleteAppointment) {
        this.getDashBoard = getDashBoard;
        this.getAppointment = getAppointment;
        this.startStopTimer = startStopTimer;
        this.finishAppointment = finishAppointment;
        this.updateAppointment = updateAppointment;
        this.deleteAppointment = deleteAppointment;
    }

    @GetMapping()
    public Dashboard get(Authentication authentication, @RequestParam(name = "startDate", required = false) LocalDate startDate,
                         @RequestParam(name = "endDate", required = false) LocalDate endDate) {
        return getDashBoard.execute(authentication.getName(), startDate, endDate);
    }

    @GetMapping("/{userId}/all")
    public List<Appointment> getMonth(@PathVariable("userId") String userId) {
        return getAppointment.execute(userId);
    }

    @PostMapping(value = "/appointment/startStop", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Appointment startStopTimer(@RequestBody InputEntryTO entry) {
        return startStopTimer.execute(entry);
    }

    @PostMapping(value = "/appointment/finish", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Appointment appointmentFinish(@RequestBody InputEntryTO entry) {
        return finishAppointment.execute(entry);
    }

    @PutMapping("/appointment/{id}")
    public Appointment update(@PathVariable(name = "id") String id, @RequestBody InputEntryTO inputEntry) {
        inputEntry.setId(id);
        return updateAppointment.execute(inputEntry);
    }

    @DeleteMapping("/appointment/{id}")
    public void delete(@PathVariable(name = "id") String id) {
        deleteAppointment.execute(id);
    }
}
