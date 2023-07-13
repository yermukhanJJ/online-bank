package kz.silk.onlinebank.service;

import kz.silk.onlinebank.domain.dto.request.LoginRequestDto;
import kz.silk.onlinebank.domain.dto.request.RegisterRequestDto;
import kz.silk.onlinebank.domain.dto.response.LoginResponseDto;
import kz.silk.onlinebank.domain.exceptions.BadRequestException;
import org.springframework.lang.NonNull;

public interface AuthService {

    void register(@NonNull RegisterRequestDto requestDto);

    LoginResponseDto login(@NonNull LoginRequestDto requestDto);

    void confirmRegistration(@NonNull String email,
                                  @NonNull String token) throws BadRequestException;
}
