package org.example.router;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.account.domain.Account;
import org.example.user.User;

import java.io.BufferedWriter;

@Getter
@Setter
@NoArgsConstructor
public class Client {

    private User user;
    private Account account;
    private BufferedWriter out;
}
