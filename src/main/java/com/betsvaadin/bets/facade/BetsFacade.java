package com.betsvaadin.bets.facade;

import com.betsvaadin.bets.BetsClient;
import com.betsvaadin.domain.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@UIScope
@SpringComponent
public class BetsFacade {

    private final BetsClient betsClient;
    private final LeaguesMap leaguesMap;

    @Autowired
    public BetsFacade(BetsClient betsClient, LeaguesMap leaguesMap) {
        this.betsClient = betsClient;
        this.leaguesMap = leaguesMap;
    }

    public SignUpFeedback signUserUp(UserDto user) {
        return betsClient.signUserUp(user);
    }

    public LogInFeedback logUserIn(String login, String password) {
        return betsClient.logUserIn(login, password);
    }

    public void updateUser(UserDto user) {
        betsClient.updateUser(user);
    }

    public UserDto getUserById(Long userId) {
        return betsClient.getUserById(userId);
    }

    public List<BetProspectDto> getBetProspects(Set<String> leagues) {
        List<BetProspectDto> betProspectDtoList = new ArrayList<>();
        for (String league : leagues) {
            betProspectDtoList.addAll(betsClient.getCurrentBetProspectsFrom(leaguesMap.getLeagues().get(league)));
        }
        return betProspectDtoList;
    }

    public void addBet(BetDto bet) {
        betsClient.addBet(bet);
    }

    public List<BetDto> getAllBets() {
        return betsClient.getAllBets();
    }

    public List<BetDto> getBetsOfUser(Long userId, Boolean onlyPending) {
        return betsClient.getBetsOfUser(userId, onlyPending);
    }
}
