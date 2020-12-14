package com.betsvaadin;

import com.betsvaadin.bets.facade.BetsFacade;
import com.betsvaadin.domain.BetDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class BetsVaadinApplicationTests {

    @Autowired
    BetsFacade betsFacade;

    @Test
    void contextLoads() {
    }
}
