package org.example.account.domain;

import lombok.Getter;
import lombok.Setter;
import org.example.common.BigDecimalUtil;
import org.example.common.PropertyUtil;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Properties;

import static org.example.common.BigDecimalUtil.*;

/*
 * TODO Json 기반 통신으로 변경 이후, Setter 제거
 */
@Getter
@Setter
public abstract class Account {

    private final PropertyUtil propertyUtil = PropertyUtil.getInstance();
    private BigInteger userId;  //FK
    private BigDecimal balance; //은행 Balance 필드에서 Critical Section이 발생
    private BigDecimal withdrawalLimit; //출금 한도
    private BigDecimal annualInterestRate;
    private static Object balanceLock = new Object();  //synchronized lock

    public Account() {
        String withdrawalLimitValue = propertyUtil.getPropertyByString("account.withdrawalLimit");
        withdrawalLimit = BigDecimalUtil.parseStringToBigDecimal(withdrawalLimitValue);
    }

    /**
     * 입금
     * @param credit - 입금 금액
     */
    public void deposit(BigDecimal credit){
        synchronized (balanceLock){
            if(validateDepositCreditZero(balance, credit)){
                balance = balance.add(credit);
            }
        }
    }

    /**
     * 출금
     * @param credit - 출금 금액
     */
    public void withdrawal(BigDecimal credit){
        synchronized (balanceLock) {
            if(validateWithdrawalCreditRange(balance, credit) &&  validateWithdrawalLimit(credit, withdrawalLimit)) {
                balance = balance.subtract(credit);
            }
        }
    }

    /**
     * 계좌이체
     * @param sendSendAccount - 출발지 계좌
     * @param reciveAccount - 목적지 계좌
     * @param credit - 송금 금액
     */
    public void transferToAccount(Account sendSendAccount, Account reciveAccount, BigDecimal credit){
        synchronized (balanceLock){
            if(validateWithdrawalCreditRange(sendSendAccount.balance, credit) &&  validateWithdrawalLimit(credit, withdrawalLimit)) {
                sendSendAccount.balance = sendSendAccount.balance.subtract(credit);
                reciveAccount.balance = reciveAccount.balance.add(credit);
            }
        }
    }

}