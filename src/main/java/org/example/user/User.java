package org.example.user;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    String userId;
    String name;
    String email;
    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setEmail(String email){
        this.email = email;
    }
}
