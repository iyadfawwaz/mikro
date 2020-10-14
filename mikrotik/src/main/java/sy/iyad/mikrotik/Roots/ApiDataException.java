
package sy.iyad.mikrotik.Roots;


import sy.iyad.mikrotik.Ready.MikrotikApiException;

public class ApiDataException extends MikrotikApiException {

    public ApiDataException(String msg) {
        super(msg);
    }

    public ApiDataException(String msg, Throwable err) {
        super(msg, err);
    }
}
