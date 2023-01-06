package med.voll.api.domain.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
		
		@NotBlank(message = "Este campo não pode estar em branco")
		String logradouro, 
		String numero, 
		String complemento,
		
		@NotBlank(message = "Este campo não pode estar em branco")
		@Pattern(regexp = "\\d{8}", message = "Este campo deve conter 8 caracteres")
		String cep,
		
		@NotBlank(message = "Este campo não pode estar em branco")
		String bairro,
		
		@NotBlank(message = "Este campo não pode estar em branco")
		String cidade,
		
		@NotBlank(message = "Este campo não pode estar em branco")
		String uf) {

}
