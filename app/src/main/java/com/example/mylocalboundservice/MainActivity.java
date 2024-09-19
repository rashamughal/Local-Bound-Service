package com.example.mylocalboundservice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    MylocalBoundService mylocalBoundService=new MylocalBoundService();
    boolean isConnected=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //connecting activity(component) with the service
        Intent intent=new Intent(MainActivity.this, MylocalBoundService.class);
        //binding an activity to a service.
        //Context.BIND_AUTO_CREATE: This flag tells the system to automatically create and start the service
        // if it is not already running. It ensures that the service is created and bound to the activity as
        // soon as the binding request is made.
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);//intent here is passed to
        //specify which class/etc to bind to,

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //method will be executed when button will be clicked
    public void getTimeButtonPressed(View view){
        TextView textView=findViewById(R.id.textView);
        textView.setText(mylocalBoundService.getSystemTime());//mylocalBoundService.getSystemTime is returning a string pattern
    }

    //making the service connection here so that the service can also communicate
    private ServiceConnection serviceConnection=new ServiceConnection() {
        //when bounded with service
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //making localbinder object to access getBoundService() method
            MylocalBoundService.MyLocallBinder mylocalbinder= (MylocalBoundService.MyLocallBinder) iBinder;
            mylocalBoundService=mylocalbinder.getBoundService();
            isConnected=true;// means activity is bounded now with service

        }

        //when disconnected with the service, because of crash of service or system if kills the service, method is called
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isConnected=false;
        }
    };
}