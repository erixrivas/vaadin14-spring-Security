package com.example.application.negocio.domain.repository.security;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.negocio.domain.entities.seguridad.Role;
import com.example.application.negocio.domain.entities.seguridad.Permission;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByNombre(String name);

	// @EntityGraph(attributePaths = {"privileges"}, type = EntityGraphType.LOAD)
	// @EntityGraph(value = "Rol.users.privileges", type = EntityGraphType.LOAD)
	List<Role> findAll();

	Role save(Role Rol);

	// @EntityGraph(value = "Rol.users.privileges", type = EntityGraphType.LOAD)
	Optional<Permission> findPermisoByNombre(String name);

	@Override
	void delete(Role Rol);

}
