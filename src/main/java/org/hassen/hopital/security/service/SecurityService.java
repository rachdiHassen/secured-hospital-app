package org.hassen.hopital.security.service;

import org.hassen.hopital.security.entities.AppRole;
import org.hassen.hopital.security.entities.AppUsers;

public interface SecurityService {
    AppUsers addNewUser(String userName, String password, String rePassword);
    AppRole addNewRoles(String roleName, String description);
    void addRoleToUser(String userName, String roleName);
    void removeRoleFromUser (String userName, String roleName);
    AppUsers LoadUserByUserName(String userName);

}
