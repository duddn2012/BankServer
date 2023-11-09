package org.example.account.service;

import lombok.RequiredArgsConstructor;
import org.example.account.domain.Account;
import org.example.account.domain.BankAccount;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class AccountService {

    private final BankAccount bankAccount;
    private final Account account;

    /**
     *  입금
     *  @param credit
     */
    public void deposit(BigDecimal credit){
        account.deposit(credit);
        bankAccount.deposit(credit);
    }

    /**
     * 출금
     * @param credit
     */
    public void withdrawal(BigDecimal credit){
        account.withdrawal(credit);
        bankAccount.withdrawal(credit);
    }

    /*
     * TODO Thread Sync 문제 해결 후 작업
     *  계좌이체
     */
    public void sendMoney(Account sendAccount, BigDecimal credit){

    }

}
