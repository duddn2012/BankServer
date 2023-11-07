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

public class SocketServer {

    public SocketServer(Integer portNumber){
        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            User user = new User();
            Account account = null;
            System.out.println("[client] : "+ clientSocket.getInetAddress());

            while(true){
                String msg = in.readLine();
                System.out.println(msg);
                if(msg.substring(0,2).equals("FN")){
                    System.out.println("클라이언트 접속 종료");
                    break;
                }
                if(msg.substring(0,2).equals("ID")){
                    user.setUserId(msg.substring(2,msg.length()));
                }
                if(msg.substring(0,2).equals("NM")){
                    user.setName(msg.substring(2,msg.length()));
                }
                if(msg.substring(0,2).equals("EM")){
                    user.setEmail(msg.substring(2,msg.length()));
                }
                if(msg.substring(0,2).equals("SV")){
                    String accountType = msg.substring(2,msg.length());
                    if(accountType.equals("1")){
                        account = new CheckingAccount();
                    }else if(accountType.equals("2")){
                        account = new SavingAccount();
                    }else if(accountType.equals("3")){
                        account = new FixedDepositAccount();
                    }else{
                        out.write("잘못된 서비스 종류입니다.");
                        break;
                    }
                }
                if(msg.substring(0,2).equals("AC")){
                    Class c = account.getClass();
                    if(c.equals(CheckingAccount.class) || c.equals(FixedDepositAccount.class) || c.equals(SavingAccount.class)){
                        account.setBalance(BigDecimal.valueOf(Long.parseLong(msg.substring(2,msg.length()))));
                        out.write(user.getName()+"님 접속을 환영합니다. 자산을 "+account.getBalance()+"원 보유 중이며 "+account.getAnnualInterestRate()+"%의 연 이율 통장을 보유 중입니다."+"\n");
                        out.flush();
                    }else{
                        System.out.println("모순되는 프로세스입니다.");
                        out.write("모순되는 프로세스입니다.");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
