package com.erocha.freeman.app.gateways.repository;

import com.erocha.freeman.app.domains.Menu;
import com.erocha.freeman.security.domains.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class MenuRepositoryImpl implements MenuRepository {

    @Override
    public List<Menu> findAll() {
        return buildData();
    }

    private List<Menu> buildData() {
        return List.of(
                Menu.builder()
                        .label("Dashboard")
                        .order(1)
                        .route("/dashboard")
                        .roles(Set.of(Profile.DEVELOPER))
                        .name("DASHBOARD")
                        .build(),
                Menu.builder()
                        .label("Reports")
                        .name("REPORTS")
                        .roles(Set.of(Profile.values()))
                        .child(Menu.builder()
                                .label("Time")
                                .route("/reports/time")
                                .name("TIME")
                                .roles(Set.of(Profile.values()))
                                .build())
                        .child(Menu.builder()
                                .label("Project")
                                .route("/reports/projects")
                                .name("PROJECT_REPORT")
                                .roles(Set.of(Profile.PROJECT_MANAGER, Profile.FINANCIAL, Profile.SYSADMIN))
                                .build())
                        .build(),
                Menu.builder()
                        .label("Management")
                        .roles(Set.of(Profile.PROJECT_MANAGER, Profile.FINANCIAL, Profile.SYSADMIN))
                        .name("MANAGEMENT")
                        .child(Menu.builder()
                                .label("Dashboard")
                                .route("/management/dashboard")
                                .roles(Set.of(Profile.PROJECT_MANAGER, Profile.SYSADMIN))
                                .name("MANAGEMENT_DASHBOARD")
                                .build())
                        .child(Menu.builder()
                                .label("Entries")
                                .route("/management/entries")
                                .roles(Set.of(Profile.PROJECT_MANAGER, Profile.SYSADMIN))
                                .name("ENTRIES")
                                .build())
                        .child(Menu.builder()
                                .label("Projects")
                                .route("/management/project")
                                .roles(Set.of(Profile.FINANCIAL, Profile.SYSADMIN))
                                .name("PROJECTS")
                                .build())
                        .child(Menu.builder()
                                .label("Teams")
                                .route("/management/team")
                                .roles(Set.of(Profile.FINANCIAL, Profile.PROJECT_MANAGER))
                                .name("PROJECTS")
                                .build())
                        .build(),
                Menu.builder()
                        .label("Register")
                        .roles(Set.of(Profile.FINANCIAL, Profile.SYSADMIN, Profile.PROJECT_MANAGER))
                        .name("REGISTER")
                        .child(Menu.builder()
                                .label("Supplier")
                                .route("/supplier")
                                .name("SUPPLIER")
                                .roles(Set.of(Profile.FINANCIAL, Profile.SYSADMIN, Profile.PROJECT_MANAGER))
                                .build())
                        .child(Menu.builder()
                                .label("Customer")
                                .route("/customer")
                                .name("CUSTOMER")
                                .roles(Set.of(Profile.FINANCIAL, Profile.SYSADMIN, Profile.PROJECT_MANAGER))
                                .build())
                        .build(),
                Menu.builder()
                        .label("Financial")
                        .roles(Set.of(Profile.FINANCIAL, Profile.SYSADMIN))
                        .name("FINANCIAL")
                        .child(Menu.builder()
                                .label("Order")
                                .route("/financial/order")
                                .name("ORDER")
                                .roles(Set.of(Profile.FINANCIAL, Profile.SYSADMIN))
                                .build())
                        .child(Menu.builder()
                                .label("Payment")
                                .route("/financial/payment")
                                .name("PAYMENT")
                                .roles(Set.of(Profile.FINANCIAL, Profile.SYSADMIN))
                                .build())
                        .build(),
                Menu.builder()
                        .label("Security")
                        .name("SECURITY")
                        .roles(Set.of(Profile.values()))
                        .child(Menu.builder()
                                .label("Account")
                                .route("/security/account")
                                .name("ACCOUNT")
                                .roles(Set.of(Profile.values()))
                                .build())
                        .child(Menu.builder()
                                .label("Settings")
                                .route("/security/settings")
                                .name("SETTINGS")
                                .roles(Set.of(Profile.values()))
                                .build())
                        .child(Menu.builder()
                                .label("Users")
                                .route("/security/users")
                                .name("USERS")
                                .roles(Set.of(Profile.SYSADMIN))
                                .build())
                        .build());
    }
}
