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
import usco.edu.co.SistemaGestion.model.validacion;
import usco.edu.co.SistemaGestion.model.estado;
import usco.edu.co.SistemaGestion.service.requerimientoService;
import usco.edu.co.SistemaGestion.service.usuarioService;
import usco.edu.co.SistemaGestion.repository.validacionRepository;
import usco.edu.co.SistemaGestion.repository.estadoRepository;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/oficina-mantenimiento")
public class OficinaMantenimientoController {
	
	@Autowired
	private requerimientoService requerimientoService;
	
	@Autowired
	private usuarioService usuarioService;
	
	@Autowired
	private validacionRepository validacionRepository;
	
	@Autowired
	private estadoRepository estadoRepository;
	
	/**
	 * Dashboard de oficina de mantenimiento
	 */
	@GetMapping
	public String index(Model model) {
		usuario usuarioActual = usuarioService.obtenerUsuarioAutenticado();
		List<requerimiento> requerimientos = requerimientoService.obtenerTodosRequerimientos();
		
		model.addAttribute("usuario", usuarioActual);
		model.addAttribute("totalRequerimientos", requerimientos.size());
		model.addAttribute("requerimientos", requerimientos);
		return "oficina-mantenimiento/index";
	}
	
	/**
	 * Ver requerimientos pendientes de revisión
	 */
	@GetMapping("/requerimientos-pendientes")
	public String requerimientosPendientes(Model model) {
		List<requerimiento> requerimientos = requerimientoService.obtenerTodosRequerimientos();
		model.addAttribute("requerimientos", requerimientos);
		return "oficina-mantenimiento/requerimientos-pendientes";
	}
	
	/**
	 * Revisar un requerimiento específico
	 */
	@GetMapping("/revisar/{id}")
	public String revisarRequerimiento(@PathVariable Long id, Model model) {
		Optional<requerimiento> req = requerimientoService.obtenerRequerimientoPorId(id);
		
		if (req.isPresent()) {
			List<validacion> validaciones = validacionRepository.findAll();
			model.addAttribute("requerimiento", req.get());
			model.addAttribute("validacion", validaciones);
			return "oficina-mantenimiento/revisar-requerimiento";
		} else {
			return "redirect:/oficina-mantenimiento/requerimientos-pendientes";
		}
	}
	
	/**
	 * Procesar asignación de validación a requerimiento
	 */
	@PostMapping("/revisar/{id}")
	public String procesarRevision(
			@PathVariable Long id,
			@RequestParam Long idValidacion,
			Model model) {
		
		try {
			Optional<requerimiento> req = requerimientoService.obtenerRequerimientoPorId(id);
			Optional<validacion> val = validacionRepository.findById(idValidacion);
			
			if (req.isPresent() && val.isPresent()) {
				requerimiento requerimiento = req.get();
				validacion validacion = val.get();
				
				// Limpiar validaciones anteriores y asignar la nueva
				requerimiento.getValidaciones().clear();
				requerimiento.getValidaciones().add(validacion);
				
				requerimientoService.actualizarRequerimiento(id, requerimiento);
				model.addAttribute("mensaje", "Validación asignada: " + validacion.getTipoValidacion());
			}
			
			return "redirect:/oficina-mantenimiento/requerimientos-pendientes";
		} catch (Exception e) {
			model.addAttribute("error", "Error al procesar requerimiento: " + e.getMessage());
			return "redirect:/oficina-mantenimiento/revisar/" + id;
		}
	}
	
	/**
	 * Ver historial de requerimientos
	 */
	@GetMapping("/historial")
	public String historial(Model model) {
		List<requerimiento> requerimientos = requerimientoService.obtenerTodosRequerimientos();
		model.addAttribute("requerimientos", requerimientos);
		return "oficina-mantenimiento/historial";
	}
	
	/**
	 * Ver tareas completadas
	 */
	@GetMapping("/completadas")
	public String tareasCompletadas(Model model) {
		List<requerimiento> todosRequerimientos = requerimientoService.obtenerTodosRequerimientos();
		
		// Filtrar solo los requerimientos completados
		List<requerimiento> completadas = todosRequerimientos.stream()
			.filter(req -> req.getEstados().stream()
				.anyMatch(est -> est.getNombreEstado().equalsIgnoreCase("COMPLETADO")))
			.toList();
		
		model.addAttribute("requerimientos", completadas);
		return "oficina-mantenimiento/tareas-completadas";
	}
	
	/**
	 * Reabrir una tarea completada
	 */
	@PostMapping("/reabrir/{id}")
	public String reabrirTarea(@PathVariable Long id, Model model) {
		try {
			Optional<requerimiento> req = requerimientoService.obtenerRequerimientoPorId(id);
			
			if (req.isPresent()) {
				requerimiento requerimiento = req.get();
				
				// Cambiar estado a "Espera"
				List<estado> estados = estadoRepository.findAll();
				for (estado est : estados) {
					if (est.getNombreEstado().equalsIgnoreCase("ESPERA")) {
						requerimiento.getEstados().clear();
						requerimiento.getEstados().add(est);
						break;
					}
				}
				
				// Limpiar operario asignado
				requerimiento.getSolicitantes().clear();
				
				requerimientoService.actualizarRequerimiento(id, requerimiento);
				model.addAttribute("mensaje", "Tarea reabierta exitosamente");
				return "redirect:/oficina-mantenimiento/completadas";
			} else {
				return "redirect:/oficina-mantenimiento/completadas";
			}
		} catch (Exception e) {
			model.addAttribute("error", "Error al reabrir tarea: " + e.getMessage());
			return "redirect:/oficina-mantenimiento/completadas";
		}
	}
	
	/**
	 * Eliminar una tarea completada
	 */
	@PostMapping("/eliminar/{id}")
	public String eliminarTarea(@PathVariable Long id, Model model) {
		try {
			Optional<requerimiento> req = requerimientoService.obtenerRequerimientoPorId(id);
			
			if (req.isPresent()) {
				requerimientoService.eliminarRequerimiento(id);
				model.addAttribute("mensaje", "Tarea eliminada exitosamente");
				return "redirect:/oficina-mantenimiento/completadas";
			} else {
				return "redirect:/oficina-mantenimiento/completadas";
			}
		} catch (Exception e) {
			model.addAttribute("error", "Error al eliminar tarea: " + e.getMessage());
			return "redirect:/oficina-mantenimiento/completadas";
		}
	}
}
