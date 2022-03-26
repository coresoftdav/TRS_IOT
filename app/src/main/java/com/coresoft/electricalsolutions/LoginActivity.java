package com.coresoft.electricalsolutions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.coresoft.electricalsolutions.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() !=null){
            startActivity(new Intent(LoginActivity.this,Dashboard.class));
            finish();
        }
        binding.signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(i);
                LoginActivity.this.finish();
            }
        });
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,pass;
                email = binding.logEmail.getText().toString();
                pass = binding.logPass.getText().toString();
                if (email.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please Enter a Valid Email Account", Toast.LENGTH_SHORT).show();
                }else if (pass.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please Enter A Valid Password", Toast.LENGTH_SHORT).show();
                }else {
                    auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(LoginActivity.this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser fu = auth.getCurrentUser();
                                if (fu !=null){
                                    if (fu.isEmailVerified()){
                                        Intent intent = new Intent(LoginActivity.this,Dashboard.class);
                                        setResult(RESULT_OK,null);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        AlertDialog.Builder adb = new AlertDialog.Builder(LoginActivity.this);
                                        adb.setTitle("Email Verification..");
                                        adb.setMessage("Please Verify The Link Send On Your Registered Email Id")
                                                .setCancelable(false).setPositiveButton("Verify", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                             try {
                                                 Intent i = new Intent(Intent.ACTION_MAIN);
                                                 i.addCategory(Intent.CATEGORY_APP_EMAIL);
                                                 startActivity(i);
                                             } catch (Exception e) {
                                                 e.printStackTrace();
                                                 Toast.makeText(LoginActivity.this, "There is no email client installed." + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                             }
                                            }
                                        });
                                        adb.setPositiveButton("Get New Link", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                fu.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            try {
                                                                Intent i = new Intent(Intent.ACTION_MAIN);
                                                                i.addCategory(Intent.CATEGORY_APP_EMAIL);
                                                                startActivity(i);
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                                Toast.makeText(LoginActivity.this,  e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }else{
                                                            Toast.makeText(LoginActivity.this, "Server Not Responding Please try Again After Some Time.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                            }
                                        });
                                    }
                                }
                            }else{
                                Toast.makeText(LoginActivity.this, "This Email Id is Not Registered. Create a new account or Try Valid Email Account. ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}