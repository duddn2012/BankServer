package org.example.account.domain;

import lombok.Getter;

/*
 * Ko's Bank
 */
@Getter
public class BankAccount extends Account {

    private static BankAccount instacne;

    private BankAccount(){

    }

    public static BankAccount getInstacne() {
        if(instacne == null){
            instacne = new BankAccount();
        }
        return instacne;
    }

}
