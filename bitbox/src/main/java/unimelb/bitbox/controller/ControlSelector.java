package unimelb.bitbox.controller;

import unimelb.bitbox.message.Coder;
import unimelb.bitbox.util.ConstUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class ControlSelector {
    private Selector selector;
    private ByteBuffer byteBuffer;

    private static ControlSelector controlSelector = new ControlSelector();
    private ControlSelector () {
        try {
            selector = Selector.open();
            byteBuffer = ByteBuffer.allocate(2048);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ControlSelector getInstance() {
        return controlSelector;
    }

    public void start() {
        try {
            /**
             * this server socket channel is serving for the client,
             * this is using the client port
             */
            ServerSocketChannel clientControlChannel = null;
            clientControlChannel = ServerSocketChannel.open();
            ServerSocket ss = clientControlChannel.socket();
            ss.bind(new InetSocketAddress(ConstUtil.CLIENT_PORT));
            clientControlChannel.configureBlocking(false);
            clientControlChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            int numberOfPrepared = 0;
            // select prepared selector
            try {
                numberOfPrepared = selector.select();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Set selectedKeys = selector.selectedKeys();
            Iterator keyIterator = selectedKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = (SelectionKey) keyIterator.next();
                if (key.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    try {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        selector.wakeup();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    try {

                        int num;
                        StringBuilder stringBuilder = new StringBuilder();
                        byteBuffer.clear();
                        while ((num=socketChannel.read(byteBuffer)) > 0) {
                            byteBuffer.flip();
                            //Coder.INSTANCE.getDecoder().decode(byteBuffer).toString()
                            stringBuilder.append(Coder.INSTANCE.getDecoder().decode(byteBuffer).toString());
                            byteBuffer.flip();
                            byteBuffer.clear();
                        }
                        System.out.println(stringBuilder.toString());
                        //TODO: 需要解密以及后续处理
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (key.isWritable()) {

                }
            }
        }


    }

}
