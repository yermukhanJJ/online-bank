package kz.silk.onlinebank.controller;

import kz.silk.onlinebank.domain.dto.request.LoginRequestDto;
import kz.silk.onlinebank.domain.dto.request.RegisterRequestDto;
import kz.silk.onlinebank.domain.dto.response.LoginResponseDto;
import kz.silk.onlinebank.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(AuthController.CONTROLLER_PATH)
public class AuthController {

    public static final String CONTROLLER_PATH = "/auth";

    private final AuthService authService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequestDto requestDto) {
        log.debug("register({})", requestDto);
        authService.register(requestDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto requestData) {
        log.debug("login({})", requestData);
        return ResponseEntity.ok(authService.login(requestData));
    }

    @GetMapping(value = "/confirm")
    public ResponseEntity<Void> confirmRegistration(@RequestParam(name = "email") String email,
                                                    @RequestParam(name = "token") String token) {
        log.debug("confirmRegistration({}, {})", email, token);
        authService.confirmRegistration(email, token);
        return ResponseEntity.noContent().build();
    }
}
