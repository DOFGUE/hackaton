package usco.edu.co.SistemaGestion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import usco.edu.co.SistemaGestion.model.roles;

public interface rolesRepository extends JpaRepository<roles, Long>{
	Optional<roles> findByNombreRol(String nombreRol);

}
