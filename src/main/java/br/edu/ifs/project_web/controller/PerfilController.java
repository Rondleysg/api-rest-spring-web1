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
import br.edu.ifs.project_web.model.Perfil;
import br.edu.ifs.project_web.repository.PerfilRepository;
import br.edu.ifs.project_web.service.LogService;
import br.edu.ifs.project_web.service.PerfilService;
import br.edu.ifs.project_web.service.UsuarioService;
import br.edu.ifs.project_web.util.ResponseDefault;

@RestController
@RequestMapping("/api/perfil")
public class PerfilController {
	
    ResponseDefault response = new ResponseDefault();
    
	private String logExecucao;
	
	@Autowired
	LogService logService;
	@Autowired
	PerfilService perfilService;
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	PerfilRepository repository;
    
	
	@GetMapping("/listarTodos")
    public ResponseEntity<List<Perfil>> listarTodos(HttpServletRequest request) {
    	UsuarioDTO usu=usuarioService.getUsuHeader(request);
    	if(usu.getPerNrId().getPerNrId()<2) {
    		logService.criar(usu.getUsuNrId(), "Tentou listar todos os perfis");
    		return null;
    	}
    	logService.criar(usu.getUsuNrId(), "Listou todos os perfis");
        return ResponseEntity.ok((List<Perfil>)repository.findAll());
	}
	
	@GetMapping(value = "/perfilById")
    public Object PerfilgetById(HttpServletRequest request, @RequestBody Integer perNrId) {
		UsuarioDTO usu=usuarioService.getUsuHeader(request);
    	/*if(usu.getPerNrId().getPerNrId()<2) {
    		logService.criar(usu.getUsuNrId(), "Tentou buscar um perfil");
    		return null;
    	}*/
    	logService.criar(usu.getUsuNrId(), "Buscou o perfil: "+perNrId);
        return perfilService.findByPerNrId(perNrId);
    }
	
	@PostMapping(value = "/createPerfil")
    public Object criarPerfil(@RequestBody Perfil perfil, HttpServletRequest request) throws Throwable {
		UsuarioDTO usu=usuarioService.getUsuHeader(request);
    	if(usu.getPerNrId().getPerNrId()<2) {
    		logService.criar(usu.getUsuNrId(), "Tentou criar um perfil.");
    		response.setCodigo(400);
            response.setMensagem("Rank insuficiente");
            response.setValue(false);
    	}
        try {
            perfilService.criar(perfil);
            response.setValue(true);
            response.setCodigo(200);
            response.setMensagem("Perfil: "+perfil.getPerTxNome()+" criado.");
            logExecucao="Novo perfil criado: " + perfil.getPerTxNome();
            logService.criar(usu.getUsuNrId(), logExecucao);
        } catch (Exception e) {
            response.setCodigo(400);
            e.printStackTrace();
            response.setMensagem(e.getMessage());
            response.setValue(false);
            logExecucao=("Tentativa de criar novo perfil com erro. ");
            logService.criar(usu.getUsuNrId(), logExecucao);
        }
        return response;
    }
	
	@PutMapping(value = "/updatePerfil")
    public Object alterarPerfil(@RequestBody Perfil perfil, HttpServletRequest request) {
		UsuarioDTO usu=usuarioService.getUsuHeader(request);
    	if(usu.getPerNrId().getPerNrId()<2) {
    		logService.criar(usu.getUsuNrId(), "Tentou alterar um perfil");
    		return null;
    	}
    	if(perfilService.findByPerNrId(perfil.getPerNrId())==null) {
    		response.setCodigo(400);
            response.setMensagem("ID do perfil não encontrado");
            response.setValue(false);
            return response;
    	}
    	logService.criar(usu.getUsuNrId(), "Alterou o perfil: "+perfil.getPerTxNome());
        try {
            perfilService.alterar(perfil);
            response.setValue(true);
            response.setCodigo(200);
            response.setMensagem("Perfil alterado.");
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
