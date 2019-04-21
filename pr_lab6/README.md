# Laboratory work nr.6 (PR)  
## Task: create a program in which you will use UDP protocol  
  
## About UDP

UDP stands for User Datagram Protocol — a datagram is the same thing as a packet of information. The UDP protocol works similarly to TCP, but it throws all the error-checking stuff out. All the back-and-forth communication and deliverability guarantees slow things down.

When using UDP, packets are just sent to the recipient. The sender will not wait to make sure the recipient received the packet — it will just continue sending the next packets. If you are the recipient and you miss some UDP packets, too bad — you can not ask for those packets again. There is no guarantee you are getting all the packets and there is no way to ask for a packet again if you miss it, but losing all this overhead means the computers can communicate more quickly.

UDP is used when speed is desirable and error correction is not necessary. For example, UDP is frequently used for live broadcasts and online games.

## Task Implementation  
  
For implementing this task I created two classes ***Client.java*** class and ***Server.java*** class.  
The basic functionality of app is that Client can send messages to Server and Server can respond to the client.  
All this works using UDP protocol.  
  
  
***Server.java*** class
```java
public class Server {
    public static void main(String[] args) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(8082);
        System.out.println("[Server is started]");
        byte receiveByte[] = new byte[1024];
        byte sendByte[];
        while (true) {
            //data receiving
            DatagramPacket receiveDatagramPacket = new DatagramPacket(receiveByte, 
            receiveByte.length);
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
            DatagramPacket sendDatagramPacket = new DatagramPacket(sendByte, sendByte.length, ip, 
            port);
            serverSocket.send(sendDatagramPacket);
        }
    }
}
```
Here as you see a ***socket*** is created on port ***8082***. The server will receive connection on this port.  
  
  
  
  
  
***Client.java*** class
```java
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
            DatagramPacket sendDatagramPacket = new DatagramPacket(sendByte, sendByte.length, ip, 
            8082);
            socket.send(sendDatagramPacket);

            //Data Receiving
            DatagramPacket receiveDatagramPacket = new DatagramPacket(receiveByte, 
            receiveByte.length);
            socket.receive(receiveDatagramPacket);
            String receiveMessage = new String(receiveDatagramPacket.getData());
            receiveMessage = receiveMessage.trim();
            System.out.println("Server:" + receiveMessage);

        }
    }
}
```

Here as you see the client sends data to ***Server*** using ***DatagramPacket***.

