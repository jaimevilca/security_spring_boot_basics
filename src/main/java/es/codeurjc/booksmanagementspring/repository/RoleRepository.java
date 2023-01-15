package es.codeurjc.booksmanagementspring.repository;

import java.util.Optional;


import es.codeurjc.booksmanagementspring.model.ERole;
import es.codeurjc.booksmanagementspring.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
