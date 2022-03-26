package com.coresoft.electricalsolutions.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.coresoft.electricalsolutions.Modal.ButtonModal;
import com.coresoft.electricalsolutions.Modal.RoomModal;
import com.coresoft.electricalsolutions.R;
import com.coresoft.electricalsolutions.databinding.FragmentButtonBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class ButtonFragment extends Fragment {

    FragmentButtonBinding binding;
    DatabaseReference buttonReff;
    FirebaseAuth auth;

    public String roomname, roomid;

    ArrayList<ButtonModal> arrayList;

    RoomModal rm = new RoomModal();
    ButtonModal bm = new ButtonModal();


    public int count;

    public ButtonFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentButtonBinding.inflate(inflater, container, false);
        auth = FirebaseAuth.getInstance();
        arrayList = new ArrayList<>();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            roomid = bundle.getString("roomid");
        }

            buttonReff = FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).child("Room").child(roomid);


        buttonReff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rm = snapshot.getValue(RoomModal.class);
                if (rm != null) {
                    roomname = rm.getRoom_name();
                    binding.roomname.setText(roomname);
                } else {
                    Toast.makeText(getContext(), "er empty", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), "" + error.toException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        if (rm != null) {
            roomname = rm.getRoom_name();
            binding.roomname.setText(roomname);
        }


        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("roomid", roomid);
                Fragment fag = new AddButtonFragment();
                fag.setArguments(b);
                FragmentManager fm = getParentFragmentManager();
                fm.beginTransaction().replace(R.id.fag_c, fag).commit();

            }
        });
        binding.B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonReff.child("B1").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            bm = snapshot.getValue(ButtonModal.class);

                            if (bm !=null){
                                int i = bm.getButton_status();
                                if (i == 0){
                                    Toast.makeText(getContext(), "0", Toast.LENGTH_SHORT).show();
                                    buttonReff.child("B1").child("button_status").setValue(1);
                                    Glide.with(ButtonFragment.this).load(R.drawable.on).into(binding.B1Status);
                                }else{
                                    Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();

                                    buttonReff.child("B1").child("button_status").setValue(0);
                                    Glide.with(ButtonFragment.this).load(R.drawable.off).into(binding.B1Status);

                                }
                            }

                            }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(getContext(), ""+ error.toException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        binding.B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonReff.child("B2").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            bm = snapshot.getValue(ButtonModal.class);

                            if (bm !=null){
                                int i = bm.getButton_status();
                                if (i == 0){
                                    Toast.makeText(getContext(), "0", Toast.LENGTH_SHORT).show();
                                    buttonReff.child("B2").child("button_status").setValue(1);
                                    Glide.with(ButtonFragment.this).load(R.drawable.on).into(binding.B2Status);

                                }else{
                                    Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();

                                    buttonReff.child("B2").child("button_status").setValue(0);
                                    Glide.with(ButtonFragment.this).load(R.drawable.off).into(binding.B2Status);

                                }
                            }

                            }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(getContext(), ""+ error.toException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        binding.B3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonReff.child("B3").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            bm = snapshot.getValue(ButtonModal.class);

                            if (bm !=null){
                                int i = bm.getButton_status();
                                if (i == 0){
                                    Toast.makeText(getContext(), "0", Toast.LENGTH_SHORT).show();
                                    buttonReff.child("B3").child("button_status").setValue(1);
                                    Glide.with(ButtonFragment.this).load(R.drawable.on).into(binding.B3Status);

                                }else{
                                    Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();

                                    buttonReff.child("B3").child("button_status").setValue(0);
                                    Glide.with(ButtonFragment.this).load(R.drawable.off).into(binding.B3Status);

                                }
                            }

                            }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(getContext(), ""+ error.toException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        binding.B4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonReff.child("B4").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            bm = snapshot.getValue(ButtonModal.class);

                            if (bm !=null){
                                int i = bm.getButton_status();
                                if (i == 0){
                                    Toast.makeText(getContext(), "0", Toast.LENGTH_SHORT).show();
                                    buttonReff.child("B4").child("button_status").setValue(1);
                                    Glide.with(ButtonFragment.this).load(R.drawable.on).into(binding.B4Status);

                                }else{
                                    Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();

                                    buttonReff.child("B4").child("button_status").setValue(0);
                                    Glide.with(ButtonFragment.this).load(R.drawable.off).into(binding.B4Status);

                                }
                            }

                            }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(getContext(), ""+ error.toException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        buttonReff.child("B1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              if (snapshot.exists()){
                  ButtonModal bm;
                  bm = snapshot.getValue(ButtonModal.class);
                  if (bm != null){
                      binding.B1BtnName.setText(bm.getButton_name());
                      binding.B1ButtonTime.setText(String.valueOf(bm.getActive_time()));
                      binding.B1Id.setText(bm.getButton_id());
                      Glide.with(ButtonFragment.this).load(bm.getButton_pic()).into(binding.B1Img);
                      if (bm.getButton_status() == 0){
                          Glide.with(ButtonFragment.this).load(R.drawable.off).into(binding.B1Status);
                      }else{
                          Glide.with(ButtonFragment.this).load(R.drawable.on).into(binding.B1Status);

                      }

                  }
              }else{
                  binding.B1.setClickable(false);
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), ""+ error.toException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        buttonReff.child("B2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              if (snapshot.exists()){
                  ButtonModal bm;
                  bm = snapshot.getValue(ButtonModal.class);
                  if (bm != null){
                      binding.B2Name.setText(bm.getButton_name());
                      binding.B2Time.setText(String.valueOf(bm.getActive_time()));
                      binding.B2Id.setText(bm.getButton_id());
                      Glide.with(ButtonFragment.this).load(bm.getButton_pic()).into(binding.B2Img);

                      if (bm.getButton_status() == 0){
                          Glide.with(ButtonFragment.this).load(R.drawable.off).into(binding.B2Status);
                      }else{
                          Glide.with(ButtonFragment.this).load(R.drawable.on).into(binding.B2Status);

                      }

                  }
              }
              else{
                  binding.B2.setClickable(false);
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), ""+ error.toException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        buttonReff.child("B3").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              if (snapshot.exists()){
                  ButtonModal bm;
                  bm = snapshot.getValue(ButtonModal.class);
                  if (bm != null){
                      binding.B3Name.setText(bm.getButton_name());
                      binding.B3Time.setText(String.valueOf(bm.getActive_time()));
                      binding.B3Id.setText(bm.getButton_id());
                      Glide.with(ButtonFragment.this).load(bm.getButton_pic()).into(binding.B3Img);
                      if (bm.getButton_status() == 0){
                          Glide.with(ButtonFragment.this).load(R.drawable.off).into(binding.B3Status);
                      }else{
                          Glide.with(ButtonFragment.this).load(R.drawable.on).into(binding.B3Status);

                      }

                  }
              }else{
                  binding.B3.setClickable(false);
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), ""+ error.toException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        buttonReff.child("B4").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              if (snapshot.exists()){
                  ButtonModal bm;
                  bm = snapshot.getValue(ButtonModal.class);
                  if (bm != null){
                      binding.B4Name.setText(bm.getButton_name());
                      binding.B4Time.setText(String.valueOf(bm.getActive_time()));
                      binding.B4Id.setText(bm.getButton_id());
                      Glide.with(ButtonFragment.this).load(bm.getButton_pic()).into(binding.B4Img);
                      if (bm.getButton_status() == 0){
                          Glide.with(ButtonFragment.this).load(R.drawable.off).into(binding.B4Status);
                      }else{
                          Glide.with(ButtonFragment.this).load(R.drawable.on).into(binding.B4Status);

                      }

                  }
              }else{
                  binding.B4.setClickable(false);
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), ""+ error.toException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}