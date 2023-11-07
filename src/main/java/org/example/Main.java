package org.example;

import org.example.account.Account;
import org.example.account.CheckingAccount;
import org.example.account.FixedDepositAccount;
import org.example.account.SavingAccount;
import org.example.socket.SocketServer;

import java.io.*;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("<port number>를 입력해주세요.");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        SocketServer socketServer = new SocketServer(portNumber);
    }
}