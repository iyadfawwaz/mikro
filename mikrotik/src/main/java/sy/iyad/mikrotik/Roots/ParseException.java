package sy.iyad.mikrotik.Roots;


import sy.iyad.mikrotik.Ready.MikrotikApiException;

public class ParseException extends MikrotikApiException {
    public ParseException(String msg) {
        super(msg);
    }

    public ParseException(String msg, Throwable err) {
        super(msg, err);
    }
}
