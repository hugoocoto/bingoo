// Licensed under Hugo's GIBNC License 1.0 (2025) – must include full license and credit "Hugo Coto" if shared.

public class Client extends Thread {

    public Client() {
        super();
    }

    @Override
    public void run() {
        System.out.println("Hello I'm a Client");
    }

}
