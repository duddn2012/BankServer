package org.example.socket;

import org.example.account.domain.Account;
import org.example.account.domain.BankAccount;
import org.example.account.service.AccountService;
import org.example.common.BigDecimalUtil;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static org.example.common.BigDecimalUtil.parseStringToBigDecimal;

public class BankSocket extends Thread{

    private ServerSocket serverSocket;
    private final BankAccount bankAccount = BankAccount.getInstacne();
    private final AccountService accountService = AccountService.getInstance();

    public BankSocket(Integer portNumber) {
        try {
            this.serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            throw new RuntimeException("ServerSocket 생성에 실패하였습니다.");
        }
    }

    @Override
    public void run(){
        BigDecimal balance = accountService.getAccountBalance(BigInteger.valueOf(1));
        bankAccount.setBalance(balance);
        System.out.println("현재 저희 은행에는 "+ bankAccount.getBalance()+"원이 저장되어 있습니다.");

        while(true){
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("[client] : "+ clientSocket.getInetAddress());
                Thread client = new Thread(new ClientThread(clientSocket));
                client.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}