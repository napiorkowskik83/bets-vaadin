package com.betsvaadin.domain;

import com.betsvaadin.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.theme.Theme;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UserLayout extends HorizontalLayout {


    public UserLayout(MainView mainView) {

        TextField userField = new TextField("User");
        userField.setValue(mainView.getUser().getUsername());
        userField.setReadOnly(true);
        userField.getStyle().set("font-size", "22px");


        BigDecimalField balanceField = new BigDecimalField("Account balance");
        balanceField.setValue(mainView.getUser().getBalance());
        balanceField.setReadOnly(true);
        balanceField.getStyle().set("font-size", "22px");
        balanceField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        balanceField.setPrefixComponent(new Icon(VaadinIcon.EURO));

        Button payIn = new Button("Pay in");
        payIn.addClickListener(event -> {
            Dialog payInDialog = new Dialog();
            BigDecimalField payInAmount = new BigDecimalField("Pay in amount");
            payInAmount.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
            payInAmount.setPrefixComponent(new Icon(VaadinIcon.EURO));
            payInAmount.setValue(BigDecimal.ZERO);
            Button submit = new Button("Submit");
            submit.addClickListener(event1 -> {
                if (payInAmount.getValue().compareTo(BigDecimal.ZERO) <= 0){
                    Notification.show("Please put number greater than 0");
                } else {
                    UserDto user = mainView.getBetsFacade().getUserById(mainView.getUser().getId());
                    if (user != null){
                        BigDecimal newBalance = user.getBalance().add(payInAmount.getValue()).setScale(2, RoundingMode.HALF_UP);
                        user.setBalance(newBalance);
                        mainView.getBetsFacade().updateUser(user);
                        mainView.setUser(user);
                        mainView.updateUserBar();
                        payInDialog.close();
                    }
                }
            });

            Button cancel = new Button("Cancel");
            cancel.addClickListener(event2 -> {
               payInDialog.close();
            });
            VerticalLayout dialogLayout = new VerticalLayout();
            dialogLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            dialogLayout.add(payInAmount, submit, cancel);
            payInDialog.add(dialogLayout);
            payInDialog.open();
        });

        Button payOut = new Button("Pay out");
        payOut.addClickListener(event3 -> {
            Dialog payOutDialog = new Dialog();
            BigDecimalField payOutAmount = new BigDecimalField("Pay out amount");
            payOutAmount.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
            payOutAmount.setPrefixComponent(new Icon(VaadinIcon.EURO));
            payOutAmount.setValue(BigDecimal.ZERO);
            Button submitPayOut = new Button("Submit");
            submitPayOut.addClickListener(event4 -> {
                if (payOutAmount.getValue().compareTo(BigDecimal.ZERO) <= 0
                        || payOutAmount.getValue().compareTo(mainView.getUser().getBalance()) > 0){
                    Notification.show("Please put amount greater than 0 but not greater than your current balance");
                } else {
                    UserDto user = mainView.getUser();
                    BigDecimal newBalance = user.getBalance().subtract(payOutAmount.getValue()).setScale(2, RoundingMode.HALF_UP);
                    user.setBalance(newBalance);
                    mainView.getBetsFacade().updateUser(user);
                    mainView.setUser(user);
                    mainView.updateUserBar();
                    payOutDialog.close();
                }
            });

            Button cancelPayOut = new Button("Cancel");
            cancelPayOut.addClickListener(event5 -> {
                payOutDialog.close();
            });
            VerticalLayout payOutDialogLayout = new VerticalLayout();
            payOutDialogLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            payOutDialogLayout.add(payOutAmount, submitPayOut, cancelPayOut);
            payOutDialog.add(payOutDialogLayout);
            payOutDialog.open();
        });

        add(userField);
        add(balanceField);
        add(payIn);
        add(payOut);

        setDefaultVerticalComponentAlignment(Alignment.END);
    }
}
