package br.edu.ifs.project_web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifs.project_web.model.Transacao;
import br.edu.ifs.project_web.repository.TransacaoRepository;

@Service
public class TransacaoService {

	@Autowired
	TransacaoRepository transacaoRepository;
	
	public Transacao findByTraNrId(Integer traNrId) {
		return transacaoRepository.findByTraNrId(traNrId);
    }

	public List<Transacao> getTodos() {
        return (List<Transacao>)transacaoRepository.findAll();
    }
	
	public List<Transacao> getTransByService(int serNrId) {
        List<Transacao> l = new ArrayList<>();
        transacaoRepository.getTransByService(serNrId).forEach(item -> {
            l.add(item);
                });
        return l;
    }
	
	public List<Transacao> getTransByPerfil(int perNrId) {
        List<Transacao> l = new ArrayList<>();
        transacaoRepository.getTransByPerfil(perNrId).forEach(item -> {
            l.add(item);
                });
        return l;
    }
	
	public void criar(Transacao transacao) throws Exception{
		if(transacao == null) {
            throw new Exception("Transaçao é obrigatorio");
		}else if(transacao.getPerNrId() == null) {
            throw new Exception("Perfil da transação é obrigatorio");
		}else if(transacao.getTraTxNome() == null) {
			throw new Exception("Nome da transação é obrigatorio");
		}else if(transacao.getTraTxUrl() == null) {
			throw new Exception("Url da transação é obrigatorio");
		}else if(transacao.getSerNrId() == null) {
			throw new Exception("Serviço da transação é obrigatorio");
		}
		transacaoRepository.save(transacao);
	}
	
}
	