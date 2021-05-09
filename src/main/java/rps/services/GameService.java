package rps.services;
import java.util.*;

import antlr.collections.impl.LList;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import rps.model.gamelogic.Selection;
import rps.model.utils.Body;
import rps.model.game.Game;
import rps.model.utils.AppUtils;
import rps.repositories.GameRepository;
import rps.repositories.TokenRepository;
import rps.tokens.Token;

@Service
@AllArgsConstructor
public class GameService {

    TokenRepository tokenRepository;
    GameRepository gameRepository;

    public Game startNewGame(Token token) {

        Game newGame = new Game(AppUtils.createNewId(), token);
        token.setOwnerGame(newGame);
        gameRepository.save(newGame);
        return newGame;
    }

//    public List<Game> joinGame(Token Token) {
////
////        List getAllOpenGames= gameRepository.findAll();
////
////        return  getAllOpenGames;
//    }


    public Game joinGame(String gameId,Token token) {
        Game gameInAction = gameRepository.getOne(gameId);
        gameInAction.setJoiner(token);
        gameInAction.setState(Game.State.ACTIVE);

        gameRepository.save(gameInAction);
        return gameInAction;
    }


    public Game getState(String gameId) {
        Game gameInAction = gameRepository.getOne(gameId);

        gameInAction.getState();

        gameRepository.save(gameInAction);

        return gameInAction;
    }

    public Game makeMove(Selection move, String token) {
        Game moveInAction = gameRepository.getOne(token);

        if (moveInAction.getMove() == null) {
            moveInAction.setMove(move);
        }
        gameRepository.save(moveInAction);

        return moveInAction;
    }

    public Game setName(String name, String token) {
        Game setName = gameRepository.getOne(token);
        if (setName == null) {
            setName.setName(name);
        }

        gameRepository.save(setName);
        return setName;

    }
}