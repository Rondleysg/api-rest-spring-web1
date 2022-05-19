package br.edu.ifs.project_web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "transacao")
public class Transacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tran_nr_id")
	private int traNrId;
	@ManyToOne
	@JoinColumn(name = "per_nr_id")
	private Perfil perNrId;
	@ManyToOne
	@JoinColumn(name = "ser_nr_id")
	private Servico serNrId;
	@Column(name = "tra_tx_nome")
	private String traTxNome;
	@Column(name = "tra_tx_status")
	private char traTxStatus;
	@Column(name = "tra_tx_url")
	private String traTxUrl;
	
}
