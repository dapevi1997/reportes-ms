package co.com.crediya.model.usuario.gateways;

import co.com.crediya.model.usuario.Usuario;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UsuarioRepository {
    Mono<List<Usuario>> usuarioPorNombreRol(String nombreRol);
}
