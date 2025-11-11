package usco.edu.co.SistemaGestion.model;

import java.util.Set;
import java.util.HashSet;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class roles {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idRol")
	private Long idRol;
	
	@Column(nullable = false, unique = true, length = 50)
	private String nombreRol;
	
	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	private Set<usuario> usuarios = new HashSet<>();
	
	// Constructores
	public roles() {}
	
	public roles(String nombreRol) {
		this.nombreRol = nombreRol;
	}
	
	// Getters y Setters
	public Long getIdRol() {
		return idRol;
	}
	
	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}
	
	public String getNombreRol() {
		return nombreRol;
	}
	
	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}
	
	public Set<usuario> getUsuarios() {
		return usuarios;
	}
	
	public void setUsuarios(Set<usuario> usuarios) {
		this.usuarios = usuarios;
	}
}
