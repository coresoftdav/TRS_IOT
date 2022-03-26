package com.coresoft.electricalsolutions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.coresoft.electricalsolutions.Fragments.AddButtonFragment;
import com.coresoft.electricalsolutions.Fragments.AddRoomFragment;
import com.coresoft.electricalsolutions.Fragments.ButtonFragment;
import com.coresoft.electricalsolutions.Fragments.RoomFragment;
import com.coresoft.electricalsolutions.Modal.Users;
import com.coresoft.electricalsolutions.databinding.ActivityWifiSwitchesBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class WifiSwitchesActivity extends AppCompatActivity {
    ActivityWifiSwitchesBinding binding;

    DatabaseReference reference;
    FirebaseAuth auth;
    StorageReference sr;
    FirebaseUser fu;
    FirebaseDatabase fd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWifiSwitchesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        sr = FirebaseStorage.getInstance().getReference();
        fu = auth.getCurrentUser();
        fd = FirebaseDatabase.getInstance();
        Fragment fag = new RoomFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fag_c,fag).commit();




        fd.getReference().child("Users").child(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() !=null){
                        Users users = snapshot.getValue(Users.class);

                            assert users != null;
                            String name = users.getName();
                        binding.username.setText(name);}
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        reference = FirebaseDatabase.getInstance().getReference();
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fag1 = null;
                fag1 = new AddRoomFragment();
                FragmentManager fagMan = getSupportFragmentManager();
                fagMan.beginTransaction().replace(R.id.fag_c,fag1).commit();

            }
        });

    }

    @Override
    protected void onResume() {

        super.onResume();
        Fragment fag = new RoomFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fag_c,fag).commit();

    }


}