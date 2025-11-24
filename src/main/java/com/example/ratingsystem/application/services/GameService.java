package com.example.ratingsystem.application.services;

import com.example.ratingsystem.adapters.inbound.DTOs.responses.GameResponse;
import com.example.ratingsystem.adapters.outbound.persistence.mappers.GameMapper;
import com.example.ratingsystem.application.ports.Game.AddGamePort;
import com.example.ratingsystem.application.ports.Game.LoadGamePort;
import com.example.ratingsystem.domain.models.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final AddGamePort addGamePort;
    private final LoadGamePort loadGamePort;
    private final GameMapper gameMapper;

    public GameResponse addGame(Game game) {
        Game saved = addGamePort.add(game);
        return gameMapper.domainToResponse(saved);
    }

    public GameResponse getGameById(Integer id) {
        Game loaded = loadGamePort.loadById(id);
        return gameMapper.domainToResponse(loaded);
    }

    public List<GameResponse> getAllGames() {
        return loadGamePort.loadAll().stream()
                .map(gameMapper::domainToResponse)
                .toList();
    }

}
