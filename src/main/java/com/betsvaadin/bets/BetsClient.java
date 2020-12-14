package com.betsvaadin.bets;

import com.betsvaadin.domain.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;

@UIScope
@SpringComponent
public class BetsClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(BetsClient.class);
    private final RestTemplate restTemplate;
    private final BetsApiConfig betsApiConfig;

    @Autowired
    public BetsClient(RestTemplate restTemplate, BetsApiConfig betsApiConfig) {
        this.restTemplate = restTemplate;
        this.betsApiConfig = betsApiConfig;
    }

    public List<BetProspectDto> getCurrentBetProspectsFrom(String sportKey) {
        URI url = createUriForGetOddsProspects(sportKey);

        try {
            BetProspectDtoList prospectsResponse = restTemplate.getForObject(url, BetProspectDtoList.class);
            return ofNullable(prospectsResponse.getList()).orElse(new ArrayList<>());
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    private URI createUriForGetOddsProspects(String sportKey) {
        URI url = UriComponentsBuilder.fromHttpUrl(betsApiConfig.getBetsApiEndpoint() +
                "/betprospects")
                .queryParam("sportKey", sportKey)
                .build().encode().toUri();
        return url;
    }

    public SignUpFeedback signUserUp(UserDto user) {
        URI url = createUriForSignUserUp();

        try {
            SignUpFeedback signUpFeedback = restTemplate.postForObject(url, user, SignUpFeedback.class);
            return ofNullable(signUpFeedback).orElse(new SignUpFeedback(null, "Server communication problem (null response)"));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new SignUpFeedback(null, "Server communication problem");
        }
    }

    public LogInFeedback logUserIn(String login, String password) {
        URI url = createUriForLogUserIn(login, password);

        try {
            LogInFeedback logInFeedback = restTemplate.getForObject(url, LogInFeedback.class);
            return ofNullable(logInFeedback).orElse(new LogInFeedback(null, "Server communication problem (null response)"));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new LogInFeedback(null, "Server communication problem");
        }
    }

    private URI createUriForLogUserIn(String login, String password) {
        URI url = UriComponentsBuilder.fromHttpUrl(betsApiConfig.getBetsApiEndpoint() +
                "/users")
                .queryParam("login", login)
                .queryParam("password", password)
                .build().encode().toUri();
        return url;
    }

    public void updateUser(UserDto user) {
        URI url = createUriForSignUserUp();
        restTemplate.put(url, user);
    }

    private URI createUriForSignUserUp() {
        URI url = UriComponentsBuilder.fromHttpUrl(betsApiConfig.getBetsApiEndpoint() +
                "/users")
                .build().encode().toUri();
        return url;
    }

    public UserDto getUserById(Long userId) {
        URI url = createUriForGetUserById(userId);

        try {
            UserDto user = restTemplate.getForObject(url, UserDto.class);
            return user;
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    private URI createUriForGetUserById(Long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl(betsApiConfig.getBetsApiEndpoint() +
                "/users/" + userId)
                .build().encode().toUri();
        return url;
    }

    public void addBet(BetDto bet) {

        URI url = createUriForAddBet();

        try {
            restTemplate.postForObject(url, bet, Boolean.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private URI createUriForAddBet() {
        URI url = UriComponentsBuilder.fromHttpUrl(betsApiConfig.getBetsApiEndpoint() +
                "/bets")
                .build().encode().toUri();
        return url;
    }

    public List<BetDto> getAllBets() {
        URI url = createUriForGetAllBets();

        try {
            BetDtoList betsResponse = restTemplate.getForObject(url, BetDtoList.class);
            return ofNullable(betsResponse.getList()).orElse(new ArrayList<>());
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    private URI createUriForGetAllBets() {
        URI url = UriComponentsBuilder.fromHttpUrl(betsApiConfig.getBetsApiEndpoint() +
                "/bets")
                .build().encode().toUri();
        return url;
    }

    public List<BetDto> getBetsOfUser(Long userId, Boolean pending) {
        URI url = createUriForGetBetsOfUser(userId, pending);
        try {
            BetDtoList betsResponse = restTemplate.getForObject(url, BetDtoList.class);
            return ofNullable(betsResponse.getList()).orElse(new ArrayList<>());
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    private URI createUriForGetBetsOfUser(Long userId, Boolean onlyPending) {
        URI url = UriComponentsBuilder.fromHttpUrl(betsApiConfig.getBetsApiEndpoint() +
                "/userbets")
                .queryParam("userId", userId)
                .queryParam("onlyPending", onlyPending)
                .build().encode().toUri();
        return url;
    }
}
