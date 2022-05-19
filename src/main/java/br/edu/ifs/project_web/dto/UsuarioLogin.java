package br.edu.ifs.project_web.dto;


import br.edu.ifs.project_web.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioLogin {
	
    private String usuTxLogin;
    private String usuTxSenha;
    
    public Usuario toUsuario() {
        return Usuario.builder()
                .usuTxSenha(usuTxSenha)
                .usuTxLogin(usuTxLogin)
                .build();
    }
    
}
