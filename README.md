<h1 align="center"> Back-end de Clínica Médica </h1>




<h2>Descrição:</h2>
<p>CRUD da API de uma clínica médica fictícia feita como estudo, desenvolvida em Java utilizando o framework Spring Boot e o Maven como gerenciador de dependências. Dentre as dependências utilizadas, utilizei o Flyway Migration para adicionar o controle de versão do banco de dados, o Spring Data JPA para persistir/recuperar dados com o padrão Repository e o Bean Validation para criar validações para os dados que serão passados para a API. No desenvolvimento da API, foram utilizadas as classes record do Java para criar padrões DTO (Data Transfer Object), para representar os dados que chegam e saem da API, dando maior controle sobre quais dados da entidade serão considerados em cada requisição HTTP. Para o método de delete do CRUD, foi utilizada a exclusão lógica, ou "soft delete", que, ao invés de apagar o objeto permanentemente do banco de dados, altera para false o atributo 'ativo' do objeto, e a requisição get foi setadada para buscar apenas os objetos que estão com o atributo 'ativo' igual a true.</p>




