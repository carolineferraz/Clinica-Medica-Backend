package med.voll.api.domain.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
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
	
	@Autowired
	private List<ValidadorAgendamentoDeConsulta> validadores;

	public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
		if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
			throw new ValidacaoException("O medico informado não existe.");
		}
		if(!pacienteRepository.existsById(dados.idPaciente())) {
			throw new ValidacaoException("O paciente informado não existe.");
		}
		
		validadores.forEach(v -> v.validar(dados));
		
		var medico = escolherMedico(dados);
		if(medico == null) {
			throw new ValidacaoException("Não existe médico disponível nessa data.");
		}
		
		var paciente = pacienteRepository.findById(dados.idPaciente()).get();
		var consulta = new Consulta(null, medico, paciente, dados.data(), null);
		
		consultaRepository.save(consulta);
		
		return new DadosDetalhamentoConsulta(consulta);
	}
	
	public void cancelar(DadosCancelamentoConsulta dados) {
	    if (!consultaRepository.existsById(dados.idConsulta())) {
	        throw new ValidacaoException("Id da consulta informado não existe!");
	    }

	    var consulta = consultaRepository.getReferenceById(dados.idConsulta());
	    consulta.cancelar(dados.motivo());
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
