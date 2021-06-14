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
public class Role implements Serializable {
	
/**
	 * 
	 */
	private static final long serialVersionUID = -1062957397752290266L;
private Integer id;
private String nombre;
private List<Permission> permisos;
private List<User> usuarios;



public static final String Admin="Admin";
public static final String Ggeneral="GGeneral";
public static final String Gventas="GVentas";
public static final String Goperraciones="GOperaciones";
public static final String Ginventario="GInventario";
public static final String Vendedor="Vendedor";
//public static final String Empacador="Empacador";
public static final String Auditor="Auditor";
public static final String Impresor="Impresor";

public static final String Troquelador="Troquelador";
public static final String Rebobinador="Rebobinador";
public static final String Despachador="Despachador";

@Id @Column(name="id")
@GeneratedValue
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
@Column(nullable=false,unique=true)
public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}

@ManyToMany(cascade=CascadeType.ALL,targetEntity=Permission.class,fetch=FetchType.EAGER)
@Fetch(FetchMode.SUBSELECT)
@JoinTable(name="permisos_rol", joinColumns=@JoinColumn(name="rol_id"), inverseJoinColumns=@JoinColumn(name="permiso_id"),schema="public")
public List<Permission> getPermisos() {
	return permisos;
}
public void setPermisos(List<Permission> permisos) {
	this.permisos = permisos;
}


public Role(){
	super();
	permisos=new ArrayList<>();
	usuarios=new ArrayList<>();
	
}
public Role(Integer id, String nombre) {
	super();
	this.id = id;
	this.nombre = nombre;
	permisos=new ArrayList<>();
	usuarios=new ArrayList<>();
}




public Role(String name) {
	// TODO Auto-generated constructor stub
	this.nombre=name;
}
@ManyToMany(fetch=FetchType.EAGER,targetEntity=User.class,mappedBy="roles")
@Fetch(FetchMode.SUBSELECT)
//@JoinTable(name="rol_usuario", joinColumns=@JoinColumn(name="rol_id"), inverseJoinColumns=@JoinColumn(name="usuario_id"),schema="public")
public List<User> getUsuarios() {
	return usuarios;
}
public void setUsuarios(List<User> usuarios) {
	this.usuarios = usuarios;

}







@Transient
public void setSetUsuarios(Set<User> usuarios){
	

	List<User> list = new ArrayList<User>(usuarios);
	this.usuarios=list;

}

@Transient
public Set<User> getSetUsuarios(){
	Set<User> iSet = null;
if (usuarios!=null)	 iSet = new  HashSet<User>(usuarios);
	return iSet;
	

}


@Transient
public void setSetPermisos(Set<Permission> terminados){
	

	List<Permission> list = new ArrayList<Permission>(terminados);
	this.permisos=list;

}

@Transient
public Set<Permission> getSetPermisos(){
	Set<Permission> iSet = null;
if (permisos!=null)	 iSet = new  HashSet<Permission>(permisos);
	return iSet;
	

}
@Override
public String toString() {
	return  nombre ;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
	Role other = (Role) obj;
	if (nombre == null) {
		if (other.nombre != null)
			return false;
	} else if (!nombre.equals(other.nombre))
		return false;
	return true;
}



}
