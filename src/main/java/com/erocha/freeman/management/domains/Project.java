package com.erocha.freeman.management.domains;

import com.erocha.freeman.commons.utils.UUIDGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project {

    @Id
    @Builder.Default
    private String id = UUIDGenerator.generate();
    private String name;
    private String code;
    private ProjectManager manager;
    private Owner owner;
    private byte[] image;

    private Set<String> phases;
    @Builder.Default
    private boolean active = true;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id) && Objects.equals(code, project.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code);
    }
}
