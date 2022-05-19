package br.edu.ifs.project_web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "sistema")
public class Sistema {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "sis_nr_id")
	private int sisNrId;
	@Column(name = "sis_tx_nome")
	private String sisTxNome;
	@Column(name = "sis_tx_status")
	private char sisTxStatus;
}
