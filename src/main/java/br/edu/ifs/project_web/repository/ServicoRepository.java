package br.edu.ifs.project_web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifs.project_web.model.Servico;
import br.edu.ifs.project_web.model.Transacao;


@Repository
public interface ServicoRepository extends CrudRepository<Servico, Integer> {

	List<Servico> findAll();
	Servico findBySerNrId(int serNrId);
	
	@Query(nativeQuery = true, value = "SELECT transacao.* FROM transacao WHERE ser_nr_id = ?1")
    List<Transacao> getTransByService(int serNrId);
	
	@Query(nativeQuery = true, value = "SELECT servico.* FROM servico WHERE sis_nr_id = ?1")
    List<Servico> getServicesBySistema(int sisNrId);
	
}