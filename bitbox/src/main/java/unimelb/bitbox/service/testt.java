package unimelb.bitbox.service;

import unimelb.bitbox.util.Configuration;
import unimelb.bitbox.util.FileSystemManager;
import unimelb.bitbox.util.FileSystemObserver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class testt implements FileSystemObserver {
    public static void main(String[] args) {
        Map<String,String> aa  = new HashMap<>();
        Set<String> cc = new HashSet<>();
        cc.add("a");
        aa.put("a","a");
        System.out.println(cc.contains("a"));
        System.out.println(aa.get("a"));
        tttt t = new tttt(aa,cc);
        t.bb.remove("a");
        t.cc.remove("a");
        System.out.println(t.bb.get("a"));
        System.out.println(t.cc.contains("a"));

    }
    public static class tttt{
        public tttt(Map<String, String> bb, Set<String> cc){
            this.cc = cc;
            this.bb = bb;
        }
        public Map<String,String> bb;
        public Set<String> cc = new HashSet<String>();

    }

    @Override
    public void processFileSystemEvent(FileSystemManager.FileSystemEvent fileSystemEvent) {

    }
}
