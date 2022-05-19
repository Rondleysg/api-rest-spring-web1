package br.edu.ifs.project_web.model;

import javax.persistence.*;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "perfil")
public class Perfil {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "per_nr_id")
	private int perNrId;
	@Column(name = "per_tx_nome")
	private String perTxNome;
	@Column(name = "per_tx_status")
	private char perTxStatus;
	
	
	
}
