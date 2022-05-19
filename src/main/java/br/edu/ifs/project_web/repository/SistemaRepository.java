package br.edu.ifs.project_web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifs.project_web.model.Servico;
import br.edu.ifs.project_web.model.Sistema;


@Repository
public interface SistemaRepository extends CrudRepository<Sistema, Integer> {


	List<Sistema> findAll();
	Sistema findBySisNrId(int sisNrId);
	
	@Query(nativeQuery = true, value = "SELECT servico.* FROM servico WHERE sis_nr_id = ?1")
    List<Servico> getServicosBySisNrId(int sisNrId);
	
	
}
