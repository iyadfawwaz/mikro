package sy.iyad.mikrotik.Roots;

public class ScanException extends ParseException {
    public ScanException(String msg) {
        super(msg);
    }

    public ScanException(String msg, Throwable err) {
        super(msg, err);
    }
}
