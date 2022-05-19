package br.edu.ifs.project_web.repository;

import org.springframework.stereotype.Repository;
import br.edu.ifs.project_web.model.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;


@Repository
public interface UsuarioRepository  extends CrudRepository<Usuario, Integer> {
	
	//Usuario findByUsuTxLogin(String login);
    Usuario findByUsuTxLoginAndUsuTxSenha(String login, String senha);
    public Optional<Usuario> findByUsuTxLogin(String login);
    Usuario findByUsuNrId(int usuNrId);

    @Query(nativeQuery = true, value = "select usu.* from usuario usu " +
            "inner join perfil peu on peu.per_nr_id = usu.per_nr_id " +
            "where peu.per_nr_id=?1")
    List<Usuario> getByPerfil(int perNrId);

    /*@Query("select u from usuario u " +
            "inner join perfil peu on peu.usuNrId = u.usuNrId " +
            "where peu.perNrId=?1")*/
    @Query(nativeQuery = true, value = "select usu.* from usuario usu " +
            "inner join perfil peu on peu.per_nr_id = usu.per_nr_id " +
            "where peu.per_nr_id=?1")
    List<Usuario> getByPerfilJPA(int perNrId);
	
}

