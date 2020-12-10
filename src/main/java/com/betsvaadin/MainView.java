package com.betsvaadin;


import com.betsvaadin.bets.facade.BetsFacade;
import com.betsvaadin.domain.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@UIScope
@PreserveOnRefresh
@SpringComponent
@Route
@Theme(value = Lumo.class)
public class MainView extends VerticalLayout implements BeforeEnterObserver {

    private UserDto user = null;
    private CheckboxGroup selectLeague = new CheckboxGroup();
    private HorizontalLayout userBar = new HorizontalLayout();
    private HorizontalLayout betProspectsBar = new HorizontalLayout();
    private HorizontalLayout mainBetsLayout = new HorizontalLayout();
    private VerticalLayout betProspectsLayout = new VerticalLayout();
    private VerticalLayout betsLayout = new VerticalLayout();
    private BetsFacade betsFacade;
    private LeaguesMap leaguesMap;

    @Autowired
    public MainView(BetsFacade betsFacade, LeaguesMap leaguesMap) {
        this.betsFacade = betsFacade;
        this.leaguesMap = leaguesMap;

        //Test data************************************
        Integer sport_key = 2014;
        List<String> teams = new ArrayList<>();
        teams.add("Brighton and Hove Albion");
        teams.add("Cádiz CF");
        ZonedDateTime commence_time = ZonedDateTime.parse("2020-12-12T13:00:00Z");
        List<BigDecimal> h2h = new ArrayList<>();
        h2h.add(new BigDecimal("3.56"));
        h2h.add(new BigDecimal("3.94"));
        h2h.add(new BigDecimal("4.18"));
        BetProspectDto betProspect1 = new BetProspectDto(sport_key, teams, commence_time, h2h);

        Integer sport_key2 = 2014;
        List<String> teams2 = new ArrayList<>();
        teams2.add("Barcelona");
        teams2.add("Real Madrid");
        ZonedDateTime commence_time2 = ZonedDateTime.parse("2020-12-12T17:00:00Z");
        List<BigDecimal> h2h_2 = new ArrayList<>();
        h2h_2.add(new BigDecimal("1.32"));
        h2h_2.add(new BigDecimal("2.35"));
        h2h_2.add(new BigDecimal("3.79"));

        BetProspectDto betProspect2 = new BetProspectDto(sport_key2, teams2, commence_time2, h2h_2);

        List<BetProspectDto> prospectList = new ArrayList<>();
        prospectList.add(betProspect1);
        prospectList.add(betProspect2);

        Button getBetProspects = new Button("Get available bet prospects");
        getBetProspects.addClickListener(event -> {
            betProspectsLayout.removeAll();
            for(BetProspectDto prospect: prospectList){
                betProspectsLayout.add(new BetProspectLayout(this, prospect));
            }

        });
        //End of test data************************************


        Button logOut = new Button("Log Out");
        logOut.addClickListener(event -> {
            user = null;
            betProspectsLayout.removeAll();
            getUI().ifPresent(ui ->
                    ui.navigate("login"));
        });

        setHorizontalComponentAlignment(Alignment.END, logOut);

//        Button getBetProspects = new Button("Get available bet prospects");
//        getBetProspects.addClickListener(event -> {
//            betProspectsLayout.removeAll();
//            if(selectLeague.getSelectedItems().size() > 0){
//                List<BetProspectDto> list = betsFacade.getBetProspects(selectLeague.getSelectedItems());
//                for (BetProspectDto prospect: list){
//                    betProspectsLayout.add(new BetProspectLayout(this, prospect));
//                }
//            }else{
//                Notification.show("Select at least 1 league to see it's bet prospects");
//            }
//        });


        selectLeague.setItems(leaguesMap.getLeagues().entrySet().stream()
                .map(e -> e.getKey()).collect(Collectors.toList()));


        add(logOut);
        add(userBar);

        betProspectsBar.getStyle().set("border-bottom-style", "solid");
        betProspectsBar.getStyle().set("border-width",  "1px");
        betProspectsBar.getStyle().set("padding-bottom", "20px");
        betProspectsBar.setWidthFull();

        betProspectsBar.add(getBetProspects, selectLeague);
        add(betProspectsBar);


        mainBetsLayout.add(betProspectsLayout, betsLayout);
        add(mainBetsLayout);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (user==null) {
            event.forwardTo(LoginView.class);
        } else {
            updateUserBar();
        }
    }

    public void updateUserBar(){
        userBar.removeAll();
        userBar.add(new UserLayout(this));
    }
}
