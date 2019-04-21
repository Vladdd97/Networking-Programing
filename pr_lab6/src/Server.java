import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(8082);
        System.out.println("[Server is started]");
        byte receiveByte[] = new byte[1024];
        byte sendByte[];
        while (true) {
            //data receiving
            DatagramPacket receiveDatagramPacket = new DatagramPacket(receiveByte, receiveByte.length);
            serverSocket.receive(receiveDatagramPacket);
            String receivedMessage = new String(receiveDatagramPacket.getData());
            receivedMessage = receivedMessage.trim();
            System.out.println("Client:" + receivedMessage);

            //data sending
            Scanner input = new Scanner(System.in);
            System.out.print("Server:");
            String sendMessage = input.nextLine();
            sendByte = sendMessage.getBytes();
            InetAddress ip = receiveDatagramPacket.getAddress();
            int port = receiveDatagramPacket.getPort();
            DatagramPacket sendDatagramPacket = new DatagramPacket(sendByte, sendByte.length, ip, port);
            serverSocket.send(sendDatagramPacket);
        }
    }

}