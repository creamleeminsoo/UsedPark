package me.leeminsoo.usedpark.controller.api.user;

import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.dto.user.CreateAccessTokenRequestDTO;
import me.leeminsoo.usedpark.dto.user.CreateAccessTokenResponseDTO;
import me.leeminsoo.usedpark.service.user.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenApiController {
    private final TokenService tokenService;

    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponseDTO> createNewAccessToken(@RequestBody CreateAccessTokenRequestDTO dto) {
        String newAccessToken = tokenService.createNewAccessToken(dto.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponseDTO(newAccessToken));
    }
}
