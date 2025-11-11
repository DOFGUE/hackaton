package usco.edu.co.SistemaGestion.model;

import java.util.Set;

import jakarta.persistence.*;
@Entity
@Table(name = "categorias")
public class categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCategoria")
	private Long idCategoria;
	
	@Column(nullable = false, unique = true, length = 100)
	private String nombreCategoria;
	
	public categoria() {}
	
	public categoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}
	
	public String getNombreCategoria() {
		return nombreCategoria;
	}
	
	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}
}
