package br.edu.ifs.project_web.filter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.edu.ifs.project_web.model.Usuario;
import br.edu.ifs.project_web.repository.UsuarioRepository;
import br.edu.ifs.project_web.service.TokenService;


public class TokenAuthenticationFilter extends OncePerRequestFilter {
	
	private final TokenService tokenService;
	private final UsuarioRepository repository;
	
	public TokenAuthenticationFilter(TokenService tokenService, UsuarioRepository repository) {
		this.tokenService = tokenService;
		this.repository = repository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String tokenFromHeader = getTokenFromHeader(request);
		boolean tokenValid = tokenService.isTokenValid(tokenFromHeader);
		if(tokenValid) {
			this.authenticate(tokenFromHeader);
		}
		filterChain.doFilter(request, response);
	}

	private void authenticate(String tokenFromHeader) {
		String login = tokenService.getTokenLogin(tokenFromHeader);
		Optional<Usuario> optionalUser = repository.findByUsuTxLogin(login);
		if(optionalUser.isPresent()) {
			Usuario user = optionalUser.get();
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}
	}

	private String getTokenFromHeader(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.substring(7, token.length());
	}

}
