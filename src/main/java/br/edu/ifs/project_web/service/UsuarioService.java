package br.edu.ifs.project_web.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifs.project_web.dto.UsuarioDTO;
import br.edu.ifs.project_web.model.Usuario;
import br.edu.ifs.project_web.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

@Service
public class UsuarioService {
	
	@Autowired
    private UsuarioRepository usuarioRepository;
	
	
    public List<Usuario> getTodos() {
        return (List<Usuario>)usuarioRepository.findAll();
    }

    public void criar(Usuario usuario) throws Exception {
        if (usuario == null) {
            throw new Exception("Usuario é obrigatorio");
        } else if (usuario.getUsuTxNome() == null || usuario.getUsuTxNome().trim().isEmpty()) {
            throw new Exception("Nome do Usuario é obrigatorio");
        } else if (usuario.getUsuTxLogin().trim().isEmpty()) {
            throw new Exception("Login do Usuario é obrigatorio");
        } else if (usuario.getUsuTxSenha().trim().isEmpty()) {
            throw new Exception("Senha do Usuario é obrigatorio");
        }
        usuarioRepository.save(usuario);
    }

    public void alterar(Usuario usuario) {
        Optional<Usuario> opUsu = usuarioRepository.findById(usuario.getUsuNrId());
        if (opUsu.isPresent()) {
            Usuario usuBD = opUsu.get();
            usuBD.setUsuTxStatus(usuario.getUsuTxStatus());
            usuBD.setUsuTxLogin(usuario.getUsuTxLogin());
            usuBD.setUsuTxSenha(usuario.getUsuTxSenha());
            usuBD.setUsuTxNome(usuario.getUsuTxNome());
            usuarioRepository.save(usuBD);
        }
    }

    public UsuarioDTO getByLogin(String login) {
        return usuarioRepository.findByUsuTxLogin(login).get().toUsuario();
    }
    
    
    public Usuario getByLoginUsu(String login) {
        return usuarioRepository.findByUsuTxLogin(login).get();
    }
    
    public List<UsuarioDTO> getByPerfil(int perNrId) {
        List<UsuarioDTO> l = new ArrayList<>();
        usuarioRepository.getByPerfil(perNrId).forEach(item -> {
            l.add(item.toUsuario());
                });
        return l;
    }

    public List<Usuario> getByPerfilJPa(int perNrId) {
        return usuarioRepository.getByPerfilJPA(perNrId);
    }

    public Usuario autenticacao(String login, String senha) {
        return usuarioRepository.findByUsuTxLoginAndUsuTxSenha(login, senha);
    }
    
    public void setToken(Usuario usuario, String token) {
        Optional<Usuario> opUsu = usuarioRepository.findById(usuario.getUsuNrId());
        if (opUsu.isPresent()) {
            Usuario usuBD = opUsu.get();
            usuBD.setUsuTxToken(token);
            usuarioRepository.save(usuBD);
        }
    }
    
    public String usuToString(UsuarioDTO usuario) {
    	
    	String toString="{\r\n"
    			+ "        \"usuNrId\": "+usuario.getUsuNrId()+",\r\n"
    			+ "        \"perNrId\": "+usuario.getPerNrId().getPerNrId()+"\r\n"
    			+ "        \"usuTxNome\": \""+usuario.getUsuTxNome()+"\",\r\n"
    			+ "        \"usuTxLogin\": \""+usuario.getUsuTxLogin()+"\",\r\n"
    			+ "        \"usuTxStatus\": \""+usuario.getUsuTxStatus()+"\",\r\n"
    			+ "        \"usuDtCadastro\": \""+usuario.getUsuDtCadastro()+"\",\r\n"
    			+ "        \"usuTxToken\": \""+usuario.getUsuTxToken()+"\"\r\n"
    			+ "    }";
    	return toString;
    }

    public UsuarioDTO getUsuHeader(HttpServletRequest request) {
    	String token = request.getHeader("Authorization");
    	token.replace("Bearer ", "");
    	String[] chunks = token.split("\\.");
    	Base64.Decoder decoder = Base64.getUrlDecoder();
    	String payload = new String(decoder.decode(chunks[1]));
    	JSONObject jsonObject = new JSONObject(payload);
    	UsuarioDTO usu=getByLogin(jsonObject.getString("sub"));
    	return usu;
    }
    
}
