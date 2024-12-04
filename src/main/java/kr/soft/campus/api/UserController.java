package kr.soft.campus.api;

import kr.soft.campus.filter.JwtFilter;
import kr.soft.campus.util.TokenProvider;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager; // 이제 빈으로 주입받음

    public UserController(TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }


    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/login")
    public ResponseEntity<?> login() {
        logger.info("login");

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken("super", "1234");

        logger.info("1");

        Authentication authentication = authenticationManager.authenticate(token);

        logger.info("2");

        SecurityContextHolder.getContext().setAuthentication(authentication);

        logger.info("3");
        String jwt = tokenProvider.createToken(authentication);

        logger.info("4");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        logger.info("header: {}", JwtFilter.AUTHORIZATION_HEADER);
        logger.info("jwt: {}", jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }

    @Data
    class TokenDto {
        private String token;

        public TokenDto() {
        }

        public TokenDto(String token) {
        }
    }


}
