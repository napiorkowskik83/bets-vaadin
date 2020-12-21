package com.betsvaadin.views;

import com.betsvaadin.bets.facade.BetsFacade;
import com.betsvaadin.domain.LogInFeedback;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@SpringComponent
@Route(value = "login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final MainView mainView;

    private final TextField login = new TextField("Login");
    private final PasswordField password = new PasswordField("Password");

    @Autowired
    public LoginView(MainView mainView, BetsFacade betsFacade) {
        this.mainView = mainView;

        Button logInButton = new Button("Log In");
        logInButton.addClickListener(event -> {
            String inputLogin = login.getValue();
            String inputPassword = password.getValue();
            if (inputLogin == null || password == null || inputLogin.length() < 3 || inputPassword.length() < 5) {
                Notification.show("Please enter at least 3 character long user login and 5 character long password!");
            } else {
                LogInFeedback logInFeedback = betsFacade.logUserIn(inputLogin, inputPassword);
                if (logInFeedback.getUser() != null) {
                    login.setValue("");
                    password.setValue("");
                    mainView.cleanUp();
                    mainView.setUser(logInFeedback.getUser());
                    getUI().ifPresent(ui ->
                            ui.navigate(""));
                } else {
                    Notification.show(logInFeedback.getMessage());
                }
            }
        });

        Button signUpButton = new Button("Sign Up");
        signUpButton.addClickListener(event -> signUpButton.getUI().ifPresent(ui ->
                ui.navigate("signup")));

        setHorizontalComponentAlignment(Alignment.CENTER, login);
        setHorizontalComponentAlignment(Alignment.CENTER, password);
        setHorizontalComponentAlignment(Alignment.CENTER, logInButton);
        setHorizontalComponentAlignment(Alignment.CENTER, signUpButton);

        add(login, password, logInButton, signUpButton);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (mainView.getUser() != null) {
            mainView.cleanUp();
            event.forwardTo(MainView.class);
        } else {
            login.setValue("");
            password.setValue("");
        }
    }
}
