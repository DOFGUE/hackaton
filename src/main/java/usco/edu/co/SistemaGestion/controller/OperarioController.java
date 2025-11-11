package usco.edu.co.SistemaGestion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import usco.edu.co.SistemaGestion.model.requerimiento;
import usco.edu.co.SistemaGestion.model.usuario;
import usco.edu.co.SistemaGestion.model.estado;
import usco.edu.co.SistemaGestion.service.requerimientoService;
import usco.edu.co.SistemaGestion.service.usuarioService;
import usco.edu.co.SistemaGestion.repository.estadoRepository;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/operario")
public class OperarioController {
	
	@Autowired
	private requerimientoService requerimientoService;
	
	@Autowired
	private usuarioService usuarioService;
	
	@Autowired
	private estadoRepository estadoRepository;
	
	/**
	 * Dashboard del operario
	 */
	@GetMapping
	public String index(Model model) {
		usuario usuarioActual = usuarioService.obtenerUsuarioAutenticado();
		
		// Obtener solo los requerimientos asignados a este operario
		List<requerimiento> requerimientos = requerimientoService.obtenerTodosRequerimientos();
		List<requerimiento> misRequerimientos = requerimientos.stream()
			.filter(req -> req.getSolicitantes().stream()
				.anyMatch(sol -> sol.getIdUsuario().equals(usuarioActual.getIdUsuario())))
			.toList();
		
		model.addAttribute("usuario", usuarioActual);
		model.addAttribute("totalTareas", misRequerimientos.size());
		model.addAttribute("requerimientos", misRequerimientos);
		return "operario/index";
	}
	
	/**
	 * Ver mis tareas asignadas
	 */
	@GetMapping("/mis-tareas")
	public String misTareas(Model model) {
		usuario usuarioActual = usuarioService.obtenerUsuarioAutenticado();
		
		// Obtener solo los requerimientos asignados a este operario que NO están completados
		List<requerimiento> requerimientos = requerimientoService.obtenerTodosRequerimientos();
		List<requerimiento> misRequerimientos = requerimientos.stream()
			.filter(req -> req.getSolicitantes().stream()
				.anyMatch(sol -> sol.getIdUsuario().equals(usuarioActual.getIdUsuario())))
			.filter(req -> req.getEstados().stream()
				.noneMatch(est -> est.getNombreEstado().equalsIgnoreCase("COMPLETADO")))
			.toList();
		
		model.addAttribute("usuario", usuarioActual);
		model.addAttribute("requerimientos", misRequerimientos);
		return "operario/mis-tareas";
	}
	
	/**
	 * Página para ejecutar una tarea
	 */
	@GetMapping("/ejecutar/{id}")
	public String ejecutarTarea(@PathVariable Long id, Model model) {
		Optional<requerimiento> req = requerimientoService.obtenerRequerimientoPorId(id);
		
		if (req.isPresent()) {
			requerimiento requerimiento = req.get();
			
			// Validar que la tarea NO esté completada
			boolean estaCompletada = requerimiento.getEstados().stream()
				.anyMatch(est -> est.getNombreEstado().equalsIgnoreCase("COMPLETADO"));
			
			if (estaCompletada) {
				return "redirect:/operario/mis-tareas";
			}
			
			// Obtener todos los estados disponibles
			List<estado> todosEstados = estadoRepository.findAll();
			
			model.addAttribute("requerimiento", requerimiento);
			model.addAttribute("estados", todosEstados);
			return "operario/ejecutar-tarea";
		} else {
			return "redirect:/operario/mis-tareas";
		}
	}
	
	/**
	 * Procesar ejecución de tarea
	 */
	@PostMapping("/ejecutar/{id}")
	public String guardarProgreso(
			@PathVariable Long id,
			@RequestParam Integer progreso,
			@RequestParam(required = false) String observaciones,
			@RequestParam(required = false) Long idEstado,
			Model model) {
		
		try {
			Optional<requerimiento> req = requerimientoService.obtenerRequerimientoPorId(id);
			
			if (req.isPresent()) {
				requerimiento requerimiento = req.get();
				requerimiento.setPorcentajeProgreso(progreso);
				requerimiento.setObservaciones(observaciones != null ? observaciones : "");
				
				// Asignar el estado si se seleccionó uno
				if (idEstado != null) {
					Optional<estado> est = estadoRepository.findById(idEstado);
					if (est.isPresent()) {
						requerimiento.getEstados().clear();
						requerimiento.getEstados().add(est.get());
					}
				}
				
				requerimientoService.actualizarRequerimiento(id, requerimiento);
				
				model.addAttribute("mensaje", "Progreso y estado registrados correctamente");
				return "redirect:/operario/mis-tareas";
			} else {
				model.addAttribute("error", "Tarea no encontrada");
				return "redirect:/operario/mis-tareas";
			}
		} catch (Exception e) {
			model.addAttribute("error", "Error al registrar progreso: " + e.getMessage());
			return "redirect:/operario/ejecutar/" + id;
		}
	}
	
	/**
	 * Reportar tarea como completada
	 */
	@PostMapping("/completar/{id}")
	public String completarTarea(
			@PathVariable Long id,
			@RequestParam(required = false) String reporte,
			Model model) {
		
		try {
			Optional<requerimiento> req = requerimientoService.obtenerRequerimientoPorId(id);
			
			if (req.isPresent()) {
				requerimiento requerimiento = req.get();
				
				// Establecer el estado a COMPLETADO
				List<estado> estados = estadoRepository.findAll();
				for (estado est : estados) {
					if (est.getNombreEstado().equalsIgnoreCase("COMPLETADO")) {
						requerimiento.getEstados().clear();
						requerimiento.getEstados().add(est);
						break;
					}
				}
				
				// Establecer progreso al 100%
				requerimiento.setPorcentajeProgreso(100);
				
				requerimientoService.actualizarRequerimiento(id, requerimiento);
				
				model.addAttribute("mensaje", "Tarea marcada como completada");
				return "redirect:/operario/mis-tareas";
			} else {
				return "redirect:/operario/mis-tareas";
			}
		} catch (Exception e) {
			model.addAttribute("error", "Error al completar tarea: " + e.getMessage());
			return "redirect:/operario/ejecutar/" + id;
		}
	}
}
