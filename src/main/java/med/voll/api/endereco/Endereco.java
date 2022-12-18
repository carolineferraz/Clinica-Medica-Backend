package med.voll.api.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

	private String logradouro; 
	private String numero;
	private String complemento; 
	private String cep; 
	private String bairro; 
	private String cidade; 
	private String uf;
	
	public Endereco(DadosEndereco dados) {
		this.logradouro = dados.logradouro();
		this.numero = dados.numero();
		this.complemento = dados.complemento();
		this.cep = dados.cep();
		this.bairro = dados.bairro();
		this.cidade = dados.cidade();
		this.uf = dados.uf();
	}
}
