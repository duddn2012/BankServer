package org.example.router;

import org.example.account.domain.Account;
import org.example.account.domain.CheckingAccount;
import org.example.account.domain.FixedDepositAccount;
import org.example.account.domain.SavingAccount;
import org.example.user.User;

import java.io.*;
import java.math.BigDecimal;
import java.net.Socket;

/**
 * Client의 요청을 라우팅 및 처리하는 클래스
 * TODO 라우팅만 처리하도록 책임의 분리가 필요함
 */
public class RequestRouter {

    private final User user;
    private final Socket clientSocket;
    private final BufferedWriter out;
    private Account account = null;

    public RequestRouter(Socket clientSocket, BufferedWriter out, User user) {
        this.user = user;
        this.clientSocket = clientSocket;
        this.out = out;
    }

    public void routing(String msg){
        try{
            Integer msgHeader = Integer.valueOf(msg.substring(0, 3));
            ApiList apiValue = ApiList.getMappedValue(msgHeader);

            switch (apiValue) {
                case CLIENT_CONNECT_EXIT:
                    System.out.println("클라이언트 접속 종료");
                    break;
                case SET_USER_ID:
                    user.setUserId(msg.substring(3, msg.length()));
                    break;
                case SET_USER_NAME:
                    user.setName(msg.substring(3, msg.length()));
                    break;
                case SET_USER_EMAIL:
                    user.setEmail(msg.substring(3, msg.length()));
                    break;
                case SET_USER_SERVICE:
                    String accountType = msg.substring(3, msg.length());
                    if (accountType.equals("1")) {
                        account = new CheckingAccount();
                    } else if (accountType.equals("2")) {
                        account = new SavingAccount();
                    } else if (accountType.equals("3")) {
                        account = new FixedDepositAccount();
                    } else {
                        out.write("잘못된 서비스 종류입니다.");
                        break;
                    }
                    break;
                case SET_ACCOUNT_BALANCE:
                    Class c = account.getClass();
                    if (c.equals(CheckingAccount.class) || c.equals(FixedDepositAccount.class) || c.equals(SavingAccount.class)) {
                        account.setBalance(BigDecimal.valueOf(Long.parseLong(msg.substring(3, msg.length()))));
                        out.write(user.getName() + "님 접속을 환영합니다. 자산을 " + account.getBalance() + "원 보유 중이며 " + account.getAnnualInterestRate() + "%의 연 이율 통장을 보유 중입니다." + "\n");
                        out.flush();
                    } else {
                        System.out.println("모순되는 프로세스입니다.");
                        out.write("모순되는 프로세스입니다.");
                        break;
                    }
                    break;
                case INVALID:
                    System.out.println("유효하지 않은 입력입니다.");
                    out.write("유효하지 않은 입력입니다.");
                    out.flush();
                    break;
            }
        }catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

}
