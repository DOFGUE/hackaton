package usco.edu.co.SistemaGestion.model;

import java.util.Set;

import jakarta.persistence.*;
@Entity
@Table(name = "estados")
public class estado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idEstado")
	private Long idEstado;
	
	@Column(nullable = false, unique = true, length = 50)
	private String nombreEstado;
	
	public estado() {}
	
	public estado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}
	
	public String getNombreEstado() {
		return nombreEstado;
	}
	
	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}

}
