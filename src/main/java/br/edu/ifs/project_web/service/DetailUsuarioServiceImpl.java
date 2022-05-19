package br.edu.ifs.project_web.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifs.project_web.model.Usuario;
import br.edu.ifs.project_web.repository.UsuarioRepository;

@Service
public class DetailUsuarioServiceImpl implements UserDetailsService {

	
	private final UsuarioRepository repository;
	
	public DetailUsuarioServiceImpl(UsuarioRepository repository) {
		this.repository=repository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

		Optional<Usuario> usuario = repository.findByUsuTxLogin(login);
		if(usuario.isEmpty()) {
			throw new UsernameNotFoundException("Usuario "+login+" não encontrado");
		}
		
		return usuario.get();
	}

}
