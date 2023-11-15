package org.example.router;

import org.example.account.domain.*;
import org.example.account.service.AccountService;
import org.example.user.User;

import java.io.*;
import java.math.BigDecimal;

import static org.example.common.BigDecimalUtil.parseStringToBigDecimal;
import static org.example.common.IOMessageUtil.*;

/**
 * Client의 요청을 라우팅 및 처리하는 클래스
 * TODO 라우팅만 처리하도록 책임의 분리가 필요함
 */
public class RequestRouter {

    private AccountService accountService = AccountService.getInstacne();
    private Client client;
    private BankAccount bankAccount = BankAccount.getInstacne();
    private ClientManager clientManager = ClientManager.getInstance();

    public RequestRouter(BufferedWriter out) {
        client = new Client();
        client.setUser(new User());
        client.setOut(out);
        clientManager.addClient(client);
    }

    public void routing(String msg){
        try{
            Integer msgHeader = Integer.valueOf(msg.substring(0, 3));
            ApiList apiValue = ApiList.getMappedValue(msgHeader);
            BigDecimal credit;
            BufferedWriter clientOut = client.getOut();
            User clientUser = client.getUser();

            switch (apiValue) {
                case CLIENT_CONNECT_EXIT:
                    printClientAction(clientUser.getUserId(), getConnectionOutMessage());
                    writeAndFlushBufferWithLineAddedMessage(clientOut, getConnectionOutMessage());
                    throw new IOException();
                case SET_USER_ID:
                    clientUser.setUserId(msg.substring(3, msg.length()));
                    break;
                case SET_USER_NAME:
                    clientUser.setName(msg.substring(3, msg.length()));
                    break;
                case SET_USER_EMAIL:
                    clientUser.setEmail(msg.substring(3, msg.length()));
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
                        writeAndFlushBufferWithLineAddedMessage(clientOut, getInvalidMessage());
                        break;
                    }
                    break;
                case SET_ACCOUNT_BALANCE:
                    Class c = client.getAccount().getClass();
                    if (c.equals(CheckingAccount.class) || c.equals(FixedDepositAccount.class) || c.equals(SavingAccount.class)) {
                        client.getAccount().setBalance(parseStringToBigDecimal(msg.substring(3, msg.length())));
                        writeAndFlushBufferWithLineAddedMessage(clientOut, getWelcomeMessage(clientUser.getUserId(),client.getAccount().getBalance(), client.getAccount().getAnnualInterestRate()));
                    } else {
                        printClientAction(clientUser.getUserId(), getInvalidMessage());
                        writeAndFlushBufferWithLineAddedMessage(clientOut, getInvalidMessage());
                        break;
                    }
                    break;
                case DEPOSIT_ACCOUNT:
                    credit = parseStringToBigDecimal(msg.substring(3, msg.length()));
                    accountService.deposit(client.getAccount(), credit);
                    printBankBalance(bankAccount.getBalance());
                    writeAndFlushBufferWithLineAddedMessage(clientOut, getClientBalanceMessage(client.getAccount().getBalance()));
                    break;
                case WITHDRAWAL_ACCOUNT:
                    credit = parseStringToBigDecimal(msg.substring(3, msg.length()));
                    accountService.withdrawal(client.getAccount(), credit);
                    printBankBalance(bankAccount.getBalance());
                    writeAndFlushBufferWithLineAddedMessage(clientOut, getClientBalanceMessage(client.getAccount().getBalance()));
                    break;
                case TRANSFER_ACCOUNT:
                    String recieveUserId = msg.substring(3,4);
                    Client recieveClient = clientManager.getClientByUserId(recieveUserId);
                    credit = parseStringToBigDecimal(msg.substring(4, msg.length()));
                    accountService.transferToAccount(client.getAccount(), recieveClient.getAccount(), credit);
                    printBankBalance(bankAccount.getBalance());
                    writeAndFlushBufferWithLineAddedMessage(clientOut, getClientBalanceMessage(client.getAccount().getBalance()));
                    writeAndFlushBufferWithLineAddedMessage(recieveClient.getOut(), getClientBalanceMessage(recieveClient.getAccount().getBalance()));
                    break;
                case INVALID:
                    printClientAction(clientUser.getUserId(), getInvalidMessage());
                    writeAndFlushBufferWithLineAddedMessage(clientOut, getInvalidMessage());
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
