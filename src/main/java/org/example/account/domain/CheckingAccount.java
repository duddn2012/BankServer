package org.example.account.domain;

import java.math.BigDecimal;

/*
 * 입출금 통장
 */
public class CheckingAccount extends Account{

    public CheckingAccount() {
        setAnnualInterestRate(BigDecimal.valueOf(0.10));
    }
}
