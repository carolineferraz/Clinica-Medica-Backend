package med.voll.api.domain.medico;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(
		
		@NotBlank(message = "Este campo não pode estar em branco")
		String nome, 
		
		@NotBlank(message = "Este campo não pode estar em branco")
		@Email(message = "Este campo não contém um formato de e-mail válido")
		@Column(unique=true)
		String email,
		
		@NotBlank(message = "Este campo não pode estar em branco")
		String telefone,
		
		@NotBlank(message = "Este campo não pode estar em branco")
		@Pattern(regexp = "\\d{4,6}", message = "Este campo deve conter de 4 a 6 caracteres")
		String crm, 
		
		@NotNull(message = "Este campo não pode estar em branco")
		Especialidade especialidade,
		
		@NotNull(message = "Este campo não pode estar em branco")
		@Valid
		DadosEndereco endereco) {
	
}
