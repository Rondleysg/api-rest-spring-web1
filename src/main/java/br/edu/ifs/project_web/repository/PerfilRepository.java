package br.edu.ifs.project_web.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifs.project_web.model.Perfil;

@Repository
public interface PerfilRepository extends CrudRepository<Perfil, Integer> {

	List<Perfil> findAll();
	Perfil findByPerNrId(int perNrId);
	
	


}
