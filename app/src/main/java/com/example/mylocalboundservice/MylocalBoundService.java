package com.example.mylocalboundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MylocalBoundService extends Service {
    //object is created of MyLocallBinder class 'localBinder'
    public MyLocallBinder locallBinder=new MyLocallBinder();

    public MylocalBoundService() {

    }
    //onBind() method is where the service and client (Activity or Fragment) establish a connection for communication between them.
    //service also wants to communicate with client(activity ) that is why local localbinder(binder class instance is sent instead of null(as in start service)

    //Intent object is receieved as a parameter when onBind method is called in MainActivity, passed param
    // represents the request to bind the service.
    @Override
    public IBinder onBind(Intent intent) {
        return locallBinder;
    }//above local binder object is returned here so that the client
    // (Main Activity) can access the running service(MylocalBoundService) instance,
    // which is in getBoundService method of MyLocallBinder class

    //method which returns date and time
    public String getSystemTime(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("hh:mm:ss   dd/MM/yyyy");
        return simpleDateFormat.format(new Date());// THIS new Date() object returns system current date and time
        //this system current date and time is then formatted into 'simpleDateFormat'.
        //because new Date() object just return date it does not have a string pattern
    }

    //this whole class act as a local binder(2)
    //this class basically has a method 'getBoundService' which return the instance of this running class 'MyLocalBoundService
    public class MyLocallBinder extends Binder {
        MylocalBoundService getBoundService(){
            return MylocalBoundService.this;
        }
    }


}

//1.The Android framework expects the service to return an IBinder object to the client,
// which acts as a communication bridge between the client and the service.
//The IBinder object that we return from onBind() is the LocalBinder, which allows the
// client to get the service instance and interact with it


//(2)we have created a local binder which will be bound in this onBound CLASS, AND PROVIDES
// THE INSTANCE OF THIS LocalBound running service class, from this instance we can access class's
//public methods