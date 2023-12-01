package com.erocha.freeman.config.bootsrap;

import com.erocha.freeman.security.domains.Profile;
import com.erocha.freeman.security.domains.User;
import com.erocha.freeman.security.usecases.AddUser;
import com.erocha.freeman.security.usecases.GetUser;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class DatabaseInitializer {


    private AddUser addUser;
    private GetUser getUser;

    public DatabaseInitializer(AddUser addUser, GetUser getUser) {
        this.addUser = addUser;
        this.getUser = getUser;
    }

    @PostConstruct
    public void init() {
        log.info("###############################################  Creating Sysadmin  #######################################################################");
        log.info("##########################################################################################################################################");

        Optional<User> optUser = getUser.execute("admin@freeman.com.br");
        if (optUser.isEmpty()) {
            User admin = User.builder().email("admin@freeman.com.br").password("162364").profile(Profile.SYSADMIN).name("Freeman System Admin").build();
            addUser.execute(admin);
        }

        log.info("##########################################################################################################################################");
        log.info("###############################################  SysAdmin created ########################################################################");
    }
}

