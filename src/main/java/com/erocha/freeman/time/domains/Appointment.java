package com.erocha.freeman.time.domains;

import com.erocha.freeman.commons.utils.UUIDGenerator;
import com.erocha.freeman.management.domains.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Builder.Default
    private String id = UUIDGenerator.generate();
    private String userId;
    private LocalDate date;
    private Project project;
    private Status status;
    private LocalDateTime begin;
    private LocalDateTime end;
    private String notes;

    public Integer getHour() {
        if (begin == null) {
            return 0;
        }
        if (end == null) {
            end = LocalDateTime.now();
        }
        Integer minutes = Math.toIntExact(ChronoUnit.MINUTES.between(begin, end));
        return minutes / 60;
    }

    public Integer getMinute() {
        if (begin == null) {
            return 0;
        }
        if (end == null) {
            end = LocalDateTime.now();
        }
        Integer minutes = Math.toIntExact(ChronoUnit.MINUTES.between(begin, end));
        return minutes % 60;
    }

    public Integer getAmountInMinutes() {
        if (begin == null) {
            return 0;
        }
        if (end == null) {
            end = LocalDateTime.now();
        }
        return Math.toIntExact(ChronoUnit.MINUTES.between(begin, end));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId);
    }
}
