package com.github.gamgoon.concurrency.ch03.server;

import com.github.gamgoon.concurrency.ch03.Constants;
import com.github.gamgoon.concurrency.ch03.command.*;
import com.github.gamgoon.concurrency.ch03.dao.WDIDAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SerialServer {
    public static void main(String[] args) {
        WDIDAO dao = WDIDAO.getDAO();
        boolean stopServer = false;
        System.out.println("Initialization complete");

        try (ServerSocket serverSocket = new ServerSocket(Constants.SERIAL_PORT)) {
            do {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {

                    String line = in.readLine();
                    Command command;
                    String[] commandData = line.split(";");
                    System.out.println("Command: " + commandData[0]);
                    switch (commandData[0]) {
                        case "q":
                            System.out.println("Query");
                            command = new QueryCommand(commandData);
                            break;
                        case "r":
                            System.out.println("Report");
                            command = new ReportCommand(commandData);
                            break;
                        case "z":
                            System.out.println("Stop");
                            command = new StopCommand(commandData);
                            stopServer = true;
                            break;
                        default:
                            System.out.println("Error");
                            command = new ErrorCommand(commandData);
                    }
                    String response = command.execute();
                    System.out.println(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (!stopServer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
