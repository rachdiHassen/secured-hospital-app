package org.hassen.hopital.security.repositories;

import org.hassen.hopital.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRolesRepository extends JpaRepository<AppRole,Long> {
    AppRole findByRoleName(String roleName);
}
