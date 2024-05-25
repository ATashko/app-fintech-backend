package com.msuser.msuser.service.impl;

import com.msuser.msuser.dto.UserRegistrationDTO;
import com.msuser.msuser.service.IUserService;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.msuser.msuser.util.keycloakProvider.getRealmResource;
import static com.msuser.msuser.util.keycloakProvider.getUserResource;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    /*
     * Method for list user by its own username
     * @return List<UserRepresentation>
     * */
    @Override
    public List<UserRepresentation> findAllUsers() {
        return getRealmResource().users().list();
    }


    /*
     * Method for create a new user in keycloak
     * @return String
     * */
    @Override
    public List<UserRepresentation> searchUserByUsername(String username) {
        return getRealmResource().users().searchByUsername(username, true);
    }


    /*
     * Method for create a new user in keycloak
     * @return String
     * */
    @Override
    public String createUser(@NonNull UserRegistrationDTO userRegistrationDTO) {
        int status = 0;

        UsersResource userResource = getUserResource();

        UserRepresentation userRepresentation = new UserRepresentation();

        userRepresentation.setFirstName(userRegistrationDTO.firstName());
        userRepresentation.setLastName(userRegistrationDTO.lastName());
        userRepresentation.setEmail(userRegistrationDTO.email());
        userRepresentation.setUsername(userRegistrationDTO.username());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(true);

        Response response = userResource.create(userRepresentation);
        status = response.getStatus();

        if (status == 201) {
            String path = response.getLocation().getPath();
            String userId = path.substring(path.lastIndexOf("/") + 1);

            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setTemporary(false);
            credentialRepresentation.setType(OAuth2Constants.PASSWORD);
            credentialRepresentation.setValue(userRegistrationDTO.pass());

            userResource.get(userId).resetPassword(credentialRepresentation);

            RealmResource realmResource = getRealmResource();
            List<RoleRepresentation> roleRepresentations = null;

            if (userRegistrationDTO.roles() == null || userRegistrationDTO.roles().isEmpty()) {
                roleRepresentations = List.of(realmResource.roles().get("user-role-realm").toRepresentation());
            } else {//todo: actualizar roles para que permita hacer consultas por defecto.
                roleRepresentations = realmResource.roles().list()
                        .stream().filter(role -> userRegistrationDTO.roles()
                                .stream()
                                .anyMatch(roleName -> roleName.equalsIgnoreCase(role.getName())))
                        .toList();
            }

            realmResource.users()
                    .get(userId)
                    .roles()
                    .realmLevel()
                    .add(roleRepresentations);
        } else if (status == 409) {
            log.error("User already exist");
            return "User already exist";
        } else {
            log.error("Error creating user");
            return "Error creating user";
        }
        return null;
    }

    /*
     * Method for remove a user in keycloak
     * @return void
     * */
    @Override
    public void deleteUser(String userId) {
        getUserResource()
                .get(userId)
                .remove();
    }

    /*
     * Method for update a user in keycloak
     * @return void
     * */
    @Override
    public void updateUser(String userId, UserRegistrationDTO userRegistrationDTO) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(OAuth2Constants.PASSWORD);
        credentialRepresentation.setValue(userRegistrationDTO.pass());

        UserRepresentation userRepresentation = new UserRepresentation();

        userRepresentation.setFirstName(userRegistrationDTO.firstName());
        userRepresentation.setLastName(userRegistrationDTO.lastName());
        userRepresentation.setEmail(userRegistrationDTO.email());
        userRepresentation.setUsername(userRegistrationDTO.username());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(true);

        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));

        UserResource userResource = getUserResource().get(userId);
        userResource.update(userRepresentation);

    }

    public UserRepresentation authenticateUser(String username, String password) {
        UsersResource usersResource = getUserResource();
        List<UserRepresentation> users = usersResource.search(username);
        if (users.isEmpty()) {
            return null;
        }

        UserRepresentation user = users.get(0);


        return user;
    }
}
