package usco.edu.co.SistemaGestion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import usco.edu.co.SistemaGestion.model.categoria;
import usco.edu.co.SistemaGestion.model.requerimiento;
import usco.edu.co.SistemaGestion.model.usuario;
import usco.edu.co.SistemaGestion.model.estado;
import usco.edu.co.SistemaGestion.repository.categoriaRepository;
import usco.edu.co.SistemaGestion.repository.estadoRepository;
import usco.edu.co.SistemaGestion.service.requerimientoService;
import usco.edu.co.SistemaGestion.service.usuarioService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/solicitante")
public class SolicitanteController {
	
	@Autowired
	private requerimientoService requerimientoService;
	
	@Autowired
	private usuarioService usuarioService;
	
	@Autowired
	private categoriaRepository categoriaRepository;
	
	@Autowired
	private estadoRepository estadoRepository;
	
	/**
	 * Dashboard del solicitante
	 */
	@GetMapping
	public String index(Model model) {
		usuario usuarioActual = usuarioService.obtenerUsuarioAutenticado();
		model.addAttribute("usuario", usuarioActual);
		return "solicitante/index";
	}
	
	/**
	 * Página para crear un nuevo requerimiento
	 */
	@GetMapping("/crear")
	public String crearRequerimiento(Model model) {
		List<categoria> categorias = categoriaRepository.findAll();
		model.addAttribute("categorias", categorias);
		return "solicitante/crear-requerimiento";
	}
	
	/**
	 * Procesar creación de requerimiento
	 */
	@PostMapping("/crear")
	public String guardarRequerimiento(
			@RequestParam String descripcion,
			@RequestParam String ubicacion,
			@RequestParam String dependencia,
			@RequestParam String fecha,
			@RequestParam(required = false) Long[] categorias,
			Model model) {
		
		try {
			requerimiento nuevoRequerimiento = new requerimiento();
			nuevoRequerimiento.setDescripcionRequerimiento(descripcion);
			nuevoRequerimiento.setUbicacionRequerimiento(ubicacion);
			nuevoRequerimiento.setDependenciaRequerimiento(dependencia);
			nuevoRequerimiento.setFechaSolicitud(fecha);
			
			usuario usuarioActual = usuarioService.obtenerUsuarioAutenticado();
			if (usuarioActual != null) {
				nuevoRequerimiento.getSolicitantes().add(usuarioActual);
			}
			
			// Agregar categorías seleccionadas
			if (categorias != null && categorias.length > 0) {
				for (Long idCategoria : categorias) {
					Optional<categoria> cat = categoriaRepository.findById(idCategoria);
					if (cat.isPresent()) {
						nuevoRequerimiento.getCategorias().add(cat.get());
					}
				}
			}
			
			requerimientoService.crearRequerimiento(nuevoRequerimiento);
			model.addAttribute("mensaje", "Requerimiento creado exitosamente");
			return "redirect:/solicitante/mis-requerimientos";
		} catch (Exception e) {
			model.addAttribute("error", "Error al crear requerimiento: " + e.getMessage());
			return "solicitante/crear-requerimiento";
		}
	}
	
	/**
	 * Ver mis requerimientos
	 */
	@GetMapping("/mis-requerimientos")
	public String misRequerimientos(Model model) {
		usuario usuarioActual = usuarioService.obtenerUsuarioAutenticado();
		List<requerimiento> todosRequerimientos = requerimientoService.obtenerTodosRequerimientos();
		
		// Filtrar: solo mostrar requerimientos que no están completados
		List<requerimiento> requerimientos = todosRequerimientos.stream()
			.filter(req -> req.getEstados().stream()
				.noneMatch(est -> est.getNombreEstado().equalsIgnoreCase("COMPLETADO")))
			.toList();
		
		model.addAttribute("usuario", usuarioActual);
		model.addAttribute("requerimientos", requerimientos);
		return "solicitante/mis-requerimientos";
	}
	
	/**
	 * Ver detalle de un requerimiento
	 */
	@GetMapping("/ver/{id}")
	public String verRequerimiento(@PathVariable Long id, Model model) {
		Optional<requerimiento> req = requerimientoService.obtenerRequerimientoPorId(id);
		
		if (req.isPresent()) {
			model.addAttribute("requerimiento", req.get());
			return "solicitante/ver-requerimiento";
		} else {
			return "redirect:/solicitante/mis-requerimientos";
		}
	}
}
