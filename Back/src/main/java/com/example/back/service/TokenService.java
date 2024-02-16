package com.example.back.service;

import com.example.back.model.Token;
import com.example.back.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    public Optional<Token> getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public List<Token> getAllValidTokens(Long userId) {
        return tokenRepository.findAllValidTokenByUser(userId);
    }

    public void saveToken(Token token) {
        tokenRepository.save(token);
    }

    public void saveAllTokens(List<Token> tokens) {
        tokenRepository.saveAll(tokens);
    }
}
