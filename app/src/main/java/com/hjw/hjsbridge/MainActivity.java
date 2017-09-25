package com.hjw.hjsbridge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hjw.hjsbridge.annotation.BridgeMethod;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @BridgeMethod(value = "testBridgeMethod")
    public void testBridgeMethod(){

    }
}
