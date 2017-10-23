package com.example.tinhnguyen.chat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.example.tinhnguyen.chat.R.id.btnlogin;

public class Register extends AppCompatActivity {
    EditText txtbusername, txtbpass, txtbconfirmpass, txtbemail;
    TextView txtvlogin;
    Button btnregister;
    ImageView imgvavatar;
    int RESULT_LOAD_IMG=1;
    FirebaseDatabase database;
    DatabaseReference myref;
    String stringbmapavatar;
    User newuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        database = FirebaseDatabase.getInstance();
        myref = database.getReference();
        AnhXa();

    }
    void AnhXa(){
        txtbusername = (EditText)findViewById(R.id.txtbusername);
        txtbpass = (EditText)findViewById(R.id.txtbpass);
        txtbconfirmpass = (EditText)findViewById(R.id.txtbconfirmpass);
        btnregister = (Button)findViewById(btnlogin);
        imgvavatar = (ImageView)findViewById(R.id.imgvavatar);
        txtvlogin = (TextView)findViewById(R.id.txtvlogin);
        txtbemail = (EditText)findViewById(R.id.txtbemail);
        btnregister = (Button)findViewById(R.id.btnregister);
        Bitmap bmavatar= ((BitmapDrawable) imgvavatar.getDrawable()).getBitmap();    //
        stringbmapavatar = getEncoded64ImageStringFromBitmap(bmavatar);              // lay anh mac dinh thanh anh dai dien
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnregister.setEnabled(false);
                btnregister.setBackgroundColor(Color.rgb(110,112,190));
                if(txtbusername.getText().toString().length()<1){
                    Toast.makeText(getApplicationContext(), "Please check username!",Toast.LENGTH_LONG).show();
                    btnregister.setEnabled(true);
                    btnregister.setBackgroundColor(Color.rgb(63,81,181));
                    return;
                }
                if(txtbpass.getText().toString().length()<6 || txtbpass.getText().toString().length()>32){
                    Toast.makeText(getApplicationContext(), "Your password be between 6-32 character",Toast.LENGTH_LONG).show();
                    btnregister.setEnabled(true);
                    btnregister.setBackgroundColor(Color.rgb(63,81,181));
                    return;
                }
                if(txtbpass.getText().toString().equals(txtbconfirmpass.getText().toString())== false  ){
                    Toast.makeText(getApplicationContext(), "Your password not match!",Toast.LENGTH_LONG).show();
                    btnregister.setEnabled(true);
                    btnregister.setBackgroundColor(Color.rgb(63,81,181));
                    return;
                }
                if(txtbemail.getText().toString().length()<4){
                    Toast.makeText(getApplicationContext(), "Please email is corect !",Toast.LENGTH_LONG).show();
                    btnregister.setEnabled(true);
                    btnregister.setBackgroundColor(Color.rgb(63,81,181));
                    return;
                }
                Log.d("kiemtra",txtbemail.getText().toString().length()+"");

                myref.child("User").addListenerForSingleValueEvent(new ValueEventListener() {  // kiểm tra xem có con user hay ko
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChild(txtbusername.getText().toString())) {  //neu co roi
                            Toast.makeText(getApplicationContext(),"Username is exist",Toast.LENGTH_LONG).show();
                            btnregister.setEnabled(true);
                            btnregister.setBackgroundColor(Color.rgb(63,81,181));
                        }
                        else{ ///neu chua co

                            newuser = new User(txtbusername.getText().toString(),txtbemail.getText().toString(),txtbpass.getText().toString(),stringbmapavatar);
                            myref.child("User").child(newuser.userName).setValue(newuser); // them user vao csdl

                            myref.child("User").addListenerForSingleValueEvent(new ValueEventListener() {  // kiểm tra xem them thanh cong hay chua
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    if (snapshot.hasChild(newuser.userName)) {
                                        Toast.makeText(getApplicationContext(),"Sign up success",Toast.LENGTH_LONG).show();
                                        btnregister.setEnabled(true);
                                        btnregister.setBackgroundColor(Color.rgb(63,81,181));
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"Sign up fail",Toast.LENGTH_LONG).show();
                                        btnregister.setEnabled(true);
                                        btnregister.setBackgroundColor(Color.rgb(63,81,181));
                                    }

                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    btnregister.setEnabled(true);
                                    btnregister.setBackgroundColor(Color.rgb(63,81,181));
                                }
                            });
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        btnregister.setEnabled(true);
                        btnregister.setBackgroundColor(Color.rgb(63,81,181));
                    }
                });

            }
        });

        txtvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this,MainActivity.class);
                startActivity(intent);
            }
        });
        imgvavatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });
    }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                stringbmapavatar = getEncoded64ImageStringFromBitmap(selectedImage);
                imgvavatar.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(Register.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(Register.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) { // chuyen bitmap sang string base64
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.DEFAULT);
        return imgString;
    }
    public Bitmap Base64toBitmap(String imgString){ // chuyen string base64 sang bitmap
        byte[] decodedString = Base64.decode(imgString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}
