// Licensed under Hugo's GIBNC License 1.0 (2025) – must include full license and credit "Hugo Coto" if shared.
package source;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Server {

    private static final String MULTICAST_IP = "225.0.0.100";
    private static final int PORT = 6789;
    private MulticastSocket socket;
    private InetAddress group;
    private boolean gameOver = false;

    public Server() {
        init();
        play();
    }

    private void init() {
        try {
            socket = new MulticastSocket(PORT);
            group = InetAddress.getByName(MULTICAST_IP);
            socket.joinGroup(group);
            System.out.println("Servidor listo en " + MULTICAST_IP + ":" + PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void listenForBingo() {
        new Thread(() -> {
            try {
                byte[] buffer = new byte[256];
                while (!gameOver) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String msg = new String(packet.getData(), 0, packet.getLength()).trim();
                    if (msg.equalsIgnoreCase("bingo")) {
                        sendMessage("bingo");
                        gameOver = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void play() {
        BallDrawer b = new BallDrawer(90);

        listenForBingo();

        while (!gameOver) {
            int number = b.draw();
            String msg = String.format("%02d", number); 
            System.out.println("Servidor saca bola: " + msg);
            sendMessage(msg);

            try {
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Juego terminado.");
        close();
    }

    private void close() {
        try {
            socket.leaveGroup(group);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}