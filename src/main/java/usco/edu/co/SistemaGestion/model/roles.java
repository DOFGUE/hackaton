package usco.edu.co.SistemaGestion.model;

import java.util.Set;

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
	
	public roles() {}
	
	public roles(String nombreRol) {
		this.nombreRol = nombreRol;
	}
	
	public String getNombreRol() {
		return nombreRol;
	}
	
	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}
}
