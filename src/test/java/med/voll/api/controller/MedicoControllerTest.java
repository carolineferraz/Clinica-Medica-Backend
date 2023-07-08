package med.voll.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.DadosCadastroMedico;
import med.voll.api.domain.medico.DadosDetalhamentoMedico;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {
	
	@Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosCadastroMedico> dadosCadastroMedicoJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoMedico> dadosDetalhamentoMedicoJson;

    @MockBean
    private MedicoRepository repository;

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
    @WithMockUser
	void cadastrarCenerio1() throws Exception {
		var resposta = mvc.perform(post("/medicos")).andReturn().getResponse();
		
		assertThat(resposta.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
    
    @Test
    @DisplayName("Deveria devolver codigo http 200 quando informacoes estao validas")
    @WithMockUser
    void cadastrarCenario2() throws Exception {
    	var dadosCadastro = new DadosCadastroMedico(
    								"Nome Medico", 
    								"medico.email@vollmed.com", 
    								"81999999999", 
    								"123456", 
    								Especialidade.CARDIOLOGIA, 
    								dadosEndereco());
    	
    	when(repository.save(any())).thenReturn(new Medico(dadosCadastro));
    	
    	var resposta = mvc.perform(post("/medicos")
    						.contentType(MediaType.APPLICATION_JSON)
    						.content(dadosCadastroMedicoJson.write(dadosCadastro).getJson()))
    						.andReturn().getResponse();
    	
    	var dadosDetalhamento = new DadosDetalhamentoMedico(
                null,
                dadosCadastro.nome(),
                dadosCadastro.email(),
                dadosCadastro.crm(),
                dadosCadastro.telefone(),
                dadosCadastro.especialidade(),
                new Endereco(dadosCadastro.endereco())
        );
    	
    	var jsonEsperado = dadosDetalhamentoMedicoJson.write(dadosDetalhamento).getJson();
    	
    	assertThat(resposta.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    	assertThat(resposta.getContentAsString()).isEqualTo(jsonEsperado);
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
