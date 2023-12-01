package com.erocha.freeman.hr.domains;

import com.erocha.freeman.commons.utils.UUIDGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {

    @Id
    @Builder.Default
    private String id = UUIDGenerator.generate();

    private boolean responsible;

    private Person person;
}
