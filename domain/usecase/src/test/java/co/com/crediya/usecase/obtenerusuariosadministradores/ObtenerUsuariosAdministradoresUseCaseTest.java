package co.com.crediya.usecase.obtenerusuariosadministradores;

import co.com.crediya.model.reporte.Constantes;
import co.com.crediya.model.usuario.Usuario;
import co.com.crediya.model.usuario.gateways.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ObtenerUsuariosAdministradoresUseCaseTest {

    private UsuarioRepository usuarioRepository;
    private ObtenerUsuariosAdministradoresUseCase useCase;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        useCase = new ObtenerUsuariosAdministradoresUseCase(usuarioRepository);
    }

    @Test
    void debeRetornarListaUsuariosAdministradores() {
        Usuario admin = Usuario.builder()
                .idUser(1L)
                .name("Admin")
                .lastName("System")
                .email("admin@mail.com")
                .birthday(LocalDate.of(1990, 1, 1))
                .address("Default Address")
                .documentId("1000000000")
                .phone("3000000000")
                .baseSalary(BigDecimal.valueOf(5000000))
                .idRole(1L)
                .build();

        when(usuarioRepository.usuarioPorNombreRol(eq(Constantes.ROL_ADMINISTRADOR_NOMBRE)))
                .thenReturn(Mono.just(List.of(admin)));

        StepVerifier.create(useCase.apply())
                .expectNextMatches(lista -> lista.size() == 1 && lista.get(0).getEmail().equals("admin@mail.com"))
                .verifyComplete();

        verify(usuarioRepository, times(1)).usuarioPorNombreRol(Constantes.ROL_ADMINISTRADOR_NOMBRE);
    }

    @Test
    void debeRetornarListaVaciaCuandoNoHayAdmins() {
        when(usuarioRepository.usuarioPorNombreRol(eq(Constantes.ROL_ADMINISTRADOR_NOMBRE)))
                .thenReturn(Mono.just(List.of()));

        StepVerifier.create(useCase.apply())
                .expectNextMatches(List::isEmpty)
                .verifyComplete();

        verify(usuarioRepository, times(1)).usuarioPorNombreRol(Constantes.ROL_ADMINISTRADOR_NOMBRE);
    }
}
