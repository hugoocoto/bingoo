// Licensed under Hugo's GIBNC License 1.0 (2025) – must include full license and credit "Hugo Coto" if shared.
package source;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Client {

    private MulticastSocket socket;
    private InetAddress group;
    private boolean shouldClose = false;
    private static final String MULTICAST_IP = "225.0.0.100";
    private static final int PORT = 6789;
    private Card c;

    public Client() {
        init();
        play();
        close();
    }

    private void close() {
        try {
            socket.leaveGroup(group);
            socket.close();
        } catch (Exception e) {

            System.out.println(e);
        }
    }

    private void init() {
        try {
            socket = new MulticastSocket(PORT);
            group = InetAddress.getByName(MULTICAST_IP);
            socket.joinGroup(group);

            c = new Card();
            System.out.println("Mi cartón:");
            c.print();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String receiveMessage() {
        try {
            byte[] buffer = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet); // bloquea hasta recibir algo
            return new String(packet.getData(), 0, packet.getLength()).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void sendMessage(String msg) {
        try {
            byte[] data = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, group, PORT);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void play() {
        while (!c.bingo() && !shouldClose) {
            String msg = receiveMessage();

            if (msg.equalsIgnoreCase("bingo")) {
                c.print();
                System.out.println("Han cantado Bingo :/");
                shouldClose = true;
                break;
            }

            // Si el mensaje es un número de dos dígitos
            try {
                int num = Integer.parseInt(msg);
                System.out.println("Ha salido la bola: " + num);

                if (c.check(num)) {
                    c.print();
                }

                if (c.bingo()) {
                    System.out.println("BINGO!");
                    sendMessage("bingo");
                    shouldClose = true;
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}
