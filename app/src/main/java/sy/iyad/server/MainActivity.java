package sy.iyad.server;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;

import sy.iyad.mikrotik.MikrotikServer;
import sy.iyad.mikrotik.OnCompleteListener;
import sy.iyad.mikrotik.PreReady.ConnectResult;
import sy.iyad.mikrotik.PreReady.ExecuteResult;
import sy.iyad.mikrotik.PreReady.Task;

public class MainActivity extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connExe();
            }
        });
        connExe();
    }
    private void connExe(){
        MikrotikServer.connect("2.2.2.2","admin","995x").addOnCompleteListener(new OnCompleteListener<ConnectResult>() {
            @Override
            public void onComplete(@NonNull Task<ConnectResult> task) {
                if (task.isSuccessful()){
                    MikrotikServer.execute("/interface/print").addOnCompleteListener(new OnCompleteListener<ExecuteResult>() {
                        @Override
                        public void onComplete(@NonNull Task<ExecuteResult> taskx) {
                            if (taskx.isSuccessful()){
                                System.out.println(taskx.getResult().getMapList().toString());
                            }else {
                                System.out.println(taskx.getException().getMessage());
                            }
                        }
                    });
                }else {
                    System.out.println(task.getException().getMessage());
                }
            }
        });
    }
}