package br.edu.ifs.project_web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifs.project_web.model.Servico;
import br.edu.ifs.project_web.model.Sistema;
import br.edu.ifs.project_web.repository.SistemaRepository;
@Service
public class SistemaService {

	@Autowired
	SistemaRepository sistemaRepository;
	
	public Sistema findBySisNrId(Integer sisNrId) {
		return sistemaRepository.findBySisNrId(sisNrId);
    }

	public List<Sistema> getTodos() {
        return (List<Sistema>)sistemaRepository.findAll();
    }
	
	public List<Servico> getServicosBySisNrId(int sisNrId) {
        List<Servico> l = new ArrayList<>();
        sistemaRepository.getServicosBySisNrId(sisNrId).forEach(item -> {
            l.add(item);
                });
        return l;
	}
	
	public void alterar(Sistema sistema) {
        Optional<Sistema> opSis = sistemaRepository.findById(sistema.getSisNrId());
        if (opSis.isPresent()) {
        	Sistema sisBD = opSis.get();
        	sisBD.setSisTxNome(sistema.getSisTxNome());
        	sisBD.setSisTxStatus(sistema.getSisTxStatus());
        	sistemaRepository.save(sisBD);
        }
    }
	
	public void criar(Sistema sistema) throws Exception{
		if(sistema == null) {
            throw new Exception("Sistema é obrigatorio");
		}else if(sistema.getSisTxNome() == null) {
			throw new Exception("Nome do sistema é obrigatorio");
		}
		sistemaRepository.save(sistema);
	}
	
}
	