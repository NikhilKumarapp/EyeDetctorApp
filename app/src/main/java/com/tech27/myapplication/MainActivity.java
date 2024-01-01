package com.tech27.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MediaController;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pd.lookatme.LookAtMe;

public class MainActivity extends AppCompatActivity {
    private LookAtMe lookAtMe;
    private ImageButton pickMediaButton;
    private static final int PICK_MEDIA_REQUEST_CODE = 1;
    private EditText editText;
    private ImageButton showEditTextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lookAtMe = findViewById(R.id.lookme);
        ImageButton pickMediaButton = findViewById(R.id.imageButton3);
        ImageButton refreshBtn = findViewById(R.id.imageButton5);
        editText = findViewById(R.id.edit_text);
        showEditTextButton = findViewById(R.id.imageButton6);
        lookAtMe.init(this);
        lookAtMe.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.demo));
//         lookAtMe.setVideoPath("https://file-examples.com/wp-content/storage/2017/04/file_example_MP4_640_3MG.mp4");

        lookAtMe.start();
        lookAtMe.setLookMe();

        pickMediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMediaPicker();
            }
        });
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        showEditTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle EditText visibility
                if (editText.getVisibility() == View.VISIBLE) {
                    editText.setVisibility(View.GONE);
                } else {
                    editText.setVisibility(View.VISIBLE);
                    lookAtMe = findViewById(R.id.lookme);
                    String url = editText.getText().toString();
                    Log.d("MainActivity", "The value of number is: " + url);
                    Uri selectedMediaUri = Uri.parse(url);
                    lookAtMe.setVideoURI(selectedMediaUri);
                }
            }
        });
    }

    private void openMediaPicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        startActivityForResult(intent, PICK_MEDIA_REQUEST_CODE);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_MEDIA_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedMediaUri = data.getData();
            lookAtMe.setVideoURI(selectedMediaUri);

            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(lookAtMe);
            lookAtMe.setMediaController(mediaController);
            lookAtMe.start();
        }
    }



}
