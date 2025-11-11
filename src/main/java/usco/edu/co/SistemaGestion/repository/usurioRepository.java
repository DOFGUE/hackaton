package usco.edu.co.SistemaGestion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import usco.edu.co.SistemaGestion.model.usuario;

public interface usurioRepository extends JpaRepository<usuario, Long>{
	
	Optional<usuario> findBynombreUsuario(String nombreUsuario);

}
