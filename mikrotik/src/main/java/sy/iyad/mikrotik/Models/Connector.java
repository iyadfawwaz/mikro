package sy.iyad.mikrotik.Models;


import sy.iyad.mikrotik.Utils.Api;
import sy.iyad.mikrotik.Utils.ApiImp;
import javax.net.SocketFactory;
import java.util.concurrent.Callable;


public class Connector implements Callable<ApiImp> {

    private final String[] entry;
    private final int[] numbers;

    public Connector(String[] entry, int[] numbers){
        this.entry = entry;
        this.numbers = numbers;
    }
    @Override
    public ApiImp call() throws Exception {
        ApiImp apiImp;
        apiImp = (ApiImp) Api.connect(SocketFactory.getDefault(),entry[0],numbers[0],numbers[1]);
        apiImp.login(entry[1],entry[2]);
        return apiImp;
    }
}
