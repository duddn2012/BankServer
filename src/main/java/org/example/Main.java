package org.example;

import org.example.socket.BankSocket;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("<port number>를 입력해주세요.");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        BankSocket socketServer = new BankSocket(portNumber);
        socketServer.start();
    }
}