package br.com.infnet.Assessment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @GetMapping("/")
    public String index() {
        return "Hello, World!";
    }

    @PostMapping("/post")
    public ResponseEntity<String> post(@RequestBody PostRequest request) {
        if (request.getName() == null || request.getAge() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nome ou idade inválidos");
        }

        return ResponseEntity.ok("Requisição recebida com sucesso");
    }

    @GetMapping("/get/{nome}/{idade}")
    public ResponseEntity<String> get(@PathVariable("nome") String nome, @PathVariable("idade") Integer idade) {
        if (nome == null || idade == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nome ou idade inválidos");
        }

        return ResponseEntity.ok("Requisição recebida com sucesso");
    }

    @GetMapping("/api")
    public ResponseEntity<ApiResponse> api() {
        try {
            // Chamada à API externa
            String response = new HttpUtils().get("https://api.openweathermap.org/data/2.5/weather?q={RioDeJaneiro}&appid={14bef796d80dc04dfef85fea36046e38}");

            // Conversão da resposta JSON para um objeto java
            ApiResponse apiResponse = new ObjectMapper().readValue(response, ApiResponse.class);

            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
