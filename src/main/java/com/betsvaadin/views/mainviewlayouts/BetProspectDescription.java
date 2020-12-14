package com.betsvaadin.views.mainviewlayouts;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class BetProspectDescription extends HorizontalLayout {

    public BetProspectDescription() {

        Label invisible1 = new Label("mm");
        invisible1.getStyle().set("color", "white");
        Label startTimeLabel = new Label("Match start");
        startTimeLabel.getStyle().set("font-weight", "bold");
        Label invisible2 = new Label("mmmmmmmm");
        invisible2.getStyle().set("color", "white");
        Label odds = new Label("Odds: ");
        Label invisible3 = new Label("ii");
        invisible3.getStyle().set("color", "white");
        Label homeTeam = new Label("HOME TEAM ");
        homeTeam.getStyle().set("font-weight", "bold");
        Label invisible4 = new Label("i");
        invisible4.getStyle().set("color", "white");
        Label draw = new Label(" DRAW ");
        draw.getStyle().set("font-weight", "bold");
        Label invisible5 = new Label("i");
        invisible5.getStyle().set("color", "white");
        Label awayTeam = new Label(" AWAY TEAM");
        awayTeam.getStyle().set("font-weight", "bold");

        add(invisible1, startTimeLabel, invisible2, odds, invisible3, homeTeam, invisible4, draw, invisible5, awayTeam);

        getStyle().set("border-top-style", "solid");
        getStyle().set("border-width", "1px");
        getStyle().set("padding-top", "20px");
        setWidthFull();
    }
}
