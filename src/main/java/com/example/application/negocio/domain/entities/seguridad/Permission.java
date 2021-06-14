package com.example.application.negocio.domain.entities.seguridad;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
@Entity
@Audited
@Table
public class Permission implements Serializable 
{ 
/**
	 * 
	 */
	private static final long serialVersionUID = 5322109520067950165L;
private Integer id;
private String descripcion;
//private List<Rol> roles;
@Id @Column(name="id")
@GeneratedValue
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
@Column(nullable=false,unique=true)
public String getDescripcion() {
	return descripcion;
}
public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}



public Permission(){
	super();
	//roles= new ArrayList<>();
}
public Permission(Integer id, String descripcion) {
	super();
	this.id = id;
	this.descripcion = descripcion;
	//roles= new ArrayList<>();
}/*
@ManyToMany(mappedBy="permisos",targetEntity=Rol.class,cascade=CascadeType.ALL, fetch=FetchType.EAGER)
@Fetch(FetchMode.SUBSELECT)
public List<Rol> getRoles() {
	return roles;
}
public void setRoles(List<Rol> roles) {
	this.roles = roles;
}
*/


public Permission(String name) {
	// TODO Auto-generated constructor stub
	this.descripcion=name;
}
@Override
public String toString() {
	return descripcion ;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
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
	Permission other = (Permission) obj;
	if (descripcion == null) {
		if (other.descripcion != null)
			return false;
	} else if (!descripcion.equals(other.descripcion))
		return false;
	return true;
}





}
