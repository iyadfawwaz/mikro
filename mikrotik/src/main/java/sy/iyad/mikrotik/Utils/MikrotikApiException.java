
package sy.iyad.mikrotik.Utils;

public class MikrotikApiException extends Exception {

    public MikrotikApiException(String msg){
        super(msg);
    }
    public MikrotikApiException(String msg,Throwable err){
        super(msg,err);
    }

}
