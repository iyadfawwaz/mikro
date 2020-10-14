
package sy.iyad.mikrotik.Ready;

public class MikrotikApiException extends Exception {
    public MikrotikApiException(String msg){
        super(msg);
    }
    public MikrotikApiException(String msg,Throwable err){
        super(msg,err);
    }
}
