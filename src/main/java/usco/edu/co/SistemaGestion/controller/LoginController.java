package usco.edu.co.SistemaGestion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	/**
	 * Página de inicio - redirige a dashboard si está autenticado
	 */
	@GetMapping("/")
	public String index() {
		return "redirect:/dashboard";
	}
	
	/**
	 * Página de login
	 */
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	/**
	 * Dashboard - página principal después del login
	 */
	@GetMapping("/dashboard")
	public String dashboard() {
		return "dashboard";
	}
}
