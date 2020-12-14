package com.betsvaadin.views;


import com.betsvaadin.bets.facade.BetsFacade;
import com.betsvaadin.domain.*;
import com.betsvaadin.views.mainviewlayouts.BetProspectDescription;
import com.betsvaadin.views.mainviewlayouts.BetProspectLayout;
import com.betsvaadin.views.mainviewlayouts.UserLayout;
import com.vaadin.flow.component.UI;
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

import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@UIScope
@PreserveOnRefresh
@SpringComponent
@Route
@Theme(value = Lumo.class)
public class MainView extends VerticalLayout implements BeforeEnterObserver, RouterLayout {

    private UserDto user = null;
    private CheckboxGroup selectLeague = new CheckboxGroup();
    private HorizontalLayout userBar = new HorizontalLayout();
    private HorizontalLayout betProspectsBar = new HorizontalLayout();
    private HorizontalLayout mainBetsLayout = new HorizontalLayout();
    private VerticalLayout betProspectsLayout = new VerticalLayout();
    private VerticalLayout betsLayout = new VerticalLayout();
    private BetProspectDescription description = new BetProspectDescription();
    private BetsFacade betsFacade;
    private LeaguesMap leaguesMap;

    @Autowired
    public MainView(BetsFacade betsFacade, LeaguesMap leaguesMap) {
        this.betsFacade = betsFacade;
        this.leaguesMap = leaguesMap;


        Button getBetProspects = new Button("Get available bet prospects");
        getBetProspects.addClickListener(event -> {
            updateBetProspectsLayout();
        });

        Button logOut = new Button("Log Out");
        logOut.addClickListener(event -> {
            cleanUp();
            UI.getCurrent().navigate(LoginView.class);
        });

        setHorizontalComponentAlignment(Alignment.END, logOut);

        selectLeague.setItems(leaguesMap.getLeagues().entrySet().stream()
                .map(e -> e.getKey()).collect(Collectors.toList()));


        userBar.setDefaultVerticalComponentAlignment(Alignment.END);

        add(logOut);
        add(userBar);

        betProspectsBar.setWidthFull();
        betProspectsBar.add(getBetProspects, selectLeague);
        description.setVisible(false);

        add(betProspectsBar, description);

        mainBetsLayout.add(betProspectsLayout, betsLayout);
        add(mainBetsLayout);
    }

    public void updateBetProspectsLayout() {
        betProspectsLayout.removeAll();
        if (selectLeague.getSelectedItems().size() > 0) {
            List<BetProspectDto> list = betsFacade.getBetProspects(selectLeague.getSelectedItems());
            for (BetProspectDto prospect : list) {
                betProspectsLayout.add(new BetProspectLayout(this, prospect));
            }
        } else {
            Notification.show("Select at least 1 league to see it's bet prospects");
        }
    }

    protected void cleanUp() {
        user = null;
        userBar.removeAll();
        betProspectsLayout.removeAll();
        betsLayout.removeAll();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (user == null) {
            event.forwardTo(LoginView.class);
        } else {
            user = betsFacade.getUserById(user.getId());
            updateUserBar();
        }
    }

    public void updateUserBar() {
        userBar.removeAll();
        userBar.add(new UserLayout(this));
    }
}
