package com.emeet.api.repository;

import java.util.Optional;

import com.emeet.api.models.ERole;
import com.emeet.api.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<com.emeet.api.models.Role, Long> {
  Optional<com.emeet.api.models.Role> findByName(ERole name);

}
