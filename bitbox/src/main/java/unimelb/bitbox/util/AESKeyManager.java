package unimelb.bitbox.util;

import unimelb.AES.AESUtil;

import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AESKeyManager {
    public static Map<SocketChannel, String> AESMap = new ConcurrentHashMap<>();
    public static String generateAESKey(SocketChannel socketChannel) {
        try{
            if(AESMap.containsKey(socketChannel))
                return AESMap.get(socketChannel);
            String key = AESUtil.generateKey();
            AESMap.put(socketChannel, key);
            return key;
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("fail to generate AES key.");
        return null;
    }


    public static String AESdecrypt(SocketChannel socketChannel, String encryMsg) {
        String key = AESMap.get(socketChannel);
        if (key == null) {
            System.out.println("not find AES key, please make sure key generated.");
            return null;
        }
        try {
            String rawMsg = AESUtil.desEncrypt(encryMsg, key);
            return rawMsg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String AESencrypt(SocketChannel socketChannel, String rawMsg) {
        String key = AESMap.get(socketChannel);
        if (key == null) {
            System.out.println("not find AES key, please make sure key generated.");
            return null;
        }
        try {
            String encryMsg = AESUtil.encrypt(rawMsg, key);
            return encryMsg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
