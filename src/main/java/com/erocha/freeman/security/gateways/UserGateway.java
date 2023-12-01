package com.erocha.freeman.security.gateways;

import com.erocha.freeman.security.domains.Profile;
import com.erocha.freeman.security.domains.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserGateway extends UserDetailsService {

    User persist(User user);

    void delete(String id);

    void delete(User user);

    List<User> findAllById(String id);

    List<User> findAllByProfile(Profile profile);

    Optional<User> findById(String id);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    List<User> query(Query query);

    Page<User> query(Query query, Pageable pageable);

    @Override
    default UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
