package com.erocha.freeman.hr.http;

import com.erocha.freeman.hr.http.json.PersonTO;
import com.erocha.freeman.hr.http.mapper.PersonMapper;
import com.erocha.freeman.hr.usecases.UpdatePerson;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hr")
public class HumanResourcesController {

    private UpdatePerson updatePerson;

    private PersonMapper personMapper;

    public HumanResourcesController(UpdatePerson updatePerson, PersonMapper personMapper) {
        this.updatePerson = updatePerson;
        this.personMapper = personMapper;
    }

    @PutMapping("/person/{id}")
    public PersonTO updatePerson(@PathVariable("id") String id, @RequestBody PersonTO person) {
        return personMapper.convertTransferObject(updatePerson.execute(personMapper.convertEntity(person)));
    }
}
