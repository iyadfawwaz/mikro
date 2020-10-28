package sy.iyad.mikrotik.Utils;



public class ScanException extends ParseException {

    public ScanException(String msg) {
        super(msg);
    }

    public ScanException(String msg, Throwable err) {
        super(msg, err);
    }

}
