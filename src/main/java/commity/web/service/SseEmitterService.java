package commity.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class SseEmitterService {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private static final long TIMEOUT = 10 * 60 * 1000L;
    private final ObjectMapper objectMapper;

    public SseEmitter subscribe(String emitterId) throws IOException {
        SseEmitter emitter = new SseEmitter(TIMEOUT);

        emitter.onTimeout(
                () -> {
                    emitter.complete();
                    emitters.remove(emitterId);
        });

        emitter.onError(e -> emitters.remove(emitterId));
        emitter.onCompletion(() -> emitters.remove(emitterId));

        emitter.send("connected", MediaType.TEXT_PLAIN);

        emitters.put(emitterId, emitter);
        return emitter;
    }

    public void broadcast(List<CommitterUpdateResponse> eventPayload) {
   		emitters.forEach((emitterId, emitter) -> {
   			try {
                for (CommitterUpdateResponse payload : eventPayload) {
                    String data = objectMapper.writeValueAsString(payload);
                    emitter.send(data, MediaType.APPLICATION_JSON);
                }
   			} catch (IOException e) {
                emitters.remove(emitterId);
                emitter.complete();
                log.error("SSE broadcasting failed", e);
            }
   		});
   	}
}
