package com.coresoft.electricalsolutions.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coresoft.electricalsolutions.Modal.RoomModal;
import com.coresoft.electricalsolutions.R;
import com.coresoft.electricalsolutions.WifiSwitchesActivity;
import com.coresoft.electricalsolutions.databinding.ActivityWifiSwitchesBinding;
import com.coresoft.electricalsolutions.databinding.FragmentRoomBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.Timer;


public class RoomFragment extends Fragment {

    DatabaseReference roomReff;
    FragmentRoomBinding binding;
    FirebaseAuth auth;

    public String deviceid, roomname;
    public int count;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRoomBinding.inflate(inflater,container,false);

        auth = FirebaseAuth.getInstance();

        roomReff = FirebaseDatabase.getInstance().getReference().child("Users").child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).child("Room");



        binding.roomRv.setLayoutManager(new GridLayoutManager(getActivity(),1));

        return binding.getRoot();

    }

    @Override
    public void onStart() {
        super.onStart();

        roomReff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    RoomModal rm = snapshot.getValue(RoomModal.class);
                    if (rm !=null){
                        if (deviceid != null) {
                            roomReff.child(deviceid).child("status").setValue(0);
                        }

                        Handler h = new Handler();
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (deviceid != null) {
                                    roomReff.child(deviceid).child("status").setValue(0);
                                }
                            }
                        }, 3000);


                        FirebaseRecyclerOptions<RoomModal> options = new FirebaseRecyclerOptions.Builder<RoomModal>()
                                .setQuery(roomReff, RoomModal.class)
                                .build();
                        FirebaseRecyclerAdapter<RoomModal, RoomNameViewHolder> adapter =
                                new FirebaseRecyclerAdapter<RoomModal, RoomNameViewHolder>(options) {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    protected void onBindViewHolder(@NonNull RoomNameViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull RoomModal model) {

                                        holder.roomname.setText(model.getRoom_name());
                                        holder.deviceId.setText(model.getDevice_id());
                                        holder.online_time.setText(String.valueOf(count));
                                        deviceid = String.valueOf(model.getDevice_id());

                                        if (model.getRoom_name() != null) {
                                            roomname = model.getRoom_name();
                                        }
                                        Glide.with(RoomFragment.this).load(R.drawable.off).into(holder.status);


                                        if (model.getStatus() == 0) {
                                            Glide.with(RoomFragment.this).load(R.drawable.off).into(holder.status);

                                        } else {
                                            Glide.with(RoomFragment.this).load(R.drawable.on).into(holder.status);

                                        }


                                        Glide.with(RoomFragment.this).load(model.getRoom_img()).into(holder.roompic);

                                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String roomid = getRef(position).getKey();

                                                Bundle bundle = new Bundle();

                                                bundle.putString("roomid", roomid);
                                                Fragment frag = new ButtonFragment();
                                                frag.setArguments(bundle);
                                                FragmentManager fm = getParentFragmentManager();
                                                fm.beginTransaction().replace(R.id.fag_c, frag).commit();

                                            }
                                        });
                                    }

                                    @NonNull
                                    @Override
                                    public RoomNameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_rv_layout, parent, false);
                                        return new RoomNameViewHolder(v);
                                    }
                                };
                        binding.roomRv.setAdapter(adapter);
                        adapter.startListening();
                }
            }else{
                    binding.errorText.setVisibility(View.VISIBLE);
                    binding.roomRv.setVisibility(View.GONE);
                    String s = "Please Create New Room....";
                    binding.errorText.setText(s);
                }



        }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    public static class RoomNameViewHolder extends RecyclerView.ViewHolder{
        TextView roomname , deviceId , online_time;
        ImageView roompic,status;
        public RoomNameViewHolder(@NonNull View itemView) {
            super(itemView);
            roomname = itemView.findViewById(R.id.room_name);
            deviceId = itemView.findViewById(R.id.device_id);
            roompic = itemView.findViewById(R.id.room_img);
            online_time = itemView.findViewById(R.id.online_time);
            status = itemView.findViewById(R.id.online_status);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (deviceid !=null){
            roomReff.child(deviceid).child("status").setValue(0);}

    }

    @Override
    public void onPause() {
        super.onPause();
        if (deviceid !=null){
            roomReff.child(deviceid).child("status").setValue(0);}

    }
}