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
import usco.edu.co.SistemaGestion.repository.usurioRepository;
import usco.edu.co.SistemaGestion.repository.estadoRepository;
import usco.edu.co.SistemaGestion.service.requerimientoService;
import usco.edu.co.SistemaGestion.service.usuarioService;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/jefe-programador-mantenimiento")
public class JefeMantenimientoController {
	
	@Autowired
	private requerimientoService requerimientoService;
	
	@Autowired
	private usuarioService usuarioService;
	
	@Autowired
	private usurioRepository usuarioRepository;
	
	@Autowired
	private estadoRepository estadoRepository;
	
	/**
	 * Dashboard del jefe de mantenimiento
	 */
	@GetMapping
	public String index(Model model) {
		usuario usuarioActual = usuarioService.obtenerUsuarioAutenticado();
		List<requerimiento> requerimientos = requerimientoService.obtenerTodosRequerimientos();
		
		model.addAttribute("usuario", usuarioActual);
		model.addAttribute("totalTareas", requerimientos.size());
		model.addAttribute("requerimientos", requerimientos);
		return "jefe-programador-mantenimiento/index";
	}
	
	/**
	 * Ver tareas asignadas a este jefe
	 */
	@GetMapping("/asignaciones")
	public String tareasAsignadas(Model model) {
		List<requerimiento> requerimientos = requerimientoService.obtenerTodosRequerimientos();
		model.addAttribute("requerimientos", requerimientos);
		return "jefe-programador-mantenimiento/asignaciones";
	}
	
	/**
	 * Página para asignar operario a una tarea
	 */
	@GetMapping("/asignar/{id}")
	public String asignarOperario(@PathVariable Long id, Model model) {
		Optional<requerimiento> req = requerimientoService.obtenerRequerimientoPorId(id);
		
		if (req.isPresent()) {
			// Filtrar solo usuarios con rol de OPERARIO
			List<usuario> todosLosUsuarios = usuarioRepository.findAll();
			List<usuario> operarios = todosLosUsuarios.stream()
				.filter(u -> u.getRoles().stream()
					.anyMatch(r -> r.getNombreRol().equalsIgnoreCase("OPERARIO")))
				.toList();
			
			model.addAttribute("requerimiento", req.get());
			model.addAttribute("operarios", operarios);
			return "jefe-programador-mantenimiento/asignar-operario";
		} else {
			return "redirect:/jefe-programador-mantenimiento/asignaciones";
		}
	}
	
	/**
	 * Procesar asignación de operario
	 */
	@PostMapping("/asignar/{id}")
	public String guardarAsignacion(
			@PathVariable Long id,
			@RequestParam Long idOperario,
			Model model) {
		
		try {
			Optional<requerimiento> req = requerimientoService.obtenerRequerimientoPorId(id);
			Optional<usuario> operario = usuarioRepository.findById(idOperario);
			
			if (req.isPresent() && operario.isPresent()) {
				requerimiento requerimiento = req.get();
				requerimiento.getSolicitantes().add(operario.get());
				requerimientoService.actualizarRequerimiento(id, requerimiento);
				
				model.addAttribute("mensaje", "Operario asignado correctamente");
				return "redirect:/jefe-programador-mantenimiento/asignaciones";
			} else {
				model.addAttribute("error", "Requerimiento u operario no encontrado");
				return "redirect:/jefe-programador-mantenimiento/asignar/" + id;
			}
		} catch (Exception e) {
			model.addAttribute("error", "Error al asignar operario: " + e.getMessage());
			return "redirect:/jefe-programador-mantenimiento/asignar/" + id;
		}
	}
	
	/**
	 * Ver progreso de tareas
	 */
	@GetMapping("/progreso")
	public String progresoTareas(Model model) {
		List<requerimiento> requerimientos = requerimientoService.obtenerTodosRequerimientos();
		model.addAttribute("requerimientos", requerimientos);
		return "jefe-programador-mantenimiento/progreso-tareas";
	}
}
