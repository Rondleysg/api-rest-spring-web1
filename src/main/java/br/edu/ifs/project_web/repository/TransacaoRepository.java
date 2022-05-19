package br.edu.ifs.project_web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifs.project_web.model.Transacao;

@Repository
public interface TransacaoRepository extends CrudRepository<Transacao, Integer> {


	List<Transacao> findAll();
	Transacao findByTraNrId(int traNrId);
	
	@Query(nativeQuery = true, value = "SELECT transacao.* FROM transacao WHERE ser_nr_id = ?1")
    List<Transacao> getTransByService(int serNrId);
	
	@Query(nativeQuery = true, value = "SELECT transacao.* FROM transacao WHERE per_nr_id = ?1")
    List<Transacao> getTransByPerfil(int perNrId);
	
}
