package org.example;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    String accountType;
    Long accountNumber;
    int age;
    String name;

    public User(String accountType, Long accountNumber, int age, String name){
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.age = age;
        this.name = name;
    }

}
