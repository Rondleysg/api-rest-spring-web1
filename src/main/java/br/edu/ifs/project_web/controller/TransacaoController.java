package br.edu.ifs.project_web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifs.project_web.dto.UsuarioDTO;
import br.edu.ifs.project_web.model.Transacao;
import br.edu.ifs.project_web.repository.TransacaoRepository;
import br.edu.ifs.project_web.service.LogService;
import br.edu.ifs.project_web.service.TransacaoService;
import br.edu.ifs.project_web.service.UsuarioService;
import br.edu.ifs.project_web.util.ResponseDefault;

@RestController
@RequestMapping("/api/transacao")
public class TransacaoController {
	
    ResponseDefault response = new ResponseDefault();
    
	private String logExecucao;
	
	@Autowired
	LogService logService;
	@Autowired
	TransacaoService traService;
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	TransacaoRepository repository;
    
	
	@GetMapping("/listarTodos")
    public ResponseEntity<List<Transacao>> listarTodos(HttpServletRequest request) {
    	UsuarioDTO usu=usuarioService.getUsuHeader(request);
    	if(usu.getPerNrId().getPerNrId()<4) {
    		logService.criar(usu.getUsuNrId(), "Tentou listar todos as transações");
    		return null;
    	}
    	logService.criar(usu.getUsuNrId(), "Listou todos as transações");
        return ResponseEntity.ok((List<Transacao>)repository.findAll());
	}
	
	@GetMapping(value = "/transacaoById")
    public Object transacaoGetById(HttpServletRequest request, @RequestBody Integer traNrId) {
		UsuarioDTO usu=usuarioService.getUsuHeader(request);
    	/*if(usu.getPerNrId().getPerNrId()<2) {
    		logService.criar(usu.getUsuNrId(), "Tentou buscar uma transação.");
    		return null;
    	}*/
    	logService.criar(usu.getUsuNrId(), "Buscou a transação: "+traNrId);
        return traService.findByTraNrId(traNrId);
    }
	
	@GetMapping(value = "/transacaoByServico")
    public Object transacaoGetByServico(HttpServletRequest request, @RequestBody Integer serNrId) {
		UsuarioDTO usu=usuarioService.getUsuHeader(request);
    	if(usu.getPerNrId().getPerNrId()<2) {
    		logService.criar(usu.getUsuNrId(), "Tentou buscar as transações de um serviço.");
    		return null;
    	}
    	logService.criar(usu.getUsuNrId(), "Buscou as transações do serviço: "+serNrId);
        return traService.getTransByService(serNrId);
    }
	
	
	
	@PostMapping(value = "/createTransacao")
    public Object criarTransacao(@RequestBody Transacao transacao, HttpServletRequest request) throws Throwable {
		UsuarioDTO usu=usuarioService.getUsuHeader(request);
    	if(usu.getPerNrId().getPerNrId()<4) {
    		logService.criar(usu.getUsuNrId(), "Tentou criar uma transação.");
    		response.setCodigo(400);
            response.setMensagem("Rank insuficiente");
            response.setValue(false);
    	}
        try {
            traService.criar(transacao);
            response.setValue(true);
            response.setCodigo(200);
            response.setMensagem("Transação: "+transacao.getTraTxNome()+" criada.");
            logExecucao="Nova transação criada: " + transacao.getTraTxNome();
            logService.criar(usu.getUsuNrId(), logExecucao);
        } catch (Exception e) {
            response.setCodigo(400);
            e.printStackTrace();
            response.setMensagem(e.getMessage());
            response.setValue(false);
            logExecucao=("Tentativa de criar nova transação com erro. ");
            logService.criar(usu.getUsuNrId(), logExecucao);
        }
        return response;
    }
	
	@PutMapping(value = "/updateTransacao")
    public Object alterarTransacao(@RequestBody Transacao transacao, HttpServletRequest request) {
		UsuarioDTO usu=usuarioService.getUsuHeader(request);
    	if(usu.getPerNrId().getPerNrId()<4) {
    		logService.criar(usu.getUsuNrId(), "Tentou alterar uma transação");
    		return null;
    	}
    	logService.criar(usu.getUsuNrId(), "Alterou a transação: "+transacao.getTraTxNome());
        try {
        	traService.alterar(transacao);
        	response.setValue(true);
            response.setCodigo(200);
            response.setMensagem("Transação alterada.");
        } catch (Exception e) {
            response.setCodigo(400);
            e.printStackTrace();
            System.out.println(e.getMessage());
            response.setMensagem(e.getMessage());
            response.setValue(false);
        }
        return response;
    }
	
	
}
