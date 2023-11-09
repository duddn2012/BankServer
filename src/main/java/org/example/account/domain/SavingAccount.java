package org.example.account.domain;

import java.math.BigDecimal;

/*
 * 예금 통장 클래스
 * TODO 작업 예정
 */
public class SavingAccount extends Account{

    public SavingAccount() {
        setAnnualInterestRate(BigDecimal.valueOf(2.75));
    }

    /*
     * 출금 오버라이딩
     */
    public void withdrawal(BigDecimal credit){
        System.out.println("예금 통장은 출금이 불가능합니다.");
    }

    /*
     * 예금 만기 후 예상 금액 조회
     */

    /*
     * 기간 별로 연 고정 금리 차등 부여
     */



}
