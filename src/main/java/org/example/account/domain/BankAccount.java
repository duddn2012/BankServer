package org.example.account.domain;

import lombok.Getter;

import java.math.BigDecimal;

import static org.example.common.BigDecimalUtil.validateDepositCreditZero;

@Getter
public class BankAccount extends Account {
    private static BigDecimal balance;

    public BankAccount(BigDecimal balance){
        this.balance = balance;
    }
}
