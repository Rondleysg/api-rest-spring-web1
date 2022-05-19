package br.edu.ifs.project_web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifs.project_web.model.Log;
import br.edu.ifs.project_web.repository.LogRepository;

@Service
public class LogService {

	@Autowired
	LogRepository logRepository;
	Log log= new Log();
	
	public List<Log> getTodos() {
        return (List<Log>)logRepository.findAll();
    }
	
	public List<Log> findByUsuNrId(int usuNrId) {
        List<Log> l = new ArrayList<>();
        logRepository.findByUsuNrId(usuNrId).forEach(item -> {
            l.add(item);
            });
        return l;
    }
	
	public List<Log> findByUsuNrLogin(String usuNrLogin) {
        List<Log> l = new ArrayList<>();
        logRepository.findByUsuNrLogin(usuNrLogin).forEach(item -> {
            l.add(item);
            });
        return l;
    }
	
	public void criar(Integer usuNrId, String logTxExecucao) {
		try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/projeto_web";
            String usuario = "root";
            String senha = "";
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, usuario, senha);
            
            //Insercao
            int nrId = usuNrId;
            String TxExecucao = logTxExecucao;
            Date DtExecucao = new Date(System.currentTimeMillis());
            String sqlInsert = "insert into log (usu_nr_id, log_tx_execucao, log_dt_execucao) values (?, ? ,?)";
            PreparedStatement psInsert = con.prepareStatement(sqlInsert);
            psInsert.setObject(1, nrId);
            psInsert.setObject(2, TxExecucao);
            psInsert.setObject(3, DtExecucao);
            psInsert.execute();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
}
