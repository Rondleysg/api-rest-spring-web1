package br.edu.ifs.project_web.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifs.project_web.dto.UsuarioDTO;
import br.edu.ifs.project_web.dto.UsuarioLogin;
import br.edu.ifs.project_web.model.Usuario;
import br.edu.ifs.project_web.service.LogService;
import br.edu.ifs.project_web.service.TokenService;
import br.edu.ifs.project_web.service.UsuarioService;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UsuarioService usuService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	LogService logService;
	
	@PostMapping
	public UsuarioDTO auth(@RequestBody @Validated UsuarioLogin usuarioLogin){
		int idLog;
		try {
			idLog=usuService.getByLogin(usuarioLogin.getUsuTxLogin()).getUsuNrId();
			logService.criar(idLog, "Tentou logar no sistema.");
		}catch (NoSuchElementException e){
			idLog=0;
			logService.criar(idLog, "Tentiva falha de login no sistema.");
		}
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(usuarioLogin.getUsuTxLogin(), usuarioLogin.getUsuTxSenha());
		Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		
		String token = tokenService.generateToken(authentication);
		Usuario usu = usuService.getByLoginUsu(usuarioLogin.getUsuTxLogin());
		
		usuService.setToken(usu, token);
		logService.criar(usu.getUsuNrId(), "Fez login no sistema.");

		/*HttpHeaders header = new HttpHeaders();
		header.set("Content-Type", "application/json");
		header.setBearerAuth(token);*/

		return usu.toUsuario();
	}

	
	
	
}
