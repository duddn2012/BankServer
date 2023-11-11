package org.example.common;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Stream I/O 시 사용되는 메시지 Util 클래스
 */
public class IOMessageUtil {

    public static void printBankBalance(BigDecimal balance){
        System.out.println("현재 저희 은행에는 "+ balance +"원이 저장되어 있습니다.");
    }
    public static void printClientAction(String userId, String message){
        System.out.println("[client] : "+ userId + " [Message] : "+ message);
    }

    public static String getClientBalanceMessage(BigDecimal balance){
        return "[남은 금액] : "+ balance +"원";
    }

    public static String getInvalidMessage(){
        return "유효하지 않은 입력입니다.";
    }

    public static String getConnectionOutMessage(){
        return "Connection out";
    }

    public static String getWelcomeMessage(String clientName, BigDecimal clientBalance, BigDecimal clientAnnualInterestRate){
        return clientName + "님 접속을 환영합니다. 자산을 " + clientBalance + "원 보유 중이며 " + clientAnnualInterestRate + "%의 연 이율 통장을 보유 중입니다.";
    }

    public static void writeAndFlushBufferWithLineAddedMessage(BufferedWriter bufferedWriter, String message) throws IOException {
        bufferedWriter.write(message+"\n");
        bufferedWriter.flush();
    }
}
