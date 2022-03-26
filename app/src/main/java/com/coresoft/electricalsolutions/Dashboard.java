package com.coresoft.electricalsolutions;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coresoft.electricalsolutions.Fragments.AddRoomFragment;
import com.coresoft.electricalsolutions.Modal.Users;
import com.coresoft.electricalsolutions.databinding.ActivityDashboardBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class Dashboard extends AppCompatActivity {
    ActivityDashboardBinding binding;
    FirebaseDatabase fd;
    DatabaseReference dr;
    FirebaseAuth auth;
    FirebaseUser fu;
    StorageReference storageReference;
    public String profile_pic,username,image;
    public ShapeableImageView iv;
      Users users;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fd = FirebaseDatabase.getInstance();
        fu = FirebaseAuth.getInstance().getCurrentUser();
        dr = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();

        ActivityResultLauncher<String> arl = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                ProgressDialog pro = new ProgressDialog(Dashboard.this);
                pro.setCancelable(false);
                pro.setTitle("**Uploading**");
                pro.setMessage("Profile Image Is Uploading Please Wait***********");
                pro.setProgress(100);
                pro.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                if (result !=null){
                    uri = Uri.parse(result.toString());
                    String img = result.getPath();


                    storageReference = FirebaseStorage.getInstance().getReference().child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).child("profile_pic");

                    Glide.with(Dashboard.this).load(uri).into(iv);
                    pro.show();
                    storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    pro.dismiss();
                                    image = uri.toString();
                                    Glide.with(Dashboard.this).load(image).into(iv);


                                }
                            });
                        }
                    });



                }

            }
        });
        binding.settingD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu pop = new PopupMenu(Dashboard.this,binding.settingD);
                pop.getMenuInflater().inflate(R.menu.bot_menu,pop.getMenu());
                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.profile_s){
                            final AlertDialog.Builder d = new AlertDialog.Builder(Dashboard.this);
                            View v=getLayoutInflater().inflate(R.layout.profile_dialog,null);

                            iv = v.findViewById(R.id.profile_pic);
                            TextInputEditText et = v.findViewById(R.id.ett);
                            Button submit,cancel;
                            submit = v.findViewById(R.id.submit_profile);
                            cancel = v.findViewById(R.id.cancel);

                            d.setView(v);
                            final AlertDialog ad = d.create();
                            ad.setCanceledOnTouchOutside(false);

                            iv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    arl.launch("image/*");


                                }
                            });
                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ad.dismiss();
                                }
                            });


                            submit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (Objects.requireNonNull(et.getText()).toString().isEmpty()){
                                        Toast.makeText(Dashboard.this, "empty", Toast.LENGTH_SHORT).show();
                                    }else{

                                        if (image != null){
                                        dr.child(fu.getUid()).child("profile_pic").setValue(image);}
                                        dr.child(fu.getUid()).child("name").setValue(et.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(Dashboard.this, "Profile Updated Successfully...", Toast.LENGTH_LONG).show();
                                                    ad.dismiss();
                                                }
                                            }
                                        });



                                }}
                            });

                            ad.setView(v);
                            ad.show();
                        }
                        return true;
                    }
                });
                pop.show();
            }
        });



        dr.child(fu.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                assert users != null;
                username = users.getName();
                profile_pic = users.getProfile_pic();
                Glide.with(Dashboard.this).load(profile_pic).into(binding.profilePic);
                binding.username.setText(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Dashboard.this, "error" + error.toException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.bookTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dashboard.this, "book tech", Toast.LENGTH_SHORT).show();
            }
        });
        binding.WiFiSwitches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,WifiSwitchesActivity.class));

            }
        });


    }
}