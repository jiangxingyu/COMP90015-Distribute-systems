package unimelb.bitbox;

import unimelb.bitbox.controller.EventSelector;
import unimelb.bitbox.controller.EventSelectorImpl;
import unimelb.bitbox.udpcontroller.UdpSelector;
import unimelb.bitbox.util.ConstUtil;
import unimelb.bitbox.util.SyncRunner;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Peer {

    public static class Run implements Runnable {

        @Override
        public void run() {
            UdpSelector udpSelector = UdpSelector.getInstance();
            udpSelector.startServer();
        }
    }
    public static class Run2 implements Runnable {

        @Override
        public void run() {
            EventSelector eventSelector = EventSelectorImpl.getInstance();
            eventSelector.controllerRunning();
        }
    }

    public static void main(String[] args) {
        if (ConstUtil.MODE.equals(ConstUtil.UDP_MODE)) {
            Thread thread = new Thread(new Peer.Run());
            thread.start();
        }
        EventSelector eventSelector = EventSelectorImpl.getInstance();
        UdpSelector udpSelector = UdpSelector.getInstance();
        try {
            ServerMain serverMain = new ServerMain();
            udpSelector.setServerMain(serverMain);
            eventSelector.getFixedThreadPool().execute(new SyncRunner(serverMain));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Thread thread2 = new Thread(new Peer.Run2());
        thread2.start();
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        UdpMessage udpMessage = new UdpMessage(
//                new InetSocketAddress("localhost",9963), "I love you thousand times");
//        udpSelector.registerWrite(udpMessage);
    }
}
