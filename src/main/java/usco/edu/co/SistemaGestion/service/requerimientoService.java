package usco.edu.co.SistemaGestion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usco.edu.co.SistemaGestion.model.requerimiento;
import usco.edu.co.SistemaGestion.model.usuario;
import usco.edu.co.SistemaGestion.repository.requerimientoRepository;
import usco.edu.co.SistemaGestion.repository.usurioRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class requerimientoService {
	
	@Autowired
	private requerimientoRepository repositorioRequerimiento;
	
	@Autowired
	private usurioRepository repositorioUsuario;
	
	/**
	 * Obtiene todos los requerimientos
	 */
	public List<requerimiento> obtenerTodosRequerimientos() {
		return repositorioRequerimiento.findAll();
	}
	
	/**
	 * Obtiene un requerimiento por ID
	 */
	public Optional<requerimiento> obtenerRequerimientoPorId(Long id) {
		return repositorioRequerimiento.findById(id);
	}
	
	/**
	 * Crea un nuevo requerimiento
	 */
	public requerimiento crearRequerimiento(requerimiento nuevoRequerimiento) {
		// Validar que la descripción no esté vacía
		if (nuevoRequerimiento.getDescripcionRequerimiento() == null 
				|| nuevoRequerimiento.getDescripcionRequerimiento().isEmpty()) {
			throw new IllegalArgumentException("La descripción del requerimiento es obligatoria");
		}
		
		// Validar campos obligatorios
		if (nuevoRequerimiento.getUbicacionRequerimiento() == null 
				|| nuevoRequerimiento.getUbicacionRequerimiento().isEmpty()) {
			throw new IllegalArgumentException("La ubicación del requerimiento es obligatoria");
		}
		
		if (nuevoRequerimiento.getDependenciaRequerimiento() == null 
				|| nuevoRequerimiento.getDependenciaRequerimiento().isEmpty()) {
			throw new IllegalArgumentException("La dependencia del requerimiento es obligatoria");
		}
		
		if (nuevoRequerimiento.getFechaSolicitud() == null 
				|| nuevoRequerimiento.getFechaSolicitud().isEmpty()) {
			throw new IllegalArgumentException("La fecha de solicitud es obligatoria");
		}
		
		return repositorioRequerimiento.save(nuevoRequerimiento);
	}
	
	/**
	 * Actualiza un requerimiento existente
	 */
	public requerimiento actualizarRequerimiento(Long id, requerimiento requerimientoActualizado) {
		return repositorioRequerimiento.findById(id)
				.map(requerimiento -> {
					if (requerimientoActualizado.getDescripcionRequerimiento() != null 
							&& !requerimientoActualizado.getDescripcionRequerimiento().isEmpty()) {
						requerimiento.setDescripcionRequerimiento(requerimientoActualizado.getDescripcionRequerimiento());
					}
					if (requerimientoActualizado.getUbicacionRequerimiento() != null 
							&& !requerimientoActualizado.getUbicacionRequerimiento().isEmpty()) {
						requerimiento.setUbicacionRequerimiento(requerimientoActualizado.getUbicacionRequerimiento());
					}
					if (requerimientoActualizado.getDependenciaRequerimiento() != null 
							&& !requerimientoActualizado.getDependenciaRequerimiento().isEmpty()) {
						requerimiento.setDependenciaRequerimiento(requerimientoActualizado.getDependenciaRequerimiento());
					}
					if (requerimientoActualizado.getFechaSolicitud() != null 
							&& !requerimientoActualizado.getFechaSolicitud().isEmpty()) {
						requerimiento.setFechaSolicitud(requerimientoActualizado.getFechaSolicitud());
					}
					return repositorioRequerimiento.save(requerimiento);
				})
				.orElseThrow(() -> new IllegalArgumentException("Requerimiento no encontrado con ID: " + id));
	}
	
	/**
	 * Elimina un requerimiento
	 */
	public void eliminarRequerimiento(Long id) {
		if (!repositorioRequerimiento.existsById(id)) {
			throw new IllegalArgumentException("Requerimiento no encontrado con ID: " + id);
		}
		repositorioRequerimiento.deleteById(id);
	}
	
	/**
	 * Agrega un solicitante a un requerimiento
	 */
	public requerimiento agregarSolicitante(Long idRequerimiento, Long idUsuario) {
		requerimiento req = repositorioRequerimiento.findById(idRequerimiento)
				.orElseThrow(() -> new IllegalArgumentException("Requerimiento no encontrado con ID: " + idRequerimiento));
		
		usuario user = repositorioUsuario.findById(idUsuario)
				.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + idUsuario));
		
		req.getSolicitantes().add(user);
		return repositorioRequerimiento.save(req);
	}
	
	/**
	 * Elimina un solicitante de un requerimiento
	 */
	public requerimiento eliminarSolicitante(Long idRequerimiento, Long idUsuario) {
		requerimiento req = repositorioRequerimiento.findById(idRequerimiento)
				.orElseThrow(() -> new IllegalArgumentException("Requerimiento no encontrado con ID: " + idRequerimiento));
		
		usuario user = repositorioUsuario.findById(idUsuario)
				.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + idUsuario));
		
		req.getSolicitantes().remove(user);
		return repositorioRequerimiento.save(req);
	}
	
	/**
	 * Obtiene los solicitantes de un requerimiento
	 */
	public Set<usuario> obtenerSolicitantesRequerimiento(Long idRequerimiento) {
		requerimiento req = repositorioRequerimiento.findById(idRequerimiento)
				.orElseThrow(() -> new IllegalArgumentException("Requerimiento no encontrado con ID: " + idRequerimiento));
		return req.getSolicitantes();
	}
	
	/**
	 * Obtiene las validaciones de un requerimiento
	 */
	public Set<usco.edu.co.SistemaGestion.model.validacion> obtenerValidacionesRequerimiento(Long idRequerimiento) {
		requerimiento req = repositorioRequerimiento.findById(idRequerimiento)
				.orElseThrow(() -> new IllegalArgumentException("Requerimiento no encontrado con ID: " + idRequerimiento));
		return req.getValidaciones();
	}
	
	/**
	 * Obtiene los estados de un requerimiento
	 */
	public Set<usco.edu.co.SistemaGestion.model.estado> obtenerEstadosRequerimiento(Long idRequerimiento) {
		requerimiento req = repositorioRequerimiento.findById(idRequerimiento)
				.orElseThrow(() -> new IllegalArgumentException("Requerimiento no encontrado con ID: " + idRequerimiento));
		return req.getEstados();
	}
	
	/**
	 * Obtiene las categorías de un requerimiento
	 */
	public Set<usco.edu.co.SistemaGestion.model.categoria> obtenerCategoriasRequerimiento(Long idRequerimiento) {
		requerimiento req = repositorioRequerimiento.findById(idRequerimiento)
				.orElseThrow(() -> new IllegalArgumentException("Requerimiento no encontrado con ID: " + idRequerimiento));
		return req.getCategorias();
	}

}
