package usco.edu.co.SistemaGestion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import usco.edu.co.SistemaGestion.model.estado;

public interface estadoRepository extends JpaRepository<estado, Long> {
	
	Optional<estado> findByNombreEstado(String nombreEstado);

}
