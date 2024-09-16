package me.leeminsoo.usedpark.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.leeminsoo.usedpark.config.jwt.JwtFactory;
import me.leeminsoo.usedpark.config.jwt.JwtProperties;

import me.leeminsoo.usedpark.domain.user.RefreshToken;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.dto.user.CreateAccessTokenRequestDTO;
import me.leeminsoo.usedpark.repository.board.RefreshTokenRepository;
import me.leeminsoo.usedpark.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TokenApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    JwtProperties jwtProperties;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        userRepository.deleteAll();
    }
    @DisplayName("createNewAccessToken: 새로운 엑세스 토큰을 발급한다")
    @Test
    public void createNewAccessToken() throws Exception {
        final String url = "/api/token";
        User testUser = userRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test")
                        .nickname("nickname")
                .build());
        String refreshToken = JwtFactory.builder()
                .claims(Map.of("id", testUser.getId()))
                .build()
                .createToken(jwtProperties);
        refreshTokenRepository.save(new RefreshToken(testUser, refreshToken));

        CreateAccessTokenRequestDTO request = new CreateAccessTokenRequestDTO() ;
        request.setRefreshToken(refreshToken);
        final String requestBody = objectMapper.writeValueAsString(request);

        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").isNotEmpty());
    }

}