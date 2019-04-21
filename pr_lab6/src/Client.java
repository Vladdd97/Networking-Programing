import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress ip = InetAddress.getLocalHost();
        byte sendByte[];
        byte receiveByte[] = new byte[1024];

        while (true) {
            //Data Sending
            Scanner input = new Scanner(System.in);
            System.out.print("Client:");
            String sendMessage = input.nextLine();
            sendByte = sendMessage.getBytes();
            DatagramPacket sendDatagramPacket = new DatagramPacket(sendByte, sendByte.length, ip, 8082);
            socket.send(sendDatagramPacket);

            //Data Receiving
            DatagramPacket receiveDatagramPacket = new DatagramPacket(receiveByte, receiveByte.length);
            socket.receive(receiveDatagramPacket);
            String receiveMessage = new String(receiveDatagramPacket.getData());
            receiveMessage = receiveMessage.trim();
            System.out.println("Server:" + receiveMessage);

        }
    }

}