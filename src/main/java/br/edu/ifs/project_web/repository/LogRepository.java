package br.edu.ifs.project_web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifs.project_web.model.Log;

@Repository
public interface LogRepository extends CrudRepository<Log, Integer> {

	List<Log> findAll();
	
	
	@Query(nativeQuery = true, value = "SELECT * FROM log WHERE log_nr_id=?1")
    List<Log> findByUsuNrId(int usuNrId);
	
	@Query(nativeQuery = true, value = "SELECT * FROM log WHERE log_nr_id=(SELECT id FROM usuario WHERE usu_nr_login=?1)")
    List<Log> findByUsuNrLogin(String usuNrLogin);



}
