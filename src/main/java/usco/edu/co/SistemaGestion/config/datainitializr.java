package usco.edu.co.SistemaGestion.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import usco.edu.co.SistemaGestion.model.categoria;
import usco.edu.co.SistemaGestion.model.estado;
import usco.edu.co.SistemaGestion.model.roles;
import usco.edu.co.SistemaGestion.model.usuario;
import usco.edu.co.SistemaGestion.model.validacion;
import usco.edu.co.SistemaGestion.repository.categoriaRepository;
import usco.edu.co.SistemaGestion.repository.estadoRepository;
import usco.edu.co.SistemaGestion.repository.rolesRepository;
import usco.edu.co.SistemaGestion.repository.usurioRepository;
import usco.edu.co.SistemaGestion.repository.validacionRepository;

@Component
public class datainitializr implements CommandLineRunner {

	@Autowired
	private rolesRepository rolesRepo;
	
	@Autowired
	private categoriaRepository categoriaRepo;
	
	@Autowired
	private estadoRepository estadoRepo;
	
	@Autowired
	private validacionRepository validacionRepo;
	
	@Autowired
	private usurioRepository usuarioRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void run(String... args) throws Exception {
		// Inicializar roles
		inicializarRoles();
		
		// Inicializar categorías
		inicializarCategorias();
		
		// Inicializar estados
		inicializarEstados();
		
		// Inicializar validaciones
		inicializarValidaciones();
		
		// Inicializar usuarios
		inicializarUsuarios();
	}
	
	private void inicializarRoles() {
		// Crear roles solo si no existen
		if (rolesRepo.findByNombreRol("SOLICITANTE").isEmpty()) {
			roles rolSolicitante = new roles("SOLICITANTE");
			rolesRepo.save(rolSolicitante);
			System.out.println("✓ Rol 'SOLICITANTE' creado");
		}
		
		if (rolesRepo.findByNombreRol("DOCENTE").isEmpty()) {
			roles rolDocente = new roles("DOCENTE");
			rolesRepo.save(rolDocente);
			System.out.println("✓ Rol 'DOCENTE' creado");
		}
		
		if (rolesRepo.findByNombreRol("FUNCIONARIO").isEmpty()) {
			roles rolFuncionario = new roles("FUNCIONARIO");
			rolesRepo.save(rolFuncionario);
			System.out.println("✓ Rol 'FUNCIONARIO' creado");
		}
		
		if (rolesRepo.findByNombreRol("ESTUDIANTE_AUTORIZADO").isEmpty()) {
			roles rolEstudianteAutorizado = new roles("ESTUDIANTE_AUTORIZADO");
			rolesRepo.save(rolEstudianteAutorizado);
			System.out.println("✓ Rol 'ESTUDIANTE_AUTORIZADO' creado");
		}
		
		if (rolesRepo.findByNombreRol("OFICINA_MANTENIMIENTO").isEmpty()) {
			roles rolOficinaMantenimiento = new roles("OFICINA_MANTENIMIENTO");
			rolesRepo.save(rolOficinaMantenimiento);
			System.out.println("✓ Rol 'OFICINA_MANTENIMIENTO' creado");
		}
		
		if (rolesRepo.findByNombreRol("JEFE_PROGRAMADOR_MANTENIMIENTO").isEmpty()) {
			roles rolJefeMantenimiento = new roles("JEFE_PROGRAMADOR_MANTENIMIENTO");
			rolesRepo.save(rolJefeMantenimiento);
			System.out.println("✓ Rol 'JEFE_PROGRAMADOR_MANTENIMIENTO' creado");
		}
		
		if (rolesRepo.findByNombreRol("OPERARIO").isEmpty()) {
			roles rolOperario = new roles("OPERARIO");
			rolesRepo.save(rolOperario);
			System.out.println("✓ Rol 'OPERARIO' creado");
		}
	}
	
	private void inicializarCategorias() {
		// Crear categorías solo si no existen
		if (categoriaRepo.findByNombreCategoria("Aires Acondicionados").isEmpty()) {
			categoria cat1 = new categoria("Aires Acondicionados");
			categoriaRepo.save(cat1);
			System.out.println("✓ Categoría 'Aires Acondicionados' creada");
		}
		
		if (categoriaRepo.findByNombreCategoria("Redes Electricas").isEmpty()) {
			categoria cat2 = new categoria("Redes Electricas");
			categoriaRepo.save(cat2);
			System.out.println("✓ Categoría 'Redes Electricas' creada");
		}
		
		if (categoriaRepo.findByNombreCategoria("Tomas Luminarias").isEmpty()) {
			categoria cat3 = new categoria("Tomas Luminarias");
			categoriaRepo.save(cat3);
			System.out.println("✓ Categoría 'Tomas Luminarias' creada");
		}

	}
	
	private void inicializarUsuarios() {
		// Crear usuario SOLICITANTE (DOCENTE)
		if (usuarioRepo.findBynombreUsuario("docente1").isEmpty()) {
			usuario usuario1 = new usuario();
			usuario1.setNombreUsuario("docente1");
			usuario1.setContrasena(passwordEncoder.encode("password123"));
			usuario1.setEnabled(true);
			
			roles rolDocente = rolesRepo.findByNombreRol("DOCENTE").orElse(null);
			if (rolDocente != null) {
				usuario1.getRoles().add(rolDocente);
			}
			
			usuarioRepo.save(usuario1);
			System.out.println("✓ Usuario 'docente1' (DOCENTE) creado");
		}
		
		// Crear usuario SOLICITANTE (FUNCIONARIO)
		if (usuarioRepo.findBynombreUsuario("funcionario1").isEmpty()) {
			usuario usuario2 = new usuario();
			usuario2.setNombreUsuario("funcionario1");
			usuario2.setContrasena(passwordEncoder.encode("password123"));
			usuario2.setEnabled(true);
			
			roles rolFuncionario = rolesRepo.findByNombreRol("FUNCIONARIO").orElse(null);
			if (rolFuncionario != null) {
				usuario2.getRoles().add(rolFuncionario);
			}
			
			usuarioRepo.save(usuario2);
			System.out.println("✓ Usuario 'funcionario1' (FUNCIONARIO) creado");
		}
		
		// Crear usuario SOLICITANTE (ESTUDIANTE AUTORIZADO)
		if (usuarioRepo.findBynombreUsuario("estudiante1").isEmpty()) {
			usuario usuario3 = new usuario();
			usuario3.setNombreUsuario("estudiante1");
			usuario3.setContrasena(passwordEncoder.encode("password123"));
			usuario3.setEnabled(true);
			
			roles rolEstudianteAutorizado = rolesRepo.findByNombreRol("ESTUDIANTE_AUTORIZADO").orElse(null);
			if (rolEstudianteAutorizado != null) {
				usuario3.getRoles().add(rolEstudianteAutorizado);
			}
			
			usuarioRepo.save(usuario3);
			System.out.println("✓ Usuario 'estudiante1' (ESTUDIANTE_AUTORIZADO) creado");
		}
		
		// Crear usuario OFICINA DE MANTENIMIENTO
		if (usuarioRepo.findBynombreUsuario("oficina_mantenimiento").isEmpty()) {
			usuario usuario4 = new usuario();
			usuario4.setNombreUsuario("oficina_mantenimiento");
			usuario4.setContrasena(passwordEncoder.encode("password123"));
			usuario4.setEnabled(true);
			
			roles rolOficinaMantenimiento = rolesRepo.findByNombreRol("OFICINA_MANTENIMIENTO").orElse(null);
			if (rolOficinaMantenimiento != null) {
				usuario4.getRoles().add(rolOficinaMantenimiento);
			}
			
			usuarioRepo.save(usuario4);
			System.out.println("✓ Usuario 'oficina_mantenimiento' (OFICINA_MANTENIMIENTO) creado");
		}
		
		// Crear usuario JEFE PROGRAMADOR DE MANTENIMIENTO
		if (usuarioRepo.findBynombreUsuario("jefe_mantenimiento").isEmpty()) {
			usuario usuario5 = new usuario();
			usuario5.setNombreUsuario("jefe_mantenimiento");
			usuario5.setContrasena(passwordEncoder.encode("password123"));
			usuario5.setEnabled(true);
			
			roles rolJefeMantenimiento = rolesRepo.findByNombreRol("JEFE_PROGRAMADOR_MANTENIMIENTO").orElse(null);
			if (rolJefeMantenimiento != null) {
				usuario5.getRoles().add(rolJefeMantenimiento);
			}
			
			usuarioRepo.save(usuario5);
			System.out.println("✓ Usuario 'jefe_mantenimiento' (JEFE_PROGRAMADOR_MANTENIMIENTO) creado");
		}
		
		// Crear usuario OPERARIO
		if (usuarioRepo.findBynombreUsuario("operario1").isEmpty()) {
			usuario usuario6 = new usuario();
			usuario6.setNombreUsuario("operario1");
			usuario6.setContrasena(passwordEncoder.encode("password123"));
			usuario6.setEnabled(true);
			
			roles rolOperario = rolesRepo.findByNombreRol("OPERARIO").orElse(null);
			if (rolOperario != null) {
				usuario6.getRoles().add(rolOperario);
			}
			
			usuarioRepo.save(usuario6);
			System.out.println("✓ Usuario 'operario1' (OPERARIO) creado");
		}
	}
	
	private void inicializarEstados() {
		// Crear estados solo si no existen
		if (estadoRepo.findByNombreEstado("NUEVO").isEmpty()) {
			estado est1 = new estado("NUEVO");
			estadoRepo.save(est1);
			System.out.println("✓ Estado 'NUEVO' creado");
		}
		
		if (estadoRepo.findByNombreEstado("EN_VALIDACION").isEmpty()) {
			estado est2 = new estado("EN_VALIDACION");
			estadoRepo.save(est2);
			System.out.println("✓ Estado 'EN_VALIDACION' creado");
		}
		
		if (estadoRepo.findByNombreEstado("ASIGNADO").isEmpty()) {
			estado est3 = new estado("ASIGNADO");
			estadoRepo.save(est3);
			System.out.println("✓ Estado 'ASIGNADO' creado");
		}
		
		if (estadoRepo.findByNombreEstado("EN_EJECUCION").isEmpty()) {
			estado est4 = new estado("EN_EJECUCION");
			estadoRepo.save(est4);
			System.out.println("✓ Estado 'EN_EJECUCION' creado");
		}
		
		if (estadoRepo.findByNombreEstado("EN_ESPERA").isEmpty()) {
			estado est5 = new estado("EN_ESPERA");
			estadoRepo.save(est5);
			System.out.println("✓ Estado 'EN_ESPERA' creado");
		}
		
		if (estadoRepo.findByNombreEstado("SOLUCIONADO").isEmpty()) {
			estado est6 = new estado("SOLUCIONADO");
			estadoRepo.save(est6);
			System.out.println("✓ Estado 'SOLUCIONADO' creado");
		}
		
		if (estadoRepo.findByNombreEstado("CERRADO").isEmpty()) {
			estado est6 = new estado("CERRADO");
			estadoRepo.save(est6);
			System.out.println("✓ Estado 'CERRADO' creado");
		}
		
		if (estadoRepo.findByNombreEstado("REABIERTO").isEmpty()) {
			estado est6 = new estado("REABIERTO");
			estadoRepo.save(est6);
			System.out.println("✓ Estado 'REABIERTO' creado");
		}
	}
	
	private void inicializarValidaciones() {
		// Crear validaciones solo si no existen
		if (validacionRepo.findByTipoValidacion("CRITICA").isEmpty()) {
			validacion val1 = new validacion("CRITICA");
			validacionRepo.save(val1);
			System.out.println("✓ Validación 'CRITICA' creada");
		}
		
		if (validacionRepo.findByTipoValidacion("ALTA").isEmpty()) {
			validacion val2 = new validacion("ALTA");
			validacionRepo.save(val2);
			System.out.println("✓ Validación 'ALTA' creada");
		}
		
		if (validacionRepo.findByTipoValidacion("MEDIA").isEmpty()) {
			validacion val3 = new validacion("MEDIA");
			validacionRepo.save(val3);
			System.out.println("✓ Validación 'MEDIA' creada");
		}
		
		if (validacionRepo.findByTipoValidacion("BAJA").isEmpty()) {
			validacion val4 = new validacion("BAJA");
			validacionRepo.save(val4);
			System.out.println("✓ Validación 'BAJA' creada");
		}
	}
}
