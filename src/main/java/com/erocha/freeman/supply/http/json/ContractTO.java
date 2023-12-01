package com.erocha.freeman.supply.http.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractTO {

    @Builder.Default
    private LocalDate dateRegister = LocalDate.now();
    private LocalDate startDate;
    private LocalDate endDate;
    private Long valuePerHourInCents;
    private String number;
}
