package com.betsvaadin.bets.facade;

import com.betsvaadin.bets.BetsClient;
import com.betsvaadin.domain.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UIScope
@SpringComponent
public class BetsFacade {

    private final BetsClient betsClient;

    @Autowired
    public BetsFacade(BetsClient betsClient) {
        this.betsClient = betsClient;
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

    public void updateUserPassword(Long userId, String newPassword) {
        betsClient.updateUserPassword(userId, newPassword);
    }

    public UserDto getUserById(Long userId) {
        return betsClient.getUserById(userId);
    }

    public List<UserDto> getAllUsers() {
        return betsClient.getAllUsers();
    }

    public Boolean checkIfUserExists(String login) {
        return betsClient.checkIfUserExists(login);
    }

    public List<BetProspectDto> getBetProspects(BetProspectsRequestDto prospectsRequest) {
        return betsClient.getCurrentBetProspects(prospectsRequest);
    }

    public void addBet(BetDto bet) {
        betsClient.addBet(bet);
    }

    public List<BetDto> getBetsOfUser(Long userId, Boolean onlyPending) {
        return betsClient.getBetsOfUser(userId, onlyPending);
    }

    public void deleteBet(Long betId) {
        betsClient.deleteBet(betId);
    }
}
