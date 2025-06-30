package commity.web.controller;

import commity.web.service.CommitterUpdateResponse;
import commity.web.service.SseEmitterService;
import commity.web.service.WebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SseController {

    private final SseEmitterService sseEmitterService;
    private final WebhookService webhookService;


    @GetMapping(path = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() throws IOException {
        String emitterId = UUID.randomUUID().toString();
        return sseEmitterService.subscribe(emitterId);
    }

    @GetMapping("/test-sse")
    public void testSSE() {
        List<CommitterUpdateResponse> list = webhookService.getCommitterUpdateResponses();
        sseEmitterService.broadcast(list);
    }
}
