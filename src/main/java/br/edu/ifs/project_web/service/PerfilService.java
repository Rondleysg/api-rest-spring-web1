package br.edu.ifs.project_web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifs.project_web.model.Perfil;
import br.edu.ifs.project_web.repository.PerfilRepository;

@Service
public class PerfilService {

	@Autowired
	PerfilRepository perfilRepository;
	
	public Perfil findByPerNrId(Integer perNrId) {
		return perfilRepository.findByPerNrId(perNrId);
    }

	public List<Perfil> getTodos() {
        return (List<Perfil>)perfilRepository.findAll();
    }
	
	public void criar(Perfil perfil) throws Exception{
		if(perfil == null) {
            throw new Exception("Perfil é obrigatorio");
		}else if(perfil.getPerTxNome() == null) {
			throw new Exception("Nome do perfil é obrigatorio");
		}
		perfilRepository.save(perfil);
	}
	
	public void alterar(Perfil perfil) throws Exception{
		Optional<Perfil> opPer = perfilRepository.findById(perfil.getPerNrId());
		if (opPer.isPresent()) {
            Perfil perBD = opPer.get();
            perBD.setPerTxNome(perfil.getPerTxNome());
            perBD.setPerTxStatus(perfil.getPerTxStatus());
            perfilRepository.save(perBD);
        }
	}
	
}
