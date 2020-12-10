package com.betsvaadin;

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
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@PreserveOnRefresh
@SpringComponent
@Route(value = "login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private MainView mainView;


    @Autowired
    public LoginView(MainView mainView, BetsFacade betsFacade) {
        this.mainView = mainView;

        TextField login = new TextField("Login");
        PasswordField password = new PasswordField("Password");

        Button logInButton = new Button("Log In");
        logInButton.addClickListener(event -> {
            String inputLogin = login.getValue();
            String inputPassword = password.getValue();
            if (inputLogin.length() < 3 || inputPassword.length() < 5) {
                Notification.show("Please enter at least 3 character long user login and 5 character long password!");
            } else {
                LogInFeedback logInFeedback = betsFacade.logUserIn(inputLogin, inputPassword);
                if (logInFeedback.getUser() != null){
                    mainView.setUser(logInFeedback.getUser());
                    getUI().ifPresent(ui ->
                            ui.navigate(""));
                }else{
                    Notification.show(logInFeedback.getMessage());
                }

            }
        });

        Button signUpButton = new Button("Sign Up");
        signUpButton.addClickListener(event -> {
            getUI().ifPresent(ui ->
                    ui.navigate("signup"));
        });

        setHorizontalComponentAlignment(Alignment.CENTER, login);
        setHorizontalComponentAlignment(Alignment.CENTER, password);
        setHorizontalComponentAlignment(Alignment.CENTER, logInButton);
        setHorizontalComponentAlignment(Alignment.CENTER, signUpButton);

        add(login, password, logInButton, signUpButton);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (mainView.getUser()!=null) {
            event.forwardTo(MainView.class);
        }
    }
}
