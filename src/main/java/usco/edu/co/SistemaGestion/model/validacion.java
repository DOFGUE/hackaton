package usco.edu.co.SistemaGestion.model;

import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "validacion")
public class validacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idValidacion")
	private Long idValidacion;
	
	@Column(nullable = false, unique = true, length = 100)
	private String tipoValidacion;
	
	public validacion() {}
	
	public validacion(String tipoValidacion) {
		this.tipoValidacion = tipoValidacion;
	}
	
	public String getTipoValidacion() {
		return tipoValidacion;
	}
	
	public void setTipoValidacion(String tipoValidacion) {
		this.tipoValidacion = tipoValidacion;
	}
}
