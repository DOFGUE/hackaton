package usco.edu.co.SistemaGestion.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import usco.edu.co.SistemaGestion.repository.usurioRepository;
import usco.edu.co.SistemaGestion.model.usuario;

@Service
public class usuarioService implements UserDetailsService {
	
	@Autowired
	private usurioRepository repositorioUsuario;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		usuario user = repositorioUsuario.findBynombreUsuario(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
		
		// Forzar la carga de roles dentro de la transacción
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(r -> new SimpleGrantedAuthority("ROLE_" + r.getNombreRol().toUpperCase()))
				.collect(Collectors.toList());
		
		// Si no hay roles, asignar un rol por defecto
		if (authorities.isEmpty()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ESTUDIANTE"));
		}
		
		return new org.springframework.security.core.userdetails.User(
			user.getNombreUsuario(), 
			user.getContrasena(), 
			user.isEnabled(), 
			true,  // accountNonExpired
			true,  // credentialsNonExpired
			true,  // accountNonLocked
			authorities
		);
	}
	
	/**
	 * Obtiene el usuario actualmente autenticado
	 */
	public usuario obtenerUsuarioAutenticado() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		
		return repositorioUsuario.findBynombreUsuario(username)
				.orElse(null);
	}
	
	/**
	 * Obtiene todos los usuarios
	 */
	public List<usuario> obtenerTodosUsuarios() {
		return repositorioUsuario.findAll();
	}
	
	/**
	 * Obtiene un usuario por ID
	 */
	public Optional<usuario> obtenerUsuarioPorId(Long id) {
		return repositorioUsuario.findById(id);
	}
	
	/**
	 * Obtiene un usuario por nombre de usuario
	 */
	public Optional<usuario> obtenerUsuarioPorNombre(String nombreUsuario) {
		return repositorioUsuario.findBynombreUsuario(nombreUsuario);
	}
	
	/**
	 * Crea un nuevo usuario
	 */
	public usuario crearUsuario(usuario nuevoUsuario) {
		// Validar que el nombre de usuario sea único
		if (repositorioUsuario.findBynombreUsuario(nuevoUsuario.getNombreUsuario()).isPresent()) {
			throw new IllegalArgumentException("El nombre de usuario ya existe: " + nuevoUsuario.getNombreUsuario());
		}
		
		// Encriptar la contraseña
		if (nuevoUsuario.getContrasena() != null && !nuevoUsuario.getContrasena().isEmpty()) {
			nuevoUsuario.setContrasena(passwordEncoder.encode(nuevoUsuario.getContrasena()));
		}
		
		// Usuario habilitado por defecto
		if (nuevoUsuario.getRoles() == null || nuevoUsuario.getRoles().isEmpty()) {
			// Se espera que se asignen roles después
		}
		
		return repositorioUsuario.save(nuevoUsuario);
	}
	
	/**
	 * Actualiza un usuario existente
	 */
	public usuario actualizarUsuario(Long id, usuario usuarioActualizado) {
		return repositorioUsuario.findById(id)
				.map(usuario -> {
					usuario.setNombreUsuario(usuarioActualizado.getNombreUsuario());
					// Solo actualizar contraseña si se proporciona una nueva
					if (usuarioActualizado.getContrasena() != null && !usuarioActualizado.getContrasena().isEmpty()) {
						usuario.setContrasena(passwordEncoder.encode(usuarioActualizado.getContrasena()));
					}
					if (usuarioActualizado.getRoles() != null) {
						usuario.setRoles(usuarioActualizado.getRoles());
					}
					return repositorioUsuario.save(usuario);
				})
				.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));
	}
	
	/**
	 * Elimina un usuario
	 */
	public void eliminarUsuario(Long id) {
		if (!repositorioUsuario.existsById(id)) {
			throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
		}
		repositorioUsuario.deleteById(id);
	}
	
	/**
	 * Habilita un usuario
	 */
	public usuario habilitarUsuario(Long id) {
		return repositorioUsuario.findById(id)
				.map(usuario -> {
					// Implement enable logic based on your usuario model
					return repositorioUsuario.save(usuario);
				})
				.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));
	}
	
	/**
	 * Deshabilita un usuario
	 */
	public usuario deshabilitarUsuario(Long id) {
		return repositorioUsuario.findById(id)
				.map(usuario -> {
					// Implement disable logic based on your usuario model
					return repositorioUsuario.save(usuario);
				})
				.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));
	}

}
