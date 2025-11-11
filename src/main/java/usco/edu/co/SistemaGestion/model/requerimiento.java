package usco.edu.co.SistemaGestion.model;

import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "requerimientos")
public class requerimiento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idRequerimiento")
	private Long idRequerimiento;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
		name = "requerimientos_categorias",
		joinColumns = @JoinColumn(name = "idRequerimiento"),
		inverseJoinColumns = @JoinColumn(name = "idCategoria")
	)
	private Set<categoria> categorias = new java.util.HashSet<>();
	
	@Column(nullable = false, unique = true, length = 100)
	private String descripcionRequerimiento;
	
	@Column(nullable = false)
	private String ubicacionRequerimiento;
	
	@Column(nullable = false)
	private String dependenciaRequerimiento;
	
	@Column(nullable = false)
	private String fechaSolicitud;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
		name = "requerimientos_usuarios",
		joinColumns = @JoinColumn(name = "idRequerimiento"),
		inverseJoinColumns = @JoinColumn(name = "idUsuario")
	)
	
	private Set<usuario> solicitantes = new java.util.HashSet<>();
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
		name = "requerimientos_validaciones",
		joinColumns = @JoinColumn(name = "idRequerimiento"),
		inverseJoinColumns = @JoinColumn(name = "idValidacion")
	)
	
	private Set<validacion> validaciones = new java.util.HashSet<>();
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
		name = "requerimientos_estados",
		joinColumns = @JoinColumn(name = "idRequerimiento"),
		inverseJoinColumns = @JoinColumn(name = "idEstado")
	)
	
	private Set<estado> estados = new java.util.HashSet<>();
	
	
	public requerimiento() {}
	
	public requerimiento(String descripcionRequerimiento) {
		this.descripcionRequerimiento = descripcionRequerimiento;
	}
	
	public String getDescripcionRequerimiento() {
		return descripcionRequerimiento;
	}
	
	public void setDescripcionRequerimiento(String descripcionRequerimiento) {
		this.descripcionRequerimiento = descripcionRequerimiento;
	}
	
	public String getUbicacionRequerimiento() {
		return ubicacionRequerimiento;
	}
	
	public void setUbicacionRequerimiento(String ubicacionRequerimiento) {
		this.ubicacionRequerimiento = ubicacionRequerimiento;
	}
	
	public String getDependenciaRequerimiento() {
		return dependenciaRequerimiento;
	}

	public void setDependenciaRequerimiento(String dependenciaRequerimiento) {
		this.dependenciaRequerimiento = dependenciaRequerimiento;
	}
	
	public String getFechaSolicitud() {
		return fechaSolicitud;
	}
	
	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	
	public Long getIdRequerimiento() {
		return idRequerimiento;
	}
	
	public void setIdRequerimiento(Long idRequerimiento) {
		this.idRequerimiento = idRequerimiento;
	}
	
	public Set<usuario> getSolicitantes() {
		return solicitantes;
	}
	
	public void setSolicitantes(Set<usuario> solicitantes) {
		this.solicitantes = solicitantes;
	}
	
	public Set<validacion> getValidaciones() {
		return validaciones;
	}
	
	public void setValidaciones(Set<validacion> validaciones) {
		this.validaciones = validaciones;
	}
	
	public Set<estado> getEstados() {
		return estados;
	}
	
	public void setEstados(Set<estado> estados) {
		this.estados = estados;
	}
	
	public Set<categoria> getCategorias() {
		return categorias;
	}
	
	public void setCategorias(Set<categoria> categorias) {
		this.categorias = categorias;
	}
}
