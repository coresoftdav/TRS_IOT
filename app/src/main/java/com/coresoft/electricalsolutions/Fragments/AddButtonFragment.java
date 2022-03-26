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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coresoft.electricalsolutions.Modal.ButtonModal;
import com.coresoft.electricalsolutions.Modal.RoomModal;
import com.coresoft.electricalsolutions.R;
import com.coresoft.electricalsolutions.databinding.FragmentAddButtonBinding;
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

import java.util.Objects;

public class AddButtonFragment extends Fragment {

    FragmentAddButtonBinding binding;
    FirebaseAuth auth;
    StorageReference storageReference;
    DatabaseReference dr;
    FirebaseDatabase fd;
    ProgressDialog pro;
    String image,buttonid,roomid;
    public AddButtonFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddButtonBinding.inflate(inflater,container,false);
        pro = new ProgressDialog(getContext());
        pro.setMessage("Please Wait For Uploading..");
        pro.setCancelable(false);

        final String userid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        fd = FirebaseDatabase.getInstance();
        dr = fd.getReference();
        auth = FirebaseAuth.getInstance();

        Bundle bundle = this.getArguments();
        if (bundle !=null){
            roomid = bundle.getString("roomid");
        }
        ActivityResultLauncher<String> arl = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result !=null){
                    Uri uri = Uri.parse(result.toString());
                    String img = result.getPath();

                    storageReference = FirebaseStorage.getInstance().getReference().child(userid).child("button_pic");

                    Glide.with(AddButtonFragment.this).load(uri).into(binding.buttonImg);
                    pro.show();
                    storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    pro.dismiss();
                                    image = uri.toString();
                                    Glide.with(AddButtonFragment.this).load(image).into(binding.buttonImg);


                                }
                            });
                        }
                    });



                }

            }
        });
        binding.buttonImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arl.launch("image/*");

            }
        });
        ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(getContext(),R.array.button_id, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adp.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        binding.buttonId.setAdapter(adp);
        binding.buttonId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                buttonid = (String) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { } });
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog pd = new ProgressDialog(getContext());
                pd.setCancelable(false);
                pd.setMessage("Please Wait While We Create New Button On The Server...");
                if (binding.buttonName.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please Enter Button name", Toast.LENGTH_SHORT).show();
                }else if (buttonid.isEmpty()){
                    Toast.makeText(getActivity(), "Please Select A Button Id.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "text" + binding.buttonName.getText().toString() + buttonid, Toast.LENGTH_SHORT).show();


                    pd.show();

                }
                dr.child("Users").child(userid).child("Room").child(roomid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(buttonid)){
                            Toast.makeText(getContext(), "Button Already Saved", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }else{

                            String button_name,button_pic,btn_id;
                            int btn_status;
                            long active_time;
                            button_name = binding.buttonName.getText().toString().trim();
                            btn_id = buttonid;
                            button_pic = image;
                            btn_status = 0;
                            active_time = 0;
                            if (button_pic == null){
                                button_pic = "https://firebasestorage.googleapis.com/v0/b/electrical-4c119.appspot.com/o/idea-bulb.png?alt=media&token=0246dd98-8d42-4538-8c75-1deec6791152";
                            }

                            ButtonModal bm = new ButtonModal(button_name,button_pic,btn_id,btn_status,active_time);
                            dr.child("Users").child(userid).child("Room").child(roomid).child(bm.getButton_id())
                                    .setValue(bm).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getContext(), "Switch Successfully Created", Toast.LENGTH_SHORT).show();


                                        pd.dismiss();
                                        Fragment fag = new ButtonFragment();
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