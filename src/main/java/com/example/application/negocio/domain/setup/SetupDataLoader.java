package com.example.application.negocio.domain.setup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.application.negocio.domain.entities.seguridad.Permission;
import com.example.application.negocio.domain.entities.seguridad.Role;
import com.example.application.negocio.domain.entities.seguridad.User;
import com.example.application.negocio.domain.repository.security.PermissionRepository;
import com.example.application.negocio.domain.repository.security.RoleRepository;
import com.example.application.negocio.domain.repository.security.UserRepository;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private ApplicationContext ctx;
	  
	private boolean alreadySetup = false;
	

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PermissionRepository privilegeRepository;

	
/*	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RolRepository RolRepository;

	@Autowired
	private PermisoRepository PermisoRepository;
*/
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	// API

	@Override
	@Transactional
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		if (alreadySetup) {
			return;
		}

		// == create initial Permisos
		final Permission readPermiso = createPermisoIfNotFound("READ_Permiso");
		final Permission writePermiso = createPermisoIfNotFound("WRITE_Permiso");
		final Permission passwordPermiso = createPermisoIfNotFound("CHANGE_PASSWORD_Permiso");
		final Permission adminWildCard = createPermisoIfNotFound("*");
		// == create initial Rols
		final List<Permission> adminPermisos = new ArrayList<>(
				Arrays.asList(readPermiso, writePermiso, passwordPermiso, adminWildCard));
		final List<Permission> userPermisos = new ArrayList<>(Arrays.asList(readPermiso, passwordPermiso));
		final Role adminRol = createRolIfNotFound("Rol_ADMIN", adminPermisos);
		createRolIfNotFound("Rol_USER", userPermisos);

		// == create initial user
		// createUserIfNotFound("test@test.com", "Test", "Test", "test", new
		// ArrayList<>(Arrays.asList(adminRol)));
		createUserIfNotFound("t", "t", "t", "t", new ArrayList<>(Arrays.asList(adminRol)));
	
		alreadySetup = true;
	}

	@Transactional
	Permission createPermisoIfNotFound(final String name) {
		Permission Permiso = privilegeRepository.findByDescripcion(name);
		if (Permiso == null) {
			Permiso = new Permission(name);
			Permiso = privilegeRepository.save(Permiso);
		}
		return Permiso;
	}

	@Transactional
	Role createRolIfNotFound(final String name, final List<Permission> Permisos) {
		Role Rol = roleRepository.findByNombre(name);
		if (Rol == null) {
			Rol = new Role();
		}
		Rol.setNombre(name);
		Rol.setPermisos(Permisos);
		Rol = roleRepository.save(Rol);
		return Rol;
	}

	@Transactional
	User createUserIfNotFound(final String email, final String firstName, final String lastName, final String password,
			final List<Role> Rols) {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			user = new User();
			user.setUsername(firstName);
			//user.setLastName(lastName);
			user.setPassword(passwordEncoder.encode(password));
			user.setEmail(email);
			user.setActivo(true);
		}
		user.setRoles(Rols);
		user = userRepository.save(user);
		return user;
	}
	
	
	
	
	
	
	
//	public static void main(String[] args) {
//		SetupDataLoader;
//	}

}