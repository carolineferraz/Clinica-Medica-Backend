package med.voll.api.domain.medico;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.Paciente;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private TestEntityManager em;

	@Test
	@DisplayName("Deveria devolver null quando único Medico cadastrado não está disponível na data")
	void escolherMedicoAleatorioPorEspecialidadeEDisponibilidadeCenario1() {
		//given ou arrange:
		var proximaSegundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);
		//when ou act:
		var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.NEUROLOGIA);
		var paciente = cadastrarPaciente("Paciente", "paciente@email.com", "00000000000");
		cadastrarConsulta(medico, paciente, proximaSegundaAs10);
		var medicoLivre = medicoRepository.escolherMedicoAleatorioPorEspecialidadeEDisponibilidade(Especialidade.NEUROLOGIA, proximaSegundaAs10);	
		//then ou assert:
		assertThat(medicoLivre).isNull();
	}
	
	@Test
	@DisplayName("Deveria devolver Medico quando há medico para essa especialidade disponível na data")
	void escolherMedicoAleatorioPorEspecialidadeEDisponibilidadeCenario2() {
		//given ou arrange:
		var proximaSegundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);	
		var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
		//when ou act:
		var medicoLivre = medicoRepository.escolherMedicoAleatorioPorEspecialidadeEDisponibilidade(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
		//then ou assert:
		assertThat(medicoLivre).isEqualTo(medico);
	}
	
	private Consulta cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
		var consulta = em.persist(new Consulta(null, medico, paciente, data, null));
		return consulta;
	}
	
	private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
		var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
		em.persist(medico);
		return medico;
	}
	
	private Paciente cadastrarPaciente(String nome, String email, String cpf) {
		var paciente = new Paciente(dadosPaciente(nome, email, cpf));
		em.persist(paciente);
		return paciente;
	}
	
	private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
		return new DadosCadastroMedico(
				nome, 
				email, 
				"81999999999", 
				crm,
				especialidade,
				dadosEndereco()
		);
	}
	
	private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
		return new DadosCadastroPaciente(
				nome, 
				email, 
				"81999999999", 
				cpf,
				dadosEndereco()
		);
	}
	
	private DadosEndereco dadosEndereco() {
		return new DadosEndereco(
				"rua xpto", 
				"000", 
				"Apt. 000", 
				"00000000",
				"Bairro", 
				"Recife", 
				"PE"
		);
	}
}
