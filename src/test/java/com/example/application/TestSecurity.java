package com.example.application;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.math3.optim.linear.UnboundedSolutionException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.example.application.negocio.domain.entities.seguridad.User;
import com.example.application.negocio.seguridad.SecurityUtils;
import com.example.application.securityTestContext.WithMockCustomUser;
import com.github.mvysny.kaributesting.v10.MockVaadin;
import com.github.mvysny.kaributesting.v10.Routes;
import com.github.mvysny.kaributesting.v10.spring.MockSpringServlet;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.spring.SpringServlet;

import kotlin.jvm.functions.Function0;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
@DirtiesContext

@PrepareForTest(SecurityUtils.class)




class TestSecurity {

	 @Autowired
	    private ApplicationContext ctx;
//	 @Autowired
//	    private static  AuthenticationManager authenticationManager;

 private static Routes routes;
 
 

 @BeforeAll
 @WithMockUser(username = "t2", authorities = { "ADMIN", "USER" },password = "t2")
 public static void discoverRoutes() {
     // routes = new Routes().autoDiscoverViews("com.vaadin.tshirtshop");
 	// routes = new Routes().autoDiscoverViews("firsoft4.views,firsoft4.firsoft3.ui");
 	// routes = new Routes().autoDiscoverViews("firsoft4.firsoft3.ui");
 	 routes = new Routes().autoDiscoverViews("com.example.application");
 	/*	final Authentication authentication = authenticationManager
 				.authenticate(new UsernamePasswordAuthenticationToken("t","t")); //

 		// if authentication was successful we will update the security context and
 		// redirect to the page requested first
 		SecurityContextHolder.getContext().setAuthentication(authentication);*/
 }


/*
 @Autowired
 private TShirtOrderRepository repo;*/

 @BeforeEach
 public void setup() {
     final Function0<UI> uiFactory = UI::new;
     final SpringServlet servlet = new MockSpringServlet(routes, ctx, uiFactory);
 /*    VaadinServletConfig servletConfig= new VaadinServletConfig(servlet.getServletConfig());
     servletConfig.
		try {
			servlet.init(servletConfig);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
     MockVaadin.setup(uiFactory, servlet);
  //   repo.deleteAll();

	
 }

 @AfterEach
 public void tearDown() {
     MockVaadin.tearDown();
 }

	
	
	
	
	
	
	
	
	
	
	
	
	
	@Test @WithMockCustomUser(name = "t",username = "t",password = "t")
	void test() {
		User user =SecurityUtils.getUser();
		assertEquals(user.getUsername(), "t");
		
		
	//	fail("Not yet implemented");
	}

}
