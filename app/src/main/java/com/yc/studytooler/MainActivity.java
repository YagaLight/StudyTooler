package com.yc.studytooler;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yc.studytooler.Test.RoomRead;
import com.yc.studytooler.Test.RoomWrite;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_write;
    private Button btn_read;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_write).setOnClickListener(this);
        findViewById(R.id.btn_read).setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_write) {
            startActivity(new Intent(this, RoomWrite.class));
        }else if (view.getId() == R.id.btn_read){
            startActivity(new Intent(this, RoomRead.class));

        }
    }
}