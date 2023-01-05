package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroPaciente(
		
		@NotBlank(message = "Este campo não pode estar em branco")
		String nome,
		
		@NotBlank(message = "Este campo não pode estar em branco")
		@Email
		String email,
		
		@NotBlank(message = "Este campo não pode estar em branco")
		String telefone,
		
		@NotBlank(message = "Este campo não pode estar em branco")
		@Pattern(regexp = "\\d{11}", message = "Este campo deve conter 11 caracteres")
		String cpf,
		
		@NotNull(message = "Este campo não pode estar em branco")
		@Valid
		DadosEndereco endereco) {

}
