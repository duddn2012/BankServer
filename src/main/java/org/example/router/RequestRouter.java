package org.example.router;

import org.example.account.domain.*;
import org.example.account.service.AccountService;
import org.example.user.User;

import java.io.*;
import java.math.BigDecimal;

import static org.example.common.BigDecimalUtil.parseStringToBigDecimal;

/**
 * Client의 요청을 라우팅 및 처리하는 클래스
 * TODO 라우팅만 처리하도록 책임의 분리가 필요함
 */
public class RequestRouter {

    private AccountService accountService = AccountService.getInstacne();
    private Client client;
    private BankAccount bankAccount = BankAccount.getInstacne();
    private ClientManager clientManager = ClientManager.getInstance();

    public RequestRouter(BufferedWriter out, User user) {
        client = new Client();
        this.client.setUser(user);
        this.client.setOut(out);
    }

    public void routing(String msg){
        try{
            Integer msgHeader = Integer.valueOf(msg.substring(0, 3));
            ApiList apiValue = ApiList.getMappedValue(msgHeader);
            BigDecimal credit;

            switch (apiValue) {
                case CLIENT_CONNECT_EXIT:
                    System.out.println("클라이언트 접속 종료");
                    client.getOut().close();
                    break;
                case SET_USER_ID:
                    client.getUser().setUserId(msg.substring(3, msg.length()));
                    break;
                case SET_USER_NAME:
                    client.getUser().setName(msg.substring(3, msg.length()));
                    break;
                case SET_USER_EMAIL:
                    client.getUser().setEmail(msg.substring(3, msg.length()));
                    break;
                case SET_USER_SERVICE:
                    String accountType = msg.substring(3, msg.length());
                    if (accountType.equals("1")) {
                        client.setAccount(new CheckingAccount());
                    } else if (accountType.equals("2")) {
                        client.setAccount(new SavingAccount());
                    } else if (accountType.equals("3")) {
                        client.setAccount(new FixedDepositAccount());
                    } else {
                        client.getOut().write("잘못된 서비스 종류입니다.");
                        break;
                    }
                    clientManager.addClient(client);
                    break;
                case SET_ACCOUNT_BALANCE:
                    Class c = client.getAccount().getClass();
                    if (c.equals(CheckingAccount.class) || c.equals(FixedDepositAccount.class) || c.equals(SavingAccount.class)) {
                        client.getAccount().setBalance(parseStringToBigDecimal(msg.substring(3, msg.length())));
                        client.getOut().write(client.getUser().getName() + "님 접속을 환영합니다. 자산을 " + client.getAccount().getBalance() + "원 보유 중이며 " + client.getAccount().getAnnualInterestRate() + "%의 연 이율 통장을 보유 중입니다." + "\n");
                        client.getOut().flush();
                    } else {
                        System.out.println("모순되는 프로세스입니다.");
                        client.getOut().write("모순되는 프로세스입니다.\n");
                        break;
                    }
                    break;
                case DEPOSIT_ACCOUNT:
                    credit = parseStringToBigDecimal(msg.substring(3, msg.length()));
                    accountService.deposit(client.getAccount(), credit);
                    System.out.println("현재 저희 은행에는 "+ bankAccount.getBalance()+"원이 저장되어 있습니다.");
                    client.getOut().write("[남은 금액] : "+ client.getAccount().getBalance() +"원\n");
                    client.getOut().flush();
                    break;
                case WITHDRAWAL_ACCOUNT:
                    credit = parseStringToBigDecimal(msg.substring(3, msg.length()));
                    accountService.withdrawal(client.getAccount(), credit);
                    System.out.println("현재 저희 은행에는 "+ bankAccount.getBalance()+"원이 저장되어 있습니다.");
                    client.getOut().write("[남은 금액] : " + client.getAccount().getBalance() +"원\n");
                    client.getOut().flush();
                    break;
                case TRANSFER_ACCOUNT:
                    String recieveUserId = msg.substring(3,4);
                    Client recieveClient = clientManager.getClientByUserId(recieveUserId);
                    credit = parseStringToBigDecimal(msg.substring(4, msg.length()));
                    accountService.transferToAccount(client.getAccount(), recieveClient.getAccount(), credit);
                    System.out.println("현재 저희 은행에는 "+ bankAccount.getBalance()+"원이 저장되어 있습니다.");
                    client.getOut().write("[남은 금액] : " + client.getAccount().getBalance() +"원\n");
                    recieveClient.getOut().write("[남은 금액] : " + recieveClient.getAccount().getBalance() +"원\n");
                    recieveClient.getOut().flush();
                    client.getOut().flush();
                    break;
                case INVALID:
                    System.out.println("유효하지 않은 입력입니다.");
                    client.getOut().write("유효하지 않은 입력입니다.\n");
                    client.getOut().flush();
                    break;
            }
        }catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            clientManager.deleteClient(client);
            System.out.println("Exception caught when trying to listen on port or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

}
