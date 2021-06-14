package com.example.application.negocio.seguridad;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.server.ServletHelper.RequestType;
import com.vaadin.flow.shared.ApplicationConstants;









public final class SecurityUtils {

	private SecurityUtils() {
		// Util methods only
	}

	/**
	 * Tests if the request is an internal framework request. The test consists of
	 * checking if the request parameter is present and if its value is consistent
	 * with any of the request types know.
	 *
	 * @param request {@link HttpServletRequest}
	 * @return true if is an internal framework request. False otherwise.
	 */
	public static boolean isFrameworkInternalRequest(HttpServletRequest request) {
		final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
		return parameterValue != null
				&& Stream.of(RequestType.values()).anyMatch(r -> r.getIdentifier().equals(parameterValue));
	}

	/**
	 * Tests if some user is authenticated. As Spring Security always will create an
	 * {@link AnonymousAuthenticationToken} we have to ignore those tokens
	 * explicitly.
	 *//*
		 * static boolean isUserLoggedIn() { Authentication authentication =
		 * SecurityContextHolder.getContext().getAuthentication(); return authentication
		 * != null && !(authentication instanceof AnonymousAuthenticationToken) &&
		 * authentication.isAuthenticated(); }
		 */

	public static boolean isAccessGranted(Class<?> securedClass) {
		// Allow if no roles are required.
		Secured secured = AnnotationUtils.findAnnotation(securedClass, Secured.class);
		if (secured == null) {
			return true; //

		}

		// lookup needed role in user roles
		List<String> allowedRoles = Arrays.asList(secured.value());
		Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();
		return userAuthentication.getAuthorities().stream() //

				.map(GrantedAuthority::getAuthority).anyMatch(allowedRoles::contains);
	}

	public static boolean isUserLoggedIn() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) //

				&& authentication.isAuthenticated();
	}

	public static boolean isUserAutoriced(String privilige) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Boolean a = authentication != null;
		Boolean b = authentication != null && ((Usuario) authentication.getPrincipal()).getRoles().stream()
				.anyMatch(e -> e.getPermisos().stream().anyMatch(o -> isPermited(o, privilige)));
		Boolean c = authentication instanceof Authentication;

		return a && b && c;
		/*
		 * return authentication != null && !(authentication instanceof
		 * UsernamePasswordAuthenticationToken) //
		 * 
		 * 
		 * && ((User)authentication.getPrincipal()).getRoles().stream().anyMatch(e->e.
		 * getPrivileges().stream().anyMatch(o->o.getName().equalsIgnoreCase(privilige))
		 * );
		 */
	}
	/*
	 * private static Object isPermited(Privilege o, String privilige) { // TODO
	 * Auto-generated method stub return null; }
	 */

	private static boolean isPermited(Permiso o, String privilege) {

		return o.getDescripcion().equalsIgnoreCase(privilege) || o.getDescripcion().equals("\\*");
	}
	/*
	 * public static boolean isAccessGranted(Class<?> securedClass) {
	 * if(LoginView.class.equals(securedClass)) { //
	 * 
	 * 
	 * return true; }
	 * 
	 * if(!isUserLoggedIn()) { //
	 * 
	 * 
	 * return false; }
	 * 
	 * // Allow if no roles are required. Secured secured =
	 * AnnotationUtils.findAnnotation(securedClass, Secured.class); if (secured ==
	 * null) {
	 * 
	 * // [...] }
	 */

	public static Usuario getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Usuario user = ((Usuario) authentication.getPrincipal());
		return user;
	}

	public static boolean isPermitted(String string) {
//		// TODO Auto-generated method stub
		
		
//		//buscamos permisos si el usuario tiene un * solo tiene full permisos
		
		Usuario usuario = getUser();
		
		List<String> permisos= new ArrayList<String>();
		
List<List<String>> algo = usuario.getRoles().parallelStream().map(e-> { return e.getPermisos().parallelStream().map(permiso->permiso.getDescripcion()).collect(Collectors.toList()); }).collect(Collectors.toList()); 
		algo.forEach(e->permisos.addAll(e)); 
		//forEach(e-> {permisos.addAll(e.getPermisos()); });
		
		if (permisos.contains("*"))
					return true;
		
//		a:b:c:*:f
		
		if (permisos.contains(string))
			return true;
		
		List<String> permisosConAsterisco= permisos.parallelStream().filter(e->e.contains("*")).collect(Collectors.toList());
		
		if(permisosConAsterisco.isEmpty())
			return false;
		
		List<String>partesPermisoAsterisco = Arrays.asList(string.split("\\*"));
		boolean permitido=true;
		for (String permisoAcomparar : permisosConAsterisco) {
			List<String>partesPerimiso = Arrays.asList(permisoAcomparar.split(":"));
			
				
			
			boolean terminaenAsterisco=partesPerimiso.get(partesPerimiso.size()-1).equals("\\*");
	//		 "a:b:c:*:f"
		
			 
			List<String>partespermisoAcomparar = Arrays.asList(string.split(":"));
		Integer segmentosTotales=Integer.valueOf(partespermisoAcomparar.size());
		Integer partesConcuerdan=Integer.valueOf(segmentosTotales);
			if (terminaenAsterisco) {
				segmentosTotales=partesPerimiso.size();
				partesConcuerdan=partesPerimiso.size();
				for (int i=0;i==(partesPerimiso.size()-1);i++) {
					if (!partesPerimiso.get(i).equalsIgnoreCase(partespermisoAcomparar.get(i))&&!partesPerimiso.get(i).equals("*")) {
						 permitido=false;
						partesConcuerdan--;
					}
				}							
				if(segmentosTotales==partesConcuerdan)
					return true;
			}
				 
			if (!terminaenAsterisco) {
				if(!(partespermisoAcomparar.size()==partesPerimiso.size()))
					 permitido=false;
						partesConcuerdan--;
				for (int i=0;i==(partesPerimiso.size()-1);i++) {
					if (!partesPerimiso.get(i).equalsIgnoreCase(partespermisoAcomparar.get(i))&&!partesPerimiso.get(i).equals("*")) {
						 permitido= false;
						partesConcuerdan--;
					}
				}
			
				if(segmentosTotales==partesConcuerdan)
					return true;
			}
		} 
		
	
		 
		return permitido;
	//	return true;
	}

	public static boolean isAuthenticated() {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean hasRole(String string) {
		// TODO Auto-generated method stub
		List<String> nombretoles = getUser().getRoles().parallelStream().map(rol->rol.getNombre()).collect(Collectors.toList());
		
		if(nombretoles.contains(string))
			return true;
		return  false;
	}

	public static void logout() {
		// TODO Auto-generated method stub
		
	}

	public static boolean hasRoles(List<String> accesoAdmin) {
		// TODO Auto-generated method stub
		
List<String> nombretoles = getUser().getRoles().parallelStream().map(rol->rol.getNombre()).collect(Collectors.toList());
 for (String string2 : accesoAdmin) {
		if(nombretoles.contains(string2))
			return true;
}
	
		return  false;
		
		
	}
}