package com.erocha.freeman.security.http;

import com.erocha.freeman.commons.exceptions.ResourceNotFoundException;
import com.erocha.freeman.security.domains.User;
import com.erocha.freeman.security.http.json.UpdatePasswordRequest;
import com.erocha.freeman.security.http.json.UserParams;
import com.erocha.freeman.security.http.json.UserRequest;
import com.erocha.freeman.security.http.mapper.UserMapper;
import com.erocha.freeman.security.usecases.AddUser;
import com.erocha.freeman.security.usecases.GetUser;
import com.erocha.freeman.security.usecases.UpdatePassword;
import com.erocha.freeman.security.usecases.UpdateUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/security")
public class SecurityController {


    private GetUser getUser;

    private AddUser addUser;

    private UpdatePassword updatePassword;

    private UpdateUser updateUser;

    public SecurityController(GetUser getUser, AddUser addUser, UpdatePassword updatePassword, UpdateUser updateUser) {
        this.getUser = getUser;
        this.addUser = addUser;
        this.updatePassword = updatePassword;
        this.updateUser = updateUser;
    }

    @GetMapping("/users/logged")
    public User get(Authentication authentication) {
        String email = authentication.getName();
        return getUser.execute(email).orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping("/users/{id}")
    public User get(@PathVariable("id") String id) {
        return getUser.executeById(id);
    }

    @GetMapping("/users")
    public Page<User> get(UserParams params, Pageable pageable) {
        return getUser.execute(params, pageable);
    }

    @PostMapping("/users")
    public User add(@Validated @RequestBody UserRequest userRequest) {
        return addUser.execute(UserMapper.userRequestMapper.convertEntity(userRequest));
    }

    @PutMapping("/users/{id}")
    public User update(@PathVariable("id") String id, @RequestBody User user) {
        user.setId(id);
        return updateUser.execute(user);
    }

    @PostMapping("/users/updatePassword")
    public ResponseEntity<User> updatePassword(Authentication authentication, @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        User user = updatePassword.execute(authentication.getName(), updatePasswordRequest.getPassword());
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}
