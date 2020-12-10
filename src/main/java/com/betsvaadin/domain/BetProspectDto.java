package com.betsvaadin.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BetProspectDto  {
    private Long id;
    private Integer sport_key;
    private java.util.List<String> teams;
    private ZonedDateTime commence_time;
    private List<BigDecimal> h2h;

    public BetProspectDto(Integer sport_key, List<String> teams, ZonedDateTime commence_time, List<BigDecimal> h2h) {
        this.sport_key = sport_key;
        this.teams = teams;
        this.commence_time = commence_time;
        this.h2h = h2h;
    }
}
