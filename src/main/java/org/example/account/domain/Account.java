package org.example.account.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.common.BigDecimalUtil;

import java.math.BigDecimal;

import static org.example.common.BigDecimalUtil.*;

/*
 * TODO Json 기반 통신으로 변경 이후, Setter 제거
 */
@Getter
@Setter
@NoArgsConstructor
public abstract class Account {

    private String userId;    //FK
    private BigDecimal balance;
    private BigDecimal withdrawalLimit;    //출금 한도
    private BigDecimal annualInterestRate;

    public Account(String userId, BigDecimal balance, BigDecimal annualInterestRate) {
        this.userId = userId;
        this.balance = balance;
        this.annualInterestRate = annualInterestRate;
    }

    /**
     * 입금
     * @param credit 
     */
    public void deposit(BigDecimal credit){
        if(validateDepositCreditZero(balance, credit)){
            balance = balance.add(credit);
        }
    }

    /**
     * 출금
     * @param credit 
     */
    public void withdrawal(BigDecimal credit){
        if(validateWithdrawalCreditRange(balance, credit) &&  validateWithdrawalLimit(credit, withdrawalLimit)) {
            balance = balance.subtract(credit);
        }
    }

    /*
     * TODO Thread Sync 문제 해결 후 작업
     *  계좌이체
     */
    public void sendMoney(Account sendAccount, BigDecimal credit){
        balance = balance.subtract(credit);
        sendAccount.balance = sendAccount.balance.add(credit);
    }


}

