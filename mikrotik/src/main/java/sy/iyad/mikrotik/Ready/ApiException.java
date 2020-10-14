
package sy.iyad.mikrotik.Ready;


public class ApiException extends MikrotikApiException {

    public ApiException(String msg) {
        super(msg);
    }

    public ApiException(String msg, Throwable err) {
        super(msg, err);
    }
}
