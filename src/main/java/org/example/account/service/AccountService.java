package org.example.account.service;

import org.example.account.domain.Account;
import org.example.account.domain.BankAccount;
import org.example.dbconnection.DbConnection;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Map;

public class AccountService {

    private static AccountService instacne;
    private DbConnection dbConnection;

    private AccountService(){
        dbConnection = new DbConnection();
    }

    public static AccountService getInstance() {
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

    public BigDecimal getAccountBalance(BigInteger accountId) {
        ArrayList<Map<String,Object>> queryResult = dbConnection.getDataByQuery("select balance from account where account_id="+accountId);
        return BigDecimal.valueOf((Long) queryResult.get(0).get("balance"));
    }

}
