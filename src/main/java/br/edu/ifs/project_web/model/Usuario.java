package br.edu.ifs.project_web.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.edu.ifs.project_web.dto.UsuarioDTO;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "usu_nr_id")
	private int usuNrId;
	@ManyToOne
	@JoinColumn(name = "per_nr_id")
	private Perfil perNrId;
	@Column (name = "usu_tx_nome")
	private String usuTxNome;
	@Column (name = "usu_tx_login")
	private String usuTxLogin;
	@Column (name = "usu_tx_status")
	private char usuTxStatus;
	@Column (name = "usu_tx_senha")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String usuTxSenha;
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column (name = "usu_dt_cadastro")
	private Date usuDtCadastro;
	@Column (name = "usu_tx_token")
	private String usuTxToken;
	
	public UsuarioDTO toUsuario() {
        return UsuarioDTO.builder()
                .usuNrId(usuNrId)
                .usuTxStatus(usuTxStatus)
                .usuTxLogin(usuTxLogin)
                .usuTxNome(usuTxNome)
                .usuDtCadastro(usuDtCadastro)
                .usuTxToken(usuTxToken)
                .perNrId(perNrId)
                .build();
    }


	public String usuToString(Usuario usuario) {
		//GSON
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
	
	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}
	
	@JsonIgnore
	@Override
	public String getPassword() {
		return getUsuTxSenha();
	}

	@JsonIgnore
	@Override
	public String getUsername() {
		return getUsuTxLogin();
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		if(Character.compare(getUsuTxStatus(), 'I')==0) {
			return false;
		}
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}

	
	
}
