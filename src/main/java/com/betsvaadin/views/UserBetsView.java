package com.betsvaadin.views;

import com.betsvaadin.domain.BetDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.RoundingMode;
import java.util.List;


@UIScope
@PreserveOnRefresh
@SpringComponent
@Route(value = "bets")
public class UserBetsView extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    private MainView mainView;

    private TextField userName = new TextField("User");
    private List<BetDto> bets;
    private Grid<BetDto> grid = new Grid<>();

    public UserBetsView() {

        Button logOut = new Button("Log Out");
        logOut.addClickListener(event -> {
            mainView.setUser(null);
            mainView.getBetsLayout().removeAll();
            mainView.getBetsLayout().removeAll();
            logOut.getUI().ifPresent(ui -> {
                ui.navigate("login");
            });
        });

        setHorizontalComponentAlignment(FlexComponent.Alignment.END, logOut);

        HorizontalLayout userBar = new HorizontalLayout();
        userBar.getStyle().set("border-bottom-style", "solid");
        userBar.getStyle().set("border-width", "1px");
        userBar.getStyle().set("padding-bottom", "20px");


        userName.setReadOnly(true);
        userName.getStyle().set("font-size", "22px");

        Checkbox onlyPending = new Checkbox();
        onlyPending.setLabel("Show only pending bets");
        onlyPending.addValueChangeListener(event -> {
            bets = mainView.getBetsFacade().getBetsOfUser(mainView.getUser().getId(),
                    onlyPending.getValue());
            grid.setItems(bets);
        });


        Button mainPageButton = new Button("Go to Main Page");
        mainPageButton.addClickListener(event6 -> {
            mainPageButton.getUI().ifPresent(ui -> {
                ui.navigate("");
            });

        });

        userBar.add(userName, onlyPending, mainPageButton);
        userBar.setDefaultVerticalComponentAlignment(Alignment.END);


        grid.addColumn(betDto -> betDto.getBetProspect().getTeams().get(0))
                .setHeader("Home team");
        grid.addColumn(betDto -> betDto.getBetProspect().getTeams().get(1))
                .setHeader("Away team");
        grid.addColumn(betDto -> betDto.getBetProspect().getCommence_time().toLocalDateTime()
                .plusHours(1).toString().replace('T', ' '))
                .setHeader("Match start");
        grid.addColumn(betDto -> betDto.getTippedWinner())
                .setHeader("Bet on");
        grid.addColumn(betDto -> betDto.getOdd())
                .setHeader("Odd");
        grid.addColumn(betDto -> betDto.getWinner())
                .setHeader("Actual winner");
        grid.addColumn(betDto -> betDto.getStake() + " €")
                .setHeader("Stake");
        grid.addColumn(betDto -> betDto.getOdd().multiply(betDto.getStake())
                .setScale(2, RoundingMode.HALF_UP) + " €")
                .setHeader("Potential win");
        grid.addColumn(betDto -> betDto.isFinalized() ? "yes" : "no")
                .setHeader("Finalized");
        grid.addColumn(betDto -> betDto.getCashWin() == null ? "" : betDto.getCashWin() + " €")
                .setHeader("Cash win");

        grid.getColumns()
                .forEach(column -> column.setAutoWidth(true));

        add(logOut, userBar, grid);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (mainView.getUser() != null) {
            userName.setValue(mainView.getUser().getUsername());
            bets = mainView.getBetsFacade().getBetsOfUser(mainView.getUser().getId(), false);
            grid.setItems(bets);
        } else {
            mainView.cleanUp();
            event.forwardTo(LoginView.class);
        }
    }
}
