package org.example.common;

import java.math.BigDecimal;


/**
 * BigDecimal Util 클래스
 */
public class BigDecimalUtil {

    public static Boolean validateWithdrawalCreditRange(BigDecimal balance, BigDecimal credit){
        return (balance.compareTo(credit)!=-1) ;    // >=
    }

    public static Boolean validateWithdrawalLimit(BigDecimal credit, BigDecimal withdrawalLimit){
        return (credit.compareTo(withdrawalLimit) !=1) ;    // <=
    }

    public static Boolean validateDepositCreditZero(BigDecimal balance, BigDecimal credit){
        return (balance.compareTo(BigDecimal.ZERO) != -1);  // >=
    }

    public static BigDecimal parseStringToBigDecimal(String value){
                return BigDecimal.valueOf(Long.parseLong(value));
    }
}
