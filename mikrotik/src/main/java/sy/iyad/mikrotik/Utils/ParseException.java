package sy.iyad.mikrotik.Utils;


public class ParseException extends MikrotikApiException {

    public ParseException(String msg) {
        super(msg);
    }

    public ParseException(String msg, Throwable err) {
        super(msg, err);
    }

}
