package co.com.crediya.usecase.obtenerusuariosadministradores;

import co.com.crediya.model.reporte.Constantes;
import co.com.crediya.model.usuario.Usuario;
import co.com.crediya.model.usuario.gateways.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class ObtenerUsuariosAdministradoresUseCase {
    private final UsuarioRepository usuarioRepository;

    public Mono<List<Usuario>> apply() {
        return usuarioRepository.usuarioPorNombreRol(Constantes.ROL_ADMINISTRADOR_NOMBRE);
    }
}
