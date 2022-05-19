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
@Table(name = "servico")
public class Servico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "ser_nr_id")
	private int serNrId;
	@ManyToOne
	@JoinColumn(name = "sis_nr_id")
	private Sistema sisNrId;
	@Column(name = "ser_tx_nome")
	private String serTxNome;
	@Column(name = "ser_tx_url")
	private String serTxUrl;
	@Column(name = "ser_tx_status")
	private char serTxStatus;
}
