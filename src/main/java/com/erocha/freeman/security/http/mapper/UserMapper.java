package com.erocha.freeman.security.http.mapper;

import com.erocha.freeman.commons.mapper.Mapper;
import com.erocha.freeman.security.domains.Profile;
import com.erocha.freeman.security.domains.User;
import com.erocha.freeman.security.http.json.UserRequest;

public class UserMapper {

    private UserMapper() {

    }

    public static final Mapper<User, UserRequest> userRequestMapper = new Mapper<User, UserRequest>() {
        @Override
        public UserRequest convertTransferObject(User user) {
            return null;
        }

        @Override
        public User convertEntity(UserRequest userRequest) {
            return User.builder().id(userRequest.getId()).name(userRequest.getName()).email(userRequest.getEmail())
                    .profile(Profile.valueOf(userRequest.getProfile())).build();
        }
    };
}
