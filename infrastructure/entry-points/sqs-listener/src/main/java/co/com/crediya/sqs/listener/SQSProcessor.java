package co.com.crediya.sqs.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Function;

//@Service
@RequiredArgsConstructor
public class SQSProcessor implements Function<Message, Mono<Void>> {
    // private final MyUseCase myUseCase;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> apply(Message message) {
        try {
            Long cantidadPrestamosAprobados = objectMapper.readValue(message.body(), Long.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return Mono.empty();
        // return myUseCase.doAny(message.body());
    }
}
