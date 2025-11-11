package usco.edu.co.SistemaGestion.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import usco.edu.co.SistemaGestion.repository.usurioRepository;
import usco.edu.co.SistemaGestion.model.usuario;

@Service
@Transactional
public class usuarioService implements UserDetailsService {
	
	@Autowired
	private usurioRepository repositorioUsuario;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		usuario user = repositorioUsuario.findBynombreUsuario(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
		
		// Cargar roles del usuario
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		if (user.getRoles() != null && !user.getRoles().isEmpty()) {
			authorities = user.getRoles().stream()
					.map(r -> new SimpleGrantedAuthority("ROLE_" + r.getNombreRol().toUpperCase()))
					.collect(Collectors.toList());
		} else {
			// Rol por defecto si no hay roles asignados
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
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
	@Transactional(readOnly = true)
	public usuario obtenerUsuarioAutenticado() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || !auth.isAuthenticated()) {
			return null;
		}
		String username = auth.getName();
		
		return repositorioUsuario.findBynombreUsuario(username)
				.orElse(null);
	}
	
	/**
	 * Obtiene todos los usuarios
	 */
	@Transactional(readOnly = true)
	public List<usuario> obtenerTodosUsuarios() {
		return repositorioUsuario.findAll();
	}
	
	/**
	 * Obtiene un usuario por ID
	 */
	@Transactional(readOnly = true)
	public Optional<usuario> obtenerUsuarioPorId(Long id) {
		return repositorioUsuario.findById(id);
	}
	
	/**
	 * Obtiene un usuario por nombre de usuario
	 */
	@Transactional(readOnly = true)
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
		
		return repositorioUsuario.save(nuevoUsuario);
	}
	
	/**
	 * Actualiza un usuario existente
	 */
	public usuario actualizarUsuario(Long id, usuario usuarioActualizado) {
		return repositorioUsuario.findById(id)
				.map(usuario -> {
					usuario.setNombreUsuario(usuarioActualizado.getNombreUsuario());
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
}