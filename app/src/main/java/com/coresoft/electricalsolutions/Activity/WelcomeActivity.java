package com.coresoft.electricalsolutions.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.coresoft.electricalsolutions.Dashboard;
import com.coresoft.electricalsolutions.LoginActivity;
import com.coresoft.electricalsolutions.R;
import com.coresoft.electricalsolutions.databinding.ActivityWelcomeBinding;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    ActivityWelcomeBinding binding;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth= FirebaseAuth.getInstance();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(auth.getCurrentUser() !=null){
                    startActivity(new Intent(WelcomeActivity.this, Dashboard.class));
                    finish();
                }else{
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                }
            }
        },2000);
    }
}