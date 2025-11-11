package usco.edu.co.SistemaGestion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import usco.edu.co.SistemaGestion.model.categoria;

public interface categoriaRepository extends JpaRepository<categoria, Long> {
	Optional<categoria> findByNombreCategoria(String nombreCategoria);

}
