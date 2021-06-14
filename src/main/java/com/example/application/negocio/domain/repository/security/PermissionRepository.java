package com.example.application.negocio.domain.repository.security;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.negocio.domain.entities.seguridad.Permission;


public interface PermissionRepository extends JpaRepository<Permission, Long> {

	Permission findByDescripcion(String name);

	Permission save(Permission Permiso);

	@EntityGraph(attributePaths = { "roles" }, type = EntityGraphType.LOAD)
	List<Permission> findAll();

	@Override
	void delete(Permission Permiso);

}
