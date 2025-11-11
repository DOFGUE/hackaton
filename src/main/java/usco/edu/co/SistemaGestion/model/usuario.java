package usco.edu.co.SistemaGestion.model;

import java.util.Set;
import java.util.HashSet;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idUsuario")
	private Long idUsuario;
	
	@Column(nullable = false, unique = true, length = 100)
	private String nombreUsuario;
	
	@Column(nullable = false)
	private String contrasena;
	
	@Column(nullable = false)
	private boolean enabled = true;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
	@JoinTable(
		name = "usuarios_roles",
		joinColumns = @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario"),
		inverseJoinColumns = @JoinColumn(name = "idRol", referencedColumnName = "idRol")
	)
	private Set<roles> roles = new HashSet<>();
	
	// Constructores
	public usuario() {}
	
	public usuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
	// Getters y Setters
	public Long getIdUsuario() {
		return idUsuario;
	}
	
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
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
	
	public Set<roles> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<roles> roles) {
		this.roles = roles;
	}
}