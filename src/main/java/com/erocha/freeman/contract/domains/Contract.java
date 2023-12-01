package com.erocha.freeman.contract.domains;

import com.erocha.freeman.commons.utils.UUIDGenerator;
import com.erocha.freeman.supply.domains.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contract {

    @Id
    @Builder.Default
    private String id = UUIDGenerator.generate();
    private String number;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate validity;
    private Supplier supplier;
}
