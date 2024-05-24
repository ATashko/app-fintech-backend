package com.msuser.msuser.service;

import com.msuser.msuser.dto.UserRegistrationDTO;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface IUserService {

    List<UserRepresentation> findAllUsers();
    List<UserRepresentation> searchUserByUsername(String username);
    String createUser(UserRegistrationDTO userDTO);

    UserRepresentation authenticateUser(String username, String password);
    void deleteUser(String userId);
    void updateUser(String userId,UserRegistrationDTO userDTO);
}
