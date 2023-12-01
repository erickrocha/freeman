package com.erocha.freeman.app.gateways.repository;

import com.erocha.freeman.commons.domains.Province;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(ProvinceRepositoryImpl.ID)
public class ProvinceRepositoryImpl implements ProvinceRepository {

    public static final String ID = "ProvinceRepositoryImpl";

    @Override
    public List<Province> findAll() {
        return buiilData();
    }

    @Override
    public Province findByAcronym(String acronym) {
        return buiilData().stream().filter(province -> province.getAcronym().equals(acronym)).findFirst()
                .orElse(Province.builder().acronym("SP").name("Sao Paulo").build());
    }

    private List<Province> buiilData() {
        return List.of(Province.builder().acronym("AC").name("Acre").build(), Province.builder().acronym("AL").name("Alagoas").build(),
                Province.builder().acronym("AM").name("Amazonas").build(), Province.builder().acronym("AP").name("Amapá¡").build(),
                Province.builder().acronym("BA").name("Bahia").build(), Province.builder().acronym("CE").name("Ceará¡").build(),
                Province.builder().acronym("DF").name("Distrito Federal").build(), Province.builder().acronym("ES").name("Espírito Santo").build(),
                Province.builder().acronym("GO").name("Goiás").build(), Province.builder().acronym("MA").name("Maranhão").build(),
                Province.builder().acronym("MG").name("Minas Gerais").build(), Province.builder().acronym("MS").name("Mato Grosso do Sul").build(),
                Province.builder().acronym("MT").name("Mato Grosso").build(), Province.builder().acronym("PA").name("Pará").build(),
                Province.builder().acronym("PB").name("Paraíba").build(), Province.builder().acronym("PE").name("Pernambuco").build(),
                Province.builder().acronym("PI").name("Piauí").build(), Province.builder().acronym("PR").name("Paraná").build(),
                Province.builder().acronym("RJ").name("Rio de Janeiro").build(), Province.builder().acronym("RN").name("Rio Grande do Norte").build(),
                Province.builder().acronym("RO").name("Rondônia").build(), Province.builder().acronym("RR").name("Roraima").build(),
                Province.builder().acronym("RS").name("Rio Grande do Sul").build(), Province.builder().acronym("SC").name("Santa Catarina").build(),
                Province.builder().acronym("SE").name("Sergipe").build(), Province.builder().acronym("SP").name("São Paulo").build(),
                Province.builder().acronym("TO").name("Tocantins").build());
    }
}
