package br.edu.ifs.project_web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifs.project_web.model.Servico;
import br.edu.ifs.project_web.model.Transacao;
import br.edu.ifs.project_web.repository.ServicoRepository;

@Service
public class ServicoService {

	@Autowired
	ServicoRepository servicoRepository;
	
	public Servico findBySerNrId(Integer serNrId) {
		return servicoRepository.findBySerNrId(serNrId);
    }

	public List<Servico> getTodos() {
        return (List<Servico>)servicoRepository.findAll();
    }
	
	public List<Transacao> getTransByService(int serNrId) {
        List<Transacao> l = new ArrayList<>();
        servicoRepository.getTransByService(serNrId).forEach(item -> {
            l.add(item);
                });
        return l;
    }
	
	public List<Servico> getServicesBySistema(int sisNrId) {
        List<Servico> l = new ArrayList<>();
        servicoRepository.getServicesBySistema(sisNrId).forEach(item -> {
            l.add(item);
                });
        return l;
    }
	
	public void criar(Servico servico) throws Exception{
		if(servico == null) {
            throw new Exception("Serviço é obrigatorio");
		}else if(servico.getSisNrId() == null) {
            throw new Exception("Sistema do serviço é obrigatorio");
		}else if(servico.getSerTxNome() == null) {
			throw new Exception("Nome do serviço é obrigatorio");
		}else if(servico.getSerTxUrl() == null) {
			throw new Exception("Url do serviço é obrigatorio");
		}
		servicoRepository.save(servico);
	}
	
	
}