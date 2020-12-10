package com.betsvaadin.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BetDto {
    private Long id;
    private UserDto user;
    private BetProspectDto betProspect;
    private LocalDateTime created;
    private Winner tippedWinner;
    private BigDecimal stake;
    private Boolean finalized;
    private Winner winner;
    private Boolean won;
    private BigDecimal cashWin;

    public BetDto(UserDto user, BetProspectDto betProspect, Winner tippedWinner) {
        this.user = user;
        this.betProspect = betProspect;
        this.tippedWinner = tippedWinner;
    }
}
