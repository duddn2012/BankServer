package org.example.account.domain;

import java.math.BigDecimal;

/*
 * 적금 통장
 */
public class FixedDepositAccount extends Account{

    public FixedDepositAccount() {
        setAnnualInterestRate(BigDecimal.valueOf(5));
    }
}
