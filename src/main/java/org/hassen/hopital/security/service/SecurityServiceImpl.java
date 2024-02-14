package org.hassen.hopital.security.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hassen.hopital.security.entities.AppRole;
import org.hassen.hopital.security.entities.AppUsers;
import org.hassen.hopital.security.repositories.AppRolesRepository;
import org.hassen.hopital.security.repositories.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class SecurityServiceImpl implements SecurityService{
    private AppUserRepository appUserRepository;
    private AppRolesRepository appRolesRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUsers addNewUser(String userName, String password, String rePassword) {
        AppUsers user=appUserRepository.findByUserName(userName);
        if(user!=null) throw new RuntimeException("USER NAME ALREADY EXIST");
        if(!(password.equals(rePassword))) throw new RuntimeException("password dosen't match");
        AppUsers newUser= new AppUsers();
        newUser.setUserID(UUID.randomUUID().toString());
        newUser.setUserName(userName);
        String hashedPwd= passwordEncoder.encode(password);
        newUser.setPassword(hashedPwd);
        newUser.setActive(true);
        appUserRepository.save(newUser);
        return newUser;
    }

    @Override
    public AppRole addNewRoles(String roleName, String description) {
        AppRole appRole=appRolesRepository.findByRoleName(roleName);
        if(appRole!=null) throw new RuntimeException("ROLE ALREADY EXIST");
        AppRole newRole=new AppRole();
        newRole.setRoleName(roleName);
        newRole.setDescription(description);
        appRolesRepository.save(newRole);
        return newRole;
    }

        
    @Override
    public void addRoleToUser(String userName, String roleName) {
        AppUsers appUser=appUserRepository.findByUserName(userName);
        if(appUser==null) throw new RuntimeException("user not found");
        AppRole appRole=appRolesRepository.findByRoleName(roleName);
        if(appRole==null) throw new RuntimeException("role not found");
        appUser.getAppRoles().add(appRole);
        appRole.getAppUsers().add(appUser);

    }

    @Override
    public void removeRoleFromUser(String userName, String roleName) {
        AppUsers appUser=appUserRepository.findByUserName(userName);
        if(appUser==null) throw new RuntimeException("user not found");
        AppRole appRole=appRolesRepository.findByRoleName(roleName);
        if(appRole==null) throw new RuntimeException("role not found");
        appUser.getAppRoles().remove(appRole);
    }

    @Override
    public AppUsers LoadUserByUserName(String userName) {
        return appUserRepository.findByUserName(userName);

    }

    }

