package com.grpc.demo.snakesladdergame;

import com.grpc.demo.gen.GameContinue;
import com.grpc.demo.gen.GameFinished;
import com.grpc.demo.gen.HeroRequest;
import com.grpc.demo.gen.HeroResponse;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.grpc.demo.snakesladdergame.Const.GAMES_MAP;
import static com.grpc.demo.snakesladdergame.Const.MOVEMENT_MAP;

public class GameStreamObserver implements StreamObserver<HeroRequest> {

    private StreamObserver<HeroResponse> responseStreamObserver;


    public GameStreamObserver(StreamObserver<HeroResponse> responseStreamObserver) {
        this.responseStreamObserver = responseStreamObserver;
    }


    @Override
    public void onNext(HeroRequest value) {
        var gameId = value.getGameId();
        GameInstance gameInstance = getGame(value, gameId);

        var heroPosition = gameInstance.getPosition().get(value.getHeroId());
        var numberOfMove = ThreadLocalRandom.current().nextInt(1, 6);
        var move = heroPosition + numberOfMove;
        var positionResult = MOVEMENT_MAP.getOrDefault(move, move);

        gameInstance.getPosition().put(value.getHeroId(), positionResult);
        gameInstance.setHeroAllow(gameInstance.getHeroAllow().equals(GameInstance.HeroAllow.FIRST)
                ? GameInstance.HeroAllow.SECOND
                : GameInstance.HeroAllow.FIRST);

        if (positionResult >= 100) {
            var looserId = gameInstance.getPosition().entrySet().stream()
                    .filter(x -> !x.getKey().equals(value.getHeroId()))
                    .findFirst().get().getKey();

            for (StreamObserver<HeroResponse> s : gameInstance.getStreamObservers()) {
                s.onNext(HeroResponse.newBuilder()
                        .setFinished(GameFinished.newBuilder()
                                .setWinnerId(value.getHeroId())
                                .setWinnerPosition(positionResult)
                                .setLooserId(looserId)
                                .setLooserPosition(gameInstance.getPosition().get(looserId))
                                .build())
                        .build());
                s.onCompleted();
            }
        } else {
            for (StreamObserver<HeroResponse> s : gameInstance.getStreamObservers()) {
                s.onNext(HeroResponse.newBuilder()
                        .setContinue(GameContinue.newBuilder()
                                .setGameId(gameId)
                                .setHeroId(value.getHeroId())
                                .setPosition(gameInstance.getPosition().get(value.getHeroId()))
                                .setNumberOfMove(numberOfMove)
                                .build())
                        .build());
            }
        }

    }

    private GameInstance getGame(HeroRequest value, long gameId) {
        return Optional.ofNullable(GAMES_MAP.get(gameId))
                .map(gameInstance -> {
                    if (GameInstance.HeroAllow.SECOND.equals(gameInstance.getHeroAllow())
                            && gameInstance.getPosition().size() == 1) {
                        gameInstance.getPosition().put(value.getHeroId(), 0L);
                        gameInstance.getStreamObservers().add(responseStreamObserver);
                    }
                    return gameInstance;
                })
                .orElseGet(() -> {
                    Map<Long, Long> position = new HashMap<>();
                    position.put(value.getHeroId(), 0L);

                    GameInstance gameInstance = GameInstance.GameInstanceBuilder.aGameInstance()
                            .heroAllow(GameInstance.HeroAllow.FIRST)
                            .streamObservers(Arrays.asList(responseStreamObserver))
                            .position(position)
                            .build();

                    GAMES_MAP.put(gameId, gameInstance);
                    return gameInstance;
                });
    }


    @Override
    public void onError(Throwable t) {
        System.out.println("Error: " + t);
        responseStreamObserver.onError(Status.INTERNAL.asRuntimeException());
    }

    @Override
    public void onCompleted() {
        System.out.println("Finish Game!");
        responseStreamObserver.onCompleted();
    }
}
