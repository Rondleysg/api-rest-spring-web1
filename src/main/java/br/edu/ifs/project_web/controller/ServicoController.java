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
import br.edu.ifs.project_web.model.Servico;
import br.edu.ifs.project_web.repository.ServicoRepository;
import br.edu.ifs.project_web.service.LogService;
import br.edu.ifs.project_web.service.ServicoService;
import br.edu.ifs.project_web.service.UsuarioService;
import br.edu.ifs.project_web.util.ResponseDefault;

@RestController
@RequestMapping("/api/servico")
public class ServicoController {
	
    ResponseDefault response = new ResponseDefault();
    
	private String logExecucao;
	
	@Autowired
	LogService logService;
	@Autowired
	ServicoService serService;
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	ServicoRepository repository;
    
	
	@GetMapping("/listarTodos")
    public ResponseEntity<List<Servico>> listarTodos(HttpServletRequest request) {
    	UsuarioDTO usu=usuarioService.getUsuHeader(request);
    	if(usu.getPerNrId().getPerNrId()<4) {
    		logService.criar(usu.getUsuNrId(), "Tentou listar todos os serviços");
    		return null;
    	}
    	logService.criar(usu.getUsuNrId(), "Listou todos os serviços");
        return ResponseEntity.ok((List<Servico>)repository.findAll());
	}
	
	@GetMapping(value = "/servicoById")
    public Object servicoGetById(HttpServletRequest request, @RequestBody Integer serNrId) {
		UsuarioDTO usu=usuarioService.getUsuHeader(request);
    	/*if(usu.getPerNrId().getPerNrId()<2) {
    		logService.criar(usu.getUsuNrId(), "Tentou buscar um serviço");
    		return null;
    	}*/
    	logService.criar(usu.getUsuNrId(), "Buscou o serviço: "+serNrId);
        return serService.findBySerNrId(serNrId);
    }
	
	@GetMapping(value = "/servicoBySistema")
    public Object servicoGetBySistema(HttpServletRequest request, @RequestBody Integer sisNrId) {
		UsuarioDTO usu=usuarioService.getUsuHeader(request);
    	if(usu.getPerNrId().getPerNrId()<2) {
    		logService.criar(usu.getUsuNrId(), "Tentou buscar os serviço de um sistema.");
    		return null;
    	}
    	logService.criar(usu.getUsuNrId(), "Buscou os serviços do sistema: "+sisNrId);
        return serService.getServicesBySistema(sisNrId);
    }
	
	@GetMapping(value = "/transacaoByServico")
    public Object GetTransacaoByServico(HttpServletRequest request, @RequestBody Integer serNrId) {
		UsuarioDTO usu=usuarioService.getUsuHeader(request);
    	if(usu.getPerNrId().getPerNrId()<2) {
    		logService.criar(usu.getUsuNrId(), "Tentou buscar as transações de um serviço.");
    		return null;
    	}
    	logService.criar(usu.getUsuNrId(), "Buscou as transações do serviço: "+serNrId);
        return serService.getTransByService(serNrId);
    }
	
	@PostMapping(value = "/createServico")
    public Object criarServico(@RequestBody Servico servico, HttpServletRequest request) throws Throwable {
		UsuarioDTO usu=usuarioService.getUsuHeader(request);
    	if(usu.getPerNrId().getPerNrId()<4) {
    		logService.criar(usu.getUsuNrId(), "Tentou criar um serviço.");
    		response.setCodigo(400);
            response.setMensagem("Rank insuficiente");
            response.setValue(false);
    	}
        try {
            serService.criar(servico);
            response.setValue(true);
            response.setCodigo(200);
            response.setMensagem("Serviço: "+servico.getSerTxNome()+" criado.");
            logExecucao="Novo serviço criado: " + servico.getSerTxNome();
            logService.criar(usu.getUsuNrId(), logExecucao);
        } catch (Exception e) {
            response.setCodigo(400);
            e.printStackTrace();
            response.setMensagem(e.getMessage());
            response.setValue(false);
            logExecucao=("Tentativa de criar novo serviço com erro. ");
            logService.criar(usu.getUsuNrId(), logExecucao);
        }
        return response;
    }
	
	@PutMapping(value = "/updateServico")
    public Object alterarServico(@RequestBody Servico servico, HttpServletRequest request) {
		UsuarioDTO usu=usuarioService.getUsuHeader(request);
    	if(usu.getPerNrId().getPerNrId()<4) {
    		logService.criar(usu.getUsuNrId(), "Tentou alterar um serviço");
    		return null;
    	}
    	logService.criar(usu.getUsuNrId(), "Alterou o serviço: "+servico.getSerTxNome());
        try {
        	serService.alterar(servico);
        	response.setValue(true);
            response.setCodigo(200);
            response.setMensagem("Serviço alterado.");
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