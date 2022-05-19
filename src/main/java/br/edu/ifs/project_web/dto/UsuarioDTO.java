package br.edu.ifs.project_web.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.edu.ifs.project_web.model.Perfil;
import br.edu.ifs.project_web.model.Usuario;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private int usuNrId;
    private String usuTxNome;
    private String usuTxLogin;
    private char usuTxStatus;
    @JsonIgnore
    private String usuTxSenha;
    private String usuTxToken;
    private Date usuDtCadastro;
    private Perfil perNrId;
    
    public Usuario toUsuario() {
        return Usuario.builder()
                .usuNrId(usuNrId)
                .usuTxStatus(usuTxStatus)
                .usuTxSenha(usuTxSenha)
                .usuTxLogin(usuTxLogin)
                .usuTxNome(usuTxNome)
                .usuDtCadastro(usuDtCadastro)
                .usuTxToken(usuTxToken)
                .perNrId(perNrId)
                .build();
    }
}