package com.prakhar.ibeats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RecyclerView recyclerView;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        ArrayList<File> mySongs = fetchSongs(Environment.getExternalStorageDirectory());
                        String [] items = new String[mySongs.size()];
                        for(int i = 0; i < mySongs.size(); i++){
                            items[i] = mySongs.get(i).getName().replace(".mp3", "");

                        }

                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        CustomAdapter songsAdapter = new CustomAdapter(items, MainActivity.this);
                        recyclerView.setAdapter(songsAdapter);

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                .check();
    }

    //Method to fetch songs from external directory
    public ArrayList<File> fetchSongs(File file){
        ArrayList<File> arrayList = new ArrayList<>();
        //Listing all the files
        File[] songs = file.listFiles();
        if(songs!=null){
            for(File myFile: songs){
                //Conditions to get a valid song items
                if(!myFile.isHidden() && myFile.isDirectory()){
                    arrayList.addAll(fetchSongs(myFile));
                } else {
                    if(myFile.getName().endsWith(".mp3") && !myFile.getName().startsWith(".")){
                        arrayList.add(myFile);
                    }
                }
            }
        }
        return arrayList;
    }
}