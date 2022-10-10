package com.example.retrofit_nashbud;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.cast.framework.media.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Save_Data extends AppCompatActivity {

    CircleImageView circleImageView;
    EditText _name;
    Button saveData,callApi;
    Uri filepath;
    Bitmap bitmap;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_data);


        circleImageView = findViewById(R.id.civ);
        _name = findViewById(R.id.fullName);
        saveData = findViewById(R.id.save_btn);
        callApi = findViewById(R.id.apiBtn);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //click on image and open gallery
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(Save_Data.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent= new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Please Select image"),1);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                        token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        //upload image into firebase
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFirebase();
            }
        });
        //call Api and Retrive data from server
        callApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Save_Data.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveToFirebase()
    {
        String name = _name.getText().toString();
        //Checking name is empty or not
        if(name.isEmpty()){
            _name.setError("Enter an full Name");
            _name.requestFocus();
            return;
        }
        if (filepath!= null) {
            //progressBar
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("File Uploading...");
            dialog.show();
            //save to storage
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference uploader = storage.getReference().child("image" + new Random().nextInt(500));
            uploader.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //fetch image url and save that in realtime db as string
                            uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageurl = uri.toString();

                                    DatabaseReference root = firebaseDatabase.getReference("nashbud");

                                    data_model obj = new data_model(name, uri.toString());
                                    root.push().setValue(obj);
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "File Uploaded", Toast.LENGTH_LONG).show();
                                    Log.d("TAG", "onSuccess: ");

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "onFailure: " + e.toString());
                                    Toast.makeText(getApplicationContext(), "File not Uploaded", Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    })
                    //this is just show persentage of uploading in dialog box
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            float percent = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            dialog.setMessage("Uploaded :" + (int) percent + " %");
                        }
                    });
        }else{
            Toast.makeText(Save_Data.this, "Please Select an Image", Toast.LENGTH_LONG).show();
            Log.d("TAG", "saveToFirebase: choole file");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==1 && resultCode==RESULT_OK)
        {
            filepath=data.getData();
            try
            {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                circleImageView.setImageBitmap(bitmap);
            }catch (Exception ex)
            {
                Log.d("TAG", "onActivityResult: "+ex.toString());
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}