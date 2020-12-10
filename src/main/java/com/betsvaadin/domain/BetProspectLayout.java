package com.betsvaadin.domain;


import com.betsvaadin.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public class BetProspectLayout extends HorizontalLayout {

    private java.util.List<String> teams;
    private ZonedDateTime commence_time;
    private List<BigDecimal> h2h;

    public BetProspectLayout(MainView mainView, BetProspectDto prospect) {
        Label startTime = new Label(prospect.getCommence_time().toLocalDateTime()
                .plusHours(1).toString().replace('T', ' '));
        startTime.setWidth("8em");

        TextField homeTeam = new TextField();
        homeTeam.setValue(prospect.getTeams().get(0));
        homeTeam.setWidth("13em");
        homeTeam.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        homeTeam.setReadOnly(true);

        Button homeTeamOdd = new Button(prospect.getH2h().get(0).toString());
        homeTeamOdd.addClickListener(event -> {
            BetDto bet = new BetDto(mainView.getUser(), prospect, Winner.HOME_TEAM);
            mainView.getBetsLayout().add(new BetLayout(mainView, bet));
        });

        Button drawOdd = new Button(prospect.getH2h().get(1).toString());
        drawOdd.addClickListener(event1 -> {
            BetDto bet = new BetDto(mainView.getUser(), prospect, Winner.DRAW);
            mainView.getBetsLayout().add(new BetLayout(mainView, bet));
        });

        Button awayTeamOdd = new Button(prospect.getH2h().get(2).toString());
        awayTeamOdd.addClickListener(event2 -> {
            BetDto bet = new BetDto(mainView.getUser(), prospect, Winner.AWAY_TEAM);
            mainView.getBetsLayout().add(new BetLayout(mainView, bet));
        });

        TextField awayTeam = new TextField();
        awayTeam.setValue(prospect.getTeams().get(1));
        awayTeam.setWidth("13em");
        awayTeam.setReadOnly(true);

        setDefaultVerticalComponentAlignment(Alignment.CENTER);

        add(startTime);
        add(homeTeam);
        add(homeTeamOdd);
        add(drawOdd);
        add(awayTeamOdd);
        add(awayTeam);
    }
}
