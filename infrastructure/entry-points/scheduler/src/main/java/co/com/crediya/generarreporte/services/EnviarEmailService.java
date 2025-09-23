package co.com.crediya.generarreporte.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Service
@RequiredArgsConstructor
public class EnviarEmailService {
    private final SesClient sesClient = SesClient.builder()
            .region(Region.US_EAST_2)
            .build();

    public Mono<SendEmailResponse> enviar(String html, String emisor, String receptor){
        return Mono.fromCallable(() -> {
            SendEmailRequest emailRequest = SendEmailRequest.builder()
                    .source(emisor)
                    .destination(Destination.builder().toAddresses(receptor).build())
                    .message(Message.builder()
                            .subject(Content.builder().data("Reporte solicitudes").charset("UTF-8").build())
                            .body(Body.builder()
                                    .html(Content.builder()
                                            .data(html).charset("UTF-8").build())
                                    .build())
                            .build())
                    .build();
            return sesClient.sendEmail(emailRequest);
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
