package com.coresoft.electricalsolutions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.coresoft.electricalsolutions.Modal.ButtonModal;
import com.coresoft.electricalsolutions.Modal.RoomModal;
import com.coresoft.electricalsolutions.Modal.Users;
import com.coresoft.electricalsolutions.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase fd;
    DatabaseReference dr;
    FirebaseUser fu;
    FirebaseAuth.AuthStateListener authStateListener;
    ProgressDialog pd;
    String email, pass, mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        fd = FirebaseDatabase.getInstance();
        fu = auth.getCurrentUser();
        dr = fd.getReference();
        pd = new ProgressDialog(this);


        binding.submit.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                if (binding.name.getText().toString().isEmpty()) {
                    Log.d("this is empty", "empty");
                    Toast.makeText(SignUpActivity.this, "Please Enter A UserName", Toast.LENGTH_SHORT).show();
                } else if (binding.email.getText().toString().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
                } else if (binding.phone.getText().toString().length() != 10) {
                    Toast.makeText(SignUpActivity.this, "Please Enter 10 Digit Mobile Number", Toast.LENGTH_SHORT).show();

                } else if (binding.password.getText().toString().length() < 6) {
                    Toast.makeText(SignUpActivity.this, "Password length is less then 6 letter", Toast.LENGTH_SHORT).show();
                } else {
                    pd.setMessage("Creating New User Please Wait....");
                    pd.setCancelable(false);
                    pd.show();
                    email = binding.email.getText().toString();
                    pass = binding.password.getText().toString();
                }

                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            fu = auth.getCurrentUser();
                            String name = binding.name.getText().toString();
                            String email = binding.email.getText().toString();
                            String mobile = binding.phone.getText().toString();
                            String pass = binding.password.getText().toString();
                            String uid = fu.getUid();
                            String pro_pic = "https://eww-wp-new.s3.ap-south-1.amazonaws.com/wp-content/uploads/2021/05/05134609/smart-light-switches.jpg";
                            String createdAt = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                            final Users user = new Users(email, pass, mobile, name, uid,pro_pic ,createdAt);
                            user.setName(name);
                            user.setMobile(mobile);
                            user.setPass(pass);
                            user.setUid(Objects.requireNonNull(auth.getCurrentUser()).getUid());
                            user.setEmail(email);
                            dr.child("Users").child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        try {
                                            if (fu != null) {
                                                fu.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            pd.dismiss();
                                                            AlertDialog.Builder adb = new AlertDialog.Builder(SignUpActivity.this);
                                                            adb.setTitle("Please Verify Your EmailId");
                                                            adb.setMessage("A Verification Email Is Sent To Your Registered Email Account")
                                                                    .setCancelable(false)
                                                                    .setPositiveButton("Login",
                                                                            new DialogInterface.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(DialogInterface dialog, int which) {
                                                                                    Intent intent1 = new Intent(SignUpActivity.this, LoginActivity.class);
                                                                                    startActivity(intent1);
                                                                                    SignUpActivity.this.finish();
                                                                                }
                                                                            });
                                                            AlertDialog ad = adb.create();
                                                            ad.show();
                                                        }
                                                    }
                                                });
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            Toast.makeText(SignUpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                    else{
                                        Toast.makeText(SignUpActivity.this, "Error" +task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            AlertDialog.Builder adb2 = new AlertDialog.Builder(SignUpActivity.this);
                            adb2.setTitle("Email ID Is Already Existed");
                            adb2.setCancelable(false);
                            adb2.setMessage("This email is already in use. Please enter a different email id or Login to your account")
                                    .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Intent intent1 = new Intent(SignUpActivity.this, LoginActivity.class);
                                            startActivity(intent1);
                                            SignUpActivity.this.finish();


                                        }
                                    });
                            adb2.setNegativeButton("Try Again", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    recreate();
                                }
                            });
                            adb2.create().show();
                            pd.dismiss();
                        }

                    }
                });


            }
        });
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            }
        });

    }


}