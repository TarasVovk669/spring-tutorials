package com.grpc.demo.snakesladdergame;

import com.grpc.demo.gen.HeroResponse;
import io.grpc.stub.StreamObserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameInstance {

    private List<StreamObserver<HeroResponse>> streamObservers;
    private HeroAllow heroAllow;
    private Map<Long, Long> position = new HashMap<>();

    public List<StreamObserver<HeroResponse>> getStreamObservers() {
        return streamObservers;
    }

    public void setStreamObservers(List<StreamObserver<HeroResponse>> streamObservers) {
        this.streamObservers = streamObservers;
    }

    public HeroAllow getHeroAllow() {
        return heroAllow;
    }

    public void setHeroAllow(HeroAllow heroAllow) {
        this.heroAllow = heroAllow;
    }

    public Map<Long, Long> getPosition() {
        return position;
    }

    public void setPosition(Map<Long, Long> position) {
        this.position = position;
    }

    enum HeroAllow {
        FIRST,
        SECOND
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GameInstance{");
        sb.append("streamObserver=").append(streamObservers);
        sb.append(", heroAllow=").append(heroAllow);
        sb.append(", position=").append(position);
        sb.append('}');
        return sb.toString();
    }


    public static final class GameInstanceBuilder {
        private List<StreamObserver<HeroResponse>> streamObservers;
        private HeroAllow heroAllow;
        private Map<Long, Long> position;

        private GameInstanceBuilder() {
        }

        public static GameInstanceBuilder aGameInstance() {
            return new GameInstanceBuilder();
        }

        public GameInstanceBuilder streamObservers(List<StreamObserver<HeroResponse>> streamObservers) {
            this.streamObservers = streamObservers;
            return this;
        }

        public GameInstanceBuilder heroAllow(HeroAllow heroAllow) {
            this.heroAllow = heroAllow;
            return this;
        }

        public GameInstanceBuilder position(Map<Long, Long> position) {
            this.position = position;
            return this;
        }

        public GameInstance build() {
            GameInstance gameInstance = new GameInstance();
            gameInstance.setStreamObservers(streamObservers);
            gameInstance.setHeroAllow(heroAllow);
            gameInstance.setPosition(position);
            return gameInstance;
        }
    }
}
