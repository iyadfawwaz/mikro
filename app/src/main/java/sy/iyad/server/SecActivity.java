package sy.iyad.server;


import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import java.util.Map;
import sy.iyad.mikrotik.MikrotikServer;
import sy.iyad.mikrotik.Models.ExecutionEventListener;


public class SecActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);
        button = findViewById(R.id.xbtn);
        button.setOnClickListener(v -> exe());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.out.println(MikrotikServer.disconnect());
    }

    private void exe(){
        MikrotikServer.execute("ip/address/print")
               .addExecutionEventListener(new ExecutionEventListener() {
                   @Override
                   public void onExecutionSuccess(List<Map<String,String>> result) {
                       button.setText(result.toString());
                   }

                   @Override
                   public void onExecutionFailed(Exception exception) {

                       button.setText(exception.getMessage());
                   }
               });

    }
}
