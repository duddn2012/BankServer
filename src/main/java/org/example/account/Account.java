package org.example.account;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public abstract class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    String userId;    //FK
    BigDecimal balance;
    BigDecimal withdrawalLimit;    //출금 한도
    BigDecimal annualInterestRate;

    public Account(String userId, BigDecimal balance, BigDecimal annualInterestRate) {
        this.userId = userId;
        this.balance = balance;
        this.annualInterestRate = annualInterestRate;
    }

    /*
     * 입금
     */
    public void deposit(BigDecimal credit){
        balance.add(credit);
    }


    /*
     * 출금
     */
    public void withdrawal(BigDecimal credit){
        //Validation Check
        if((balance.compareTo(credit)==0 || balance.compareTo(credit) == 1) &&  (credit.compareTo(withdrawalLimit) ==0 || credit.compareTo(withdrawalLimit) == -1)) {
            balance.subtract(credit);
        }
    }

    /*
     * 계좌이체
     */
    public void sendMoney(Account sendAccount, BigDecimal credit){
        balance.subtract(credit);
        sendAccount.balance.add(credit);
    }
}

