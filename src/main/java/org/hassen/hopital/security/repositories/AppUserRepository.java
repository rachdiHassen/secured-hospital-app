package org.hassen.hopital.security.repositories;

import org.hassen.hopital.security.entities.AppUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUsers,String> {
    AppUsers findByUserName(String userName);
}
