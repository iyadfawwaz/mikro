
package sy.iyad.mikrotik.Utils;

/*
@author iyadFawwaz;
 */

public class ApiDataException extends MikrotikApiException {

    public ApiDataException(String msg) {
        super(msg);
    }

    public ApiDataException(String msg, Throwable err) {
        super(msg, err);
    }
}
