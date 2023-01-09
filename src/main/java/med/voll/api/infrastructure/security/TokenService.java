package med.voll.api.infrastructure.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import med.voll.api.domain.usuario.Usuario;

@Service
public class TokenService {
	
	public String gerarToken(Usuario usuario) {
		try {
		    var algoritmo = Algorithm.HMAC256("123456");
		    return JWT.create()
		        .withIssuer("API Voll Med")
		        .withSubject(usuario.getLogin())
		        .withExpiresAt(dataExpiracao())
		        .sign(algoritmo);
		} catch (JWTCreationException exception){
		    throw new RuntimeException("Erro na geração do Token", exception);
		}
	}
	
	public Instant dataExpiracao(){
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}

}
