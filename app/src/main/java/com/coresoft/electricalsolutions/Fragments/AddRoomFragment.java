package com.coresoft.electricalsolutions.Fragments;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coresoft.electricalsolutions.Modal.RoomModal;
import com.coresoft.electricalsolutions.R;
import com.coresoft.electricalsolutions.WifiSwitchesActivity;
import com.coresoft.electricalsolutions.databinding.ActivityWifiSwitchesBinding;
import com.coresoft.electricalsolutions.databinding.FragmentAddRoomBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;


public class AddRoomFragment extends Fragment {


    FragmentAddRoomBinding binding;

    FirebaseAuth auth;
    StorageReference storageReference;
    DatabaseReference dr;
    FirebaseDatabase fd;
    ProgressDialog pro;


    String image;
    public AddRoomFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding= FragmentAddRoomBinding.inflate(inflater,container,false);
       pro = new ProgressDialog(getContext());
       pro.setMessage("Please Wait For Uploading..");
       pro.setCancelable(false);

        final String userid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
       fd = FirebaseDatabase.getInstance();
       dr = fd.getReference();
       auth = FirebaseAuth.getInstance();




        ActivityResultLauncher<String> arl = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result !=null){
                    Uri uri = Uri.parse(result.toString());
                    String img = result.getPath();


                    storageReference = FirebaseStorage.getInstance().getReference().child(userid).child("room_pic");

                    Glide.with(AddRoomFragment.this).load(uri).into(binding.roomImg);
                    pro.show();
                    storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    pro.dismiss();
                                    image = uri.toString();
                                    Glide.with(AddRoomFragment.this).load(image).into(binding.roomImg);


                                }
                            });
                        }
                    });



                }

            }
        });
        binding.roomImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arl.launch("image/*");
            }
        });
       binding.submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ProgressDialog pd = new ProgressDialog(getContext());
               pd.setCancelable(false);
               pd.setMessage("Please Wait While We Create New Room On The Server...");
               if (binding.roomName.getText().toString().isEmpty()){
                   Toast.makeText(getContext(), "Please Enter RoomName", Toast.LENGTH_SHORT).show();
               }else if (binding.trsId.getText().toString().isEmpty()){
                   Toast.makeText(getActivity(), "Please Enter Device Id Printed On the Device.", Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(getActivity(), "text" + binding.roomName.getText().toString() + binding.trsId.getText().toString(), Toast.LENGTH_SHORT).show();


                   pd.show();

               }
               dr.child("Users").child(userid).child("Room").addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       if (snapshot.hasChild(binding.roomName.getText().toString())){
                           Toast.makeText(getContext(), "Room Name Already Saved", Toast.LENGTH_SHORT).show();
                           pd.dismiss();
                       }else{

                           String room_name,room_pic,deviceid;
                           int status;
                           long online_time;
                           room_name = binding.roomName.getText().toString().trim();
                           deviceid = binding.trsId.getText().toString().trim();
                           room_pic = image;
                           status = 0;
                           online_time = 0;
                           if (room_pic == null){
                               room_pic = "https://www.google.com/imgres?imgurl=https%3A%2F%2Fhips.hearstapps.com%2Fhmg-prod.s3.amazonaws.com%2Fimages%2Fliving-room-1-1557931775.jpg&imgrefurl=https%3A%2F%2Fwww.elledecor.com%2Fdesign-decorate%2Froom-ideas%2Fg27479671%2Fliving-room-wallpaper-ideas%2F&tbnid=-269xj50fpMHTM&vet=12ahUKEwj02Mn2lMD2AhWl_TgGHXk1AyAQMygGegUIARDsAQ..i&docid=cMGr5dH9eVpygM&w=980&h=1287&q=room%20wallpaper&client=ubuntu&ved=2ahUKEwj02Mn2lMD2AhWl_TgGHXk1AyAQMygGegUIARDsAQ";
                           }

                           RoomModal rm = new RoomModal(room_name,room_pic,deviceid,status,online_time);
                           dr.child("Users").child(userid).child("Room").child(deviceid)
                                   .setValue(rm).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if (task.isSuccessful()){
                                       Toast.makeText(getContext(), "Room Successfully Created", Toast.LENGTH_SHORT).show();

                                       pd.dismiss();
                                       Fragment fag = new RoomFragment();
                                       FragmentManager fm = getParentFragmentManager();
                                       fm.beginTransaction().replace(R.id.fag_c,fag).commit();

                                   }else{
                                       Toast.makeText(getContext(), "Something Went Wrong Please Try Again", Toast.LENGTH_SHORT).show();
                                       pd.dismiss();
                                   }
                               }
                           });
                       }

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {
                       Toast.makeText(getContext(), "Error"+ error.toException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                       pd.dismiss();
                   }
               });

           }
       });


        return binding.getRoot();
    }
}