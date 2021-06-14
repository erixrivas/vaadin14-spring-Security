package com.example.application.negocio.domain.entities.seguridad;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 5301704855602505174L;

	private Integer id;

	private String username;

	private String email;

	private String password;

	private String salt;
	
	private List<Role> roles;
	private Boolean activo;

	@Id @Column(name="id")
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(nullable=false,unique=true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	
	

	
	public User() {
		super();
		
		roles = new ArrayList<>();
		
		
		// TODO Auto-generated constructor stub
	}

	
	
	@ManyToMany(targetEntity=Role.class,cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name="rol_usuario", joinColumns=@JoinColumn(name="usuario_id"), inverseJoinColumns=@JoinColumn(name="rol_id"),schema="public")	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public User(String username, String email, String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.activo=true;
	}
			
	@Transient
	public void setSetRoles(Set<Role> roles){
		System.out.println("cambio roles");

		List<Role> list = new ArrayList<Role>(roles);
		this.roles=list;

	}
	
	@Transient
	public Set<Role> getSetRoles(){
		Set<Role> iSet = null;
	if (roles!=null)	 iSet = new  HashSet<Role>(roles);
		return iSet;
		

	}
	
	
	
	  @Override
	    public String toString() {
	        return this.getUsername();
	    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((salt == null) ? 0 : salt.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (salt == null) {
			if (other.salt != null)
				return false;
		} else if (!salt.equals(other.salt))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
@Column
	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	

	

}
