package org.example.socket;

import org.example.account.Account;
import org.example.account.CheckingAccount;
import org.example.account.FixedDepositAccount;
import org.example.account.SavingAccount;
import org.example.user.User;

import java.io.*;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;

public class BankSocket {
    private ServerSocket serverSocket;

    public BankSocket(Integer portNumber) {
        try {
            this.serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start(){
        while(true){
            try {
                Socket clientSocket = serverSocket.accept();
                Runnable runnable = new ClientThread(clientSocket);
                Thread clientThread = new Thread(runnable);
                clientThread.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
