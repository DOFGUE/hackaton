package usco.edu.co.SistemaGestion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import usco.edu.co.SistemaGestion.model.validacion;

public interface validacionRepository extends JpaRepository<validacion, Long>{
	
	Optional<validacion> findByTipoValidacion(String tipoValidacion);

}
