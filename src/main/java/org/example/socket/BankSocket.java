package org.example.socket;

import org.example.account.domain.Account;
import org.example.account.domain.BankAccount;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class BankSocket extends Thread{
    private ServerSocket serverSocket;
    public static BankAccount bankAccount;

    public BankSocket(Integer portNumber) {
        try {
            this.serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            throw new RuntimeException("ServerSocket 생성에 실패하였습니다.");
        }
    }

    @Override
    public void run(){
        //은행 초기 자금 입력
        Scanner scanner = new Scanner(System.in);
        System.out.print("은행의 초기 자금을 입력해주세요.\n금액:");
        bankAccount = new BankAccount(scanner.nextBigDecimal());

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