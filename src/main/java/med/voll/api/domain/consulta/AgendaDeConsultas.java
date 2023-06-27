package med.voll.api.domain.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;

@Service
public class AgendaDeConsultas {
	
	@Autowired
	private ConsultaRepository consultaRepository;
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private PacienteRepository pacienteRepository;

	public void agendar(DadosAgendamentoConsulta dados) {
		if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
			throw new ValidacaoException("O medico informado não está cadastrado no sistema.");
		}
		if(!pacienteRepository.existsById(dados.idPaciente())) {
			throw new ValidacaoException("O medico informado não está cadastrado no sistema.");
		}
		
		var medico = escolherMedico(dados);
		var paciente = pacienteRepository.findById(dados.idPaciente()).get();
		var consulta = new Consulta(null, medico, paciente, dados.data());
		
		consultaRepository.save(consulta);
	}
	
	private Medico escolherMedico(DadosAgendamentoConsulta dados) {
		if(dados.idMedico() != null) {
			return medicoRepository.getReferenceById(dados.idMedico());
		}
		if(dados.especialidade() == null) {
			throw new ValidacaoException("Obrigatório informar a especialidade quando não selecionado um médico específico.");
		}
		
		return medicoRepository.escolherMedicoAleatorioPorEspecialidadeEDisponibilidade(dados.especialidade(), dados.data());
	}
	
}
