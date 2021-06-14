package com.example.application.negocio.domain.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.negocio.domain.entities.seguridad.User;


public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);

	@Override
	void delete(User user);

}
