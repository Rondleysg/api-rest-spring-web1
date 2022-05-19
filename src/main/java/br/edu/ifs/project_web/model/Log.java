package br.edu.ifs.project_web.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "log")
public class Log {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "log_nr_id")
	private int logNrId;
	@Column(name = "usu_nr_id")
	private int usuNrId;
	@Column(name = "log_tx_execucao")
	private String logTxExecucao;
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "log_dt_execucao")
	private Date logDtExecucao;
	
}
