package com.coresoft.electricalsolutions;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.coresoft.electricalsolutions.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    com.google.firebase.database.DatabaseReference led1,led2,led3,led4;
    com.google.firebase.database.FirebaseDatabase database;

    String s="0";
    String k="1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        try{
            getSupportActionBar().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
         database = com.google.firebase.database.FirebaseDatabase.getInstance();

        led1 = database.getReference("LED1_STATUS");
        led2 = database.getReference("LED2_STATUS");
        led3 = database.getReference("LED3_STATUS");
        led4 = database.getReference("LED4_STATUS");


        led1.getKey();
        led2.getKey();
        led3.getKey();
        led4.getKey();

        com.bumptech.glide.Glide.with(this).asGif().load(com.coresoft.electricalsolutions.R.raw.remove).into(binding.image);

        binding.bulbbton.setOnClickListener(v -> led1.setValue(k, new com.google.firebase.database.DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(com.google.firebase.database.DatabaseError error, com.google.firebase.database.DatabaseReference ref) {
                android.widget.Toast.makeText(com.coresoft.electricalsolutions.MainActivity.this, "bt1", android.widget.Toast.LENGTH_SHORT).show();
            }
        }));
        binding.bulbbtoff.setOnClickListener(v -> led1.setValue(s, new com.google.firebase.database.DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(com.google.firebase.database.DatabaseError error, com.google.firebase.database.DatabaseReference ref) {
                android.widget.Toast.makeText(com.coresoft.electricalsolutions.MainActivity.this, "bt1", android.widget.Toast.LENGTH_SHORT).show();

            }
        }));
        binding.bulb2bton.setOnClickListener(v -> led2.setValue(k, new com.google.firebase.database.DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(com.google.firebase.database.DatabaseError error, com.google.firebase.database.DatabaseReference ref) {
                android.widget.Toast.makeText(com.coresoft.electricalsolutions.MainActivity.this, "bt1", android.widget.Toast.LENGTH_SHORT).show();

            }
        }));
        binding.bulb2btoff.setOnClickListener(v -> led2.setValue(s, new com.google.firebase.database.DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(com.google.firebase.database.DatabaseError error, com.google.firebase.database.DatabaseReference ref) {
                android.widget.Toast.makeText(com.coresoft.electricalsolutions.MainActivity.this, "bt1", android.widget.Toast.LENGTH_SHORT).show();

            }
        }));
        binding.bulb3bton.setOnClickListener(v -> led3.setValue(k, new com.google.firebase.database.DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(com.google.firebase.database.DatabaseError error, com.google.firebase.database.DatabaseReference ref) {
                android.widget.Toast.makeText(com.coresoft.electricalsolutions.MainActivity.this, "bt1", android.widget.Toast.LENGTH_SHORT).show();

            }
        }));
        binding.bulb3btoff.setOnClickListener(v -> led3.setValue(s, new com.google.firebase.database.DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(com.google.firebase.database.DatabaseError error, com.google.firebase.database.DatabaseReference ref) {
                android.widget.Toast.makeText(com.coresoft.electricalsolutions.MainActivity.this, "bt1", android.widget.Toast.LENGTH_SHORT).show();

            }
        }));
        binding.fanbton.setOnClickListener(v -> led4.setValue(k, new com.google.firebase.database.DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(com.google.firebase.database.DatabaseError error, com.google.firebase.database.DatabaseReference ref) {
                android.widget.Toast.makeText(com.coresoft.electricalsolutions.MainActivity.this, "bt1", android.widget.Toast.LENGTH_SHORT).show();

            }
        }));
        binding.fanbtoff.setOnClickListener(v -> led4.setValue(s, new com.google.firebase.database.DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(com.google.firebase.database.DatabaseError error, com.google.firebase.database.DatabaseReference ref) {
                android.widget.Toast.makeText(com.coresoft.electricalsolutions.MainActivity.this, "bt1", android.widget.Toast.LENGTH_SHORT).show();

            }
        }));

    }


}