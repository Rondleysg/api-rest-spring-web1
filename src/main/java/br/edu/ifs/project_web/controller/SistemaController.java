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
import br.edu.ifs.project_web.model.Sistema;
import br.edu.ifs.project_web.repository.SistemaRepository;
import br.edu.ifs.project_web.service.LogService;
import br.edu.ifs.project_web.service.SistemaService;
import br.edu.ifs.project_web.service.UsuarioService;
import br.edu.ifs.project_web.util.ResponseDefault;

@RestController
@RequestMapping("/api/sistema")
public class SistemaController {
	
    ResponseDefault response = new ResponseDefault();
    
	private String logExecucao;
	
	@Autowired
	LogService logService;
	@Autowired
	SistemaService sisService;
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	SistemaRepository repository;
    
	
	@GetMapping("/listarTodos")
    public ResponseEntity<List<Sistema>> listarTodos(HttpServletRequest request) {
    	UsuarioDTO usu=usuarioService.getUsuHeader(request);
    	if(usu.getPerNrId().getPerNrId()<4) {
    		logService.criar(usu.getUsuNrId(), "Tentou listar todos os sistemas");
    		return null;
    	}
    	logService.criar(usu.getUsuNrId(), "Listou todos os sistemas");
        return ResponseEntity.ok((List<Sistema>)repository.findAll());
	}
	
	@GetMapping(value = "/sistemaById")
    public Object sistemaGetById(HttpServletRequest request, @RequestBody Integer sisNrId) {
		UsuarioDTO usu=usuarioService.getUsuHeader(request);
    	/*if(usu.getPerNrId().getPerNrId()<2) {
    		logService.criar(usu.getUsuNrId(), "Tentou buscar um sistema");
    		return null;
    	}*/
    	logService.criar(usu.getUsuNrId(), "Buscou o sistema: "+sisNrId);
        return sisService.findBySisNrId(sisNrId);
    }
	
	@PostMapping(value = "/createSistema")
    public Object criarPerfil(@RequestBody Sistema sistema, HttpServletRequest request) throws Throwable {
		UsuarioDTO usu=usuarioService.getUsuHeader(request);
    	if(usu.getPerNrId().getPerNrId()<4) {
    		logService.criar(usu.getUsuNrId(), "Tentou criar um sistema.");
    		response.setCodigo(400);
            response.setMensagem("Rank insuficiente");
            response.setValue(false);
    	}
        try {
            sisService.criar(sistema);
            response.setValue(true);
            response.setCodigo(200);
            response.setMensagem("Sistema: "+sistema.getSisTxNome()+" criado.");
            logExecucao="Novo sistema criado: " + sistema.getSisTxNome();
            logService.criar(usu.getUsuNrId(), logExecucao);
        } catch (Exception e) {
            response.setCodigo(400);
            e.printStackTrace();
            response.setMensagem(e.getMessage());
            response.setValue(false);
            logExecucao=("Tentativa de criar novo sistema com erro. ");
            logService.criar(usu.getUsuNrId(), logExecucao);
        }
        return response;
    }
	
	@PutMapping(value = "/updateSistema")
    public Object alterarSistema(@RequestBody Sistema sistema, HttpServletRequest request) {
		UsuarioDTO usu=usuarioService.getUsuHeader(request);
    	if(usu.getPerNrId().getPerNrId()<4) {
    		logService.criar(usu.getUsuNrId(), "Tentou alterar um sistema");
    		return null;
    	}
    	logService.criar(usu.getUsuNrId(), "Alterou o sistema: "+sistema.getSisTxNome());
        try {
        	sisService.alterar(sistema);
        	response.setValue(true);
            response.setCodigo(200);
            response.setMensagem("Sistema alterado.");
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
