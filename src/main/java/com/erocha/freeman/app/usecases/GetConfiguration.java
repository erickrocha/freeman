package com.erocha.freeman.app.usecases;

import com.erocha.freeman.app.domains.Menu;
import com.erocha.freeman.app.gateways.MenuGateway;
import com.erocha.freeman.app.gateways.ProvinceGateway;
import com.erocha.freeman.app.http.json.Config;
import com.erocha.freeman.app.http.mapper.ProfileMapper;
import com.erocha.freeman.commons.exceptions.ResourceNotFoundException;
import com.erocha.freeman.commons.mapper.Mapper;
import com.erocha.freeman.crm.domains.Customer;
import com.erocha.freeman.crm.http.json.CustomerParams;
import com.erocha.freeman.crm.usecases.GetCustomer;
import com.erocha.freeman.hr.domains.Person;
import com.erocha.freeman.hr.gateways.PersonGateway;
import com.erocha.freeman.hr.http.mapper.PersonMapper;
import com.erocha.freeman.management.gateways.ProjectGateway;
import com.erocha.freeman.management.http.json.MemberTO;
import com.erocha.freeman.management.http.json.OwnerTO;
import com.erocha.freeman.management.http.json.ProjectManagerTO;
import com.erocha.freeman.management.http.mapper.ProjectMapper;
import com.erocha.freeman.security.domains.Profile;
import com.erocha.freeman.security.domains.User;
import com.erocha.freeman.security.gateways.UserGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetConfiguration {


    private ProvinceGateway provinceGateway;

    private UserGateway userGateway;

    private MenuGateway menuGateway;

    private ProjectGateway projectGateway;

    private PersonGateway personGateway;

    private ProjectMapper projectMapper;

    private ProfileMapper profileMapper;

    private PersonMapper personMapper;

    private GetCustomer getCustomer;

    public GetConfiguration(ProvinceGateway provinceGateway, UserGateway userGateway, MenuGateway menuGateway,
                            ProjectGateway projectGateway, PersonGateway personGateway, ProjectMapper projectMapper,
                            ProfileMapper profileMapper, PersonMapper personMapper, GetCustomer getCustomer) {
        this.provinceGateway = provinceGateway;
        this.userGateway = userGateway;
        this.menuGateway = menuGateway;
        this.projectGateway = projectGateway;
        this.personGateway = personGateway;
        this.projectMapper = projectMapper;
        this.profileMapper = profileMapper;
        this.personMapper = personMapper;
        this.getCustomer = getCustomer;
    }

    public Config execute(String email) {
        User me = userGateway.findByEmail(email).orElseThrow(ResourceNotFoundException::new);
        Person person = personGateway.findById(me.getId()).orElseThrow(ResourceNotFoundException::new);
        return Config.builder().user(me).person(personMapper.convertTransferObject(person)).provinces(provinceGateway.findAll())
                .menus(menuGateway.findAll().stream().filter(menu -> menu.getRoles().contains(me.getProfile())).map(menu -> mapMenu(menu, me))
                        .toList()).myProjects(projectMapper.convertTransferObject(projectGateway.findAll()))
                .managers(getProjectManagerMapper().convertTransferObject(userGateway.findAllByProfile(Profile.PROJECT_MANAGER)))
                .profiles(profileMapper.convertTransferObject(Profile.getAll())).players(getMembers()).techLeads(getMembers())
                .owners(getCustomerMapper().convertTransferObject(getCustomer.execute(new CustomerParams()))).build();

    }

    private Menu mapMenu(Menu menu, User me) {
        List<Menu> menus = menu.getChildren().stream().filter(menu1 -> menu1.getRoles().contains(me.getProfile())).toList();
        menu.setChildren(menus);
        return menu;
    }

    private List<MemberTO> getMembers() {
        return personGateway.findAllWithoutSysAdmin().stream().map(this::buildMember).toList();
    }

    private MemberTO buildMember(Person person) {
        return MemberTO.builder().id(person.getId()).name(person.getName()).profile(person.getProfile().getLabel()).build();
    }

    private Mapper<User, ProjectManagerTO> getProjectManagerMapper() {
        return new Mapper<>() {
            @Override
            public ProjectManagerTO convertTransferObject(User user) {
                return ProjectManagerTO.builder().id(user.getId()).name(user.getName()).build();
            }

            @Override
            public User convertEntity(ProjectManagerTO projectManagerTO) {
                return null;
            }
        };
    }

    private Mapper<Customer, OwnerTO> getCustomerMapper() {
        return new Mapper<>() {
            @Override
            public OwnerTO convertTransferObject(Customer customer) {
                OwnerTO ownerTO = OwnerTO.builder().id(customer.getId()).build();
                Optional.ofNullable(customer.getBusinessName())
                        .ifPresentOrElse(ownerTO::setName, () -> ownerTO.setName(customer.getLegalName()));
                return ownerTO;
            }

            @Override
            public Customer convertEntity(OwnerTO customerTO) {
                return null;
            }
        };
    }
}
