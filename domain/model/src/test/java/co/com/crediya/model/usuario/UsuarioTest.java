package co.com.crediya.model.usuario;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void testBuilderAndGetters() {
        LocalDate fecha = LocalDate.of(1990, 1, 1);

        Usuario usuario = Usuario.builder()
                .idUser(1L)
                .name("Daniel")
                .lastName("Pérez")
                .email("daniel@crediya.com")
                .birthday(fecha)
                .address("Calle 123")
                .documentId("123456789")
                .phone("3001234567")
                .baseSalary(BigDecimal.valueOf(2500))
                .idRole(2L)
                .build();

        assertEquals(1L, usuario.getIdUser());
        assertEquals("Daniel", usuario.getName());
        assertEquals("Pérez", usuario.getLastName());
        assertEquals("daniel@crediya.com", usuario.getEmail());
        assertEquals(fecha, usuario.getBirthday());
        assertEquals("Calle 123", usuario.getAddress());
        assertEquals("123456789", usuario.getDocumentId());
        assertEquals("3001234567", usuario.getPhone());
        assertEquals(BigDecimal.valueOf(2500), usuario.getBaseSalary());
        assertEquals(2L, usuario.getIdRole());
    }

    @Test
    void testSettersAndNoArgsConstructor() {
        Usuario usuario = new Usuario();
        usuario.setName("Sofía");
        usuario.setEmail("sofia@crediya.com");

        assertEquals("Sofía", usuario.getName());
        assertEquals("sofia@crediya.com", usuario.getEmail());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDate fecha = LocalDate.of(2000, 5, 15);

        Usuario usuario = new Usuario(
                10L,
                "Andrés",
                "Ramírez",
                "andres@crediya.com",
                fecha,
                "Carrera 45",
                "987654321",
                "3109876543",
                BigDecimal.valueOf(3000),
                3L
        );

        assertEquals(10L, usuario.getIdUser());
        assertEquals("Andrés", usuario.getName());
        assertEquals("Ramírez", usuario.getLastName());
        assertEquals("andres@crediya.com", usuario.getEmail());
        assertEquals(fecha, usuario.getBirthday());
        assertEquals("Carrera 45", usuario.getAddress());
        assertEquals("987654321", usuario.getDocumentId());
        assertEquals("3109876543", usuario.getPhone());
        assertEquals(BigDecimal.valueOf(3000), usuario.getBaseSalary());
        assertEquals(3L, usuario.getIdRole());
    }

    @Test
    void testToBuilder() {
        Usuario original = Usuario.builder()
                .idUser(5L)
                .name("Carlos")
                .lastName("Gómez")
                .build();

        Usuario copia = original.toBuilder()
                .name("Carla") // solo cambio el nombre
                .build();

        assertEquals(5L, copia.getIdUser());
        assertEquals("Carla", copia.getName());
        assertEquals("Gómez", copia.getLastName());
    }
}
