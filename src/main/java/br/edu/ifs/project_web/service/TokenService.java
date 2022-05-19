package br.edu.ifs.project_web.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.edu.ifs.project_web.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	public static final int TOKEN_EXPIRACAO = 600_000;
	public static final String TOKEN_SENHA="530c4535-715c-4cfd-926b-1f8cd1727dc7";
	
	@Autowired
	private UsuarioService service;
	
	public String generateToken(Authentication authentication) {
		Usuario usuario = (Usuario) authentication.getPrincipal();
		Date exp = new Date(System.currentTimeMillis()+TOKEN_EXPIRACAO);
		String token = Jwts.builder().setIssuer("ProjectWeb")
                             .setSubject(usuario.getUsuTxLogin())
                             .setIssuedAt(new Date())
				             .setExpiration(exp)
                             .signWith(SignatureAlgorithm.HS256, TOKEN_SENHA).compact();
		service.setToken(usuario, token);
		return token;
	}
	
	public boolean isTokenValid(String token) {
		try {
			Jwts.parser().setSigningKey(TOKEN_SENHA).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public String getTokenLogin(String token) {
		Claims body = Jwts.parser().setSigningKey(TOKEN_SENHA).parseClaimsJws(token).getBody();
		return String.valueOf(body.getSubject());
	}
	
}
