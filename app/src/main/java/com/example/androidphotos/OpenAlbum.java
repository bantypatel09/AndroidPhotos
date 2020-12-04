package com.example.androidphotos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class OpenAlbum extends AppCompatActivity {

    private ListView listview;
    private Album currAlbum;
    private Button removePhotoButton;
    private Button displayPhotoButton;
    private int selectedIndex = -1;

    ArrayList<Photo> photos= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setting up display
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_album);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //setting up buttons
        Button addPhoto;

        //retrieving current album
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        currAlbum = (Album)args.getSerializable("ALBUM");
        photos = currAlbum.getPhotos();

        //setting up list of photos
        listview = findViewById(R.id.PhotoList);
        listview.setAdapter(new ArrayAdapter<>(this,R.layout.image_list_item, photos));
        listview.setClickable(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                selectedIndex = position;
            }
        });

        //setting on action listener for display photo button
        addPhoto = findViewById(R.id.addPhotoButton);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhotoActivity();
            }
        });

        //setting on action listener for add photo button
        displayPhotoButton = findViewById(R.id.displayPhotoButton);
        displayPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPhotoActivity();
            }
        });
    }

    public void pickPhotoActivity(){
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 1);
    }

    public void displayPhotoActivity(){
        Intent intent = new Intent(this, DisplayPhoto.class);
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
            case 1:
                if(resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    Photo newPhoto = new Photo("", new ArrayList<>(), selectedImage.toString());
                    currAlbum.addPhoto(newPhoto);
                    update();
                    Intent intent = new Intent(this, AddPhoto.class);
                    Bundle args = new Bundle();
                    args.putSerializable("PHOTO", (Serializable)newPhoto);
                    intent.putExtra("BUNDLE", args);
                    startActivity(intent);
                }
                break;
        }
    }

    //method to update the list view
    public void update(){
        PhotosAdapter customAdapter = new PhotosAdapter(this, photos);
        listview.setAdapter(customAdapter);
    }

}