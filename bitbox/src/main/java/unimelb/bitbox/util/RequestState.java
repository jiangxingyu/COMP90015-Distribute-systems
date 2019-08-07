package unimelb.bitbox.util;

public class RequestState {
    private String command = null;
    private String pathName = null;
    private long position = 0;
    private long length = 0;
    public RequestState(String cmd, String pathName){
        this.command = cmd;
        this.pathName = pathName;
    }
    public RequestState(String cmd, String pathName, long pos, long len){
        this.command = cmd;
        this.pathName = pathName;
        this.position = pos;
        this.length = len;
    }
    public String getCommand(){
        return this.command;
    }
    public String getPathName(){
        return this.pathName;
    }
    public long getPosition(){
        return this.position;
    }
    public long getLength(){
        return this.length;
    }
    public boolean equals(Object obj){
        RequestState rs = null;
        if(obj instanceof RequestState)
        {
            rs = (RequestState)obj;
            if(rs.command.equals(this.command)&&rs.pathName.equals(this.pathName)&&rs.position==this.position&&rs.length==this.length)
                return true;
            return false;
        }
        return false;
    }
}
