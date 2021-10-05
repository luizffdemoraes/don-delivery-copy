package com.doncorleone.dondelivery.resources.security;

import javax.validation.Valid;

import com.doncorleone.dondelivery.dto.security.LoginRequest;
import com.doncorleone.dondelivery.dto.security.TokenDto;
import com.doncorleone.dondelivery.services.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthResource {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginRequest request){
        UsernamePasswordAuthenticationToken dadosLogin = request.converter();

        Authentication auth = authManager.authenticate(dadosLogin);
        String token = tokenService.gerarToken(auth);

        return ResponseEntity.ok(new TokenDto(token, "Bearer"));
    }
}