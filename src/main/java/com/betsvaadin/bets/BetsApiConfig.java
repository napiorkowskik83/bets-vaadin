package com.betsvaadin.bets;

import com.vaadin.flow.spring.annotation.SpringComponent;

import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@UIScope
@Getter
@SpringComponent
public class BetsApiConfig {

    @Value("${bets.api.endpoint.prod}")
    private String betsApiEndpoint;

    private String ELPKey = "soccer_epl";
    private String LaLigaKey = "soccer_spain_la_liga";
    private String SerieAKey = "soccer_italy_serie_a";

}
