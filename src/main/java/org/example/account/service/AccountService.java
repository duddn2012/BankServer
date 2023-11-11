package org.example.account.service;

import lombok.RequiredArgsConstructor;
import org.example.account.domain.Account;
import org.example.account.domain.BankAccount;

import java.math.BigDecimal;

public class AccountService {

    private static AccountService instacne;

    private AccountService(){

    }

    public static AccountService getInstacne() {
        if(instacne == null){
            instacne = new AccountService();
        }
        return instacne;
    }

    private BankAccount bankAccount = BankAccount.getInstacne();

    /**
     *  입금
     *  @param credit - 입금 금액
     */
    public void deposit(Account account, BigDecimal credit){
        account.deposit(credit);
        bankAccount.deposit(credit);
    }

    /**
     * 출금
     * @param credit - 출금 금액
     */
    public void withdrawal(Account account, BigDecimal credit){
        account.withdrawal(credit);
        bankAccount.withdrawal(credit);
    }

    /**
     * 계좌이체
     * @param recieveAccount - 목적지 계좌
     * @param credit - 송금 금액
     */
    public void transferToAccount(Account sendAccount, Account recieveAccount, BigDecimal credit){
        sendAccount.transferToAccount(sendAccount, recieveAccount, credit);
    }

}
