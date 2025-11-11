package usco.edu.co.SistemaGestion.model;

import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idUsuario")
	private Long idSolicitante;
	
	@Column(nullable = false, unique = true, length = 100)
	private String nombreUsuario;
	
	@Column(nullable = false)
	private String contrasena;
	
	private boolean enabled = true;
	
	public usuario() {}
	
	public usuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
	public String getContrasena() {
		return contrasena;
	}
	
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public Long getIdSolicitante() {
		return idSolicitante;
	}
	
	public void setIdSolicitante(Long idSolicitante) {
		this.idSolicitante = idSolicitante;
	}
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
		name = "solicitantes_roles",
		joinColumns = @JoinColumn(name = "idUsuario"),
		inverseJoinColumns = @JoinColumn(name = "idRol")
	)
	private Set<roles> roles = new java.util.HashSet<>();
	
	public Set<roles> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<roles> roles) {
		this.roles = roles;
	}
	
	
}