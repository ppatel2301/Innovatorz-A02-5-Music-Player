package comp3350.sonicmatic.presentation;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.application.Services;
import comp3350.sonicmatic.business.AccessProfile;
import comp3350.sonicmatic.business.AccessSong;
import comp3350.sonicmatic.databinding.ActivityMainBinding;
import comp3350.sonicmatic.exceptions.NoMusicException;
import comp3350.sonicmatic.interfaces.IPlayer;
import comp3350.sonicmatic.presentation.login.UserViewModel;
import comp3350.sonicmatic.presentation.player.ListeningHistoryMusicAdapter;
import comp3350.sonicmatic.presentation.player.MusicViewModel;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE_PERMISSION = 123;
    private DrawerLayout drawer;
    private ListeningHistoryMusicAdapter adapter;
    private View layout;
    private TextView usernameText;
    private IPlayer player;
    private MusicViewModel musicViewModel;
    private UserViewModel userViewModel;
    private View trackHistoryView;
    private ActivityResultLauncher<String> picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // required that all instances of the music player have access to the context
        // services need application context to access assets folder
        Services.setContext(getApplicationContext());

        musicViewModel = new ViewModelProvider(this).get(MusicViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        //Hide the status bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        addListeningAdapter();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it from the user
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_EXTERNAL_STORAGE_PERMISSION);
        } else {
            // Permission is already granted, proceed with your logic
            initTrackPicker();
        }

        initTrackPicker();

        drawer = binding.drawerProfileLayout;
        usernameText = drawer.findViewById(R.id.profile_username);
        trackHistoryView = findViewById(R.id.track_history_layout);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_profile, R.id.navigation_browse, R.id.navigation_leader_board)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        layout = requireViewById(R.id.collasped_music_layout1);

        binding.navView.setOnItemSelectedListener(item -> {

            if(item.getItemId() == R.id.navigation_profile)
            {
                if(!drawer.isDrawerOpen(GravityCompat.END))
                {
                    updateGetUsername();
                    drawer.openDrawer(GravityCompat.END);
                    layout.setVisibility(View.GONE);
                }else{
                    drawer.closeDrawer(GravityCompat.END);
                    layout.setVisibility(View.VISIBLE);
                }
                return true;
            }else{
                if(drawer.isDrawerOpen(GravityCompat.END))
                {
                    drawer.closeDrawer(GravityCompat.END);
                    layout.setVisibility(View.VISIBLE);
                }
            }
            return NavigationUI.onNavDestinationSelected(item, navController);
        });

        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                updateGetUsername();
                trackHistoryView.setVisibility(View.VISIBLE);

                if(!userViewModel.isLoggedOut())
                {
                    layout.setVisibility(View.VISIBLE);
                }
            }
        });

        ImageView play_pause_collasped = layout.findViewById(R.id.collasped_play_button);
        play_pause_collasped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player = musicViewModel.getPlayer();
                if(player != null)
                {
                    if(player.isPaused())
                    {
                        try {
                            player.resume();
                            play_pause_collasped.setImageResource(R.drawable.baseline_pause_circle_outline_white_24);
                        } catch (NoMusicException e) {
                            throw new RuntimeException(e);
                        }
                    }else{
                        try {
                            player.pause();
                            play_pause_collasped.setImageResource(R.drawable.baseline_play_circle_outline_white_24);
                        } catch (NoMusicException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }else{
                    // Tell the user to select a music to play
                    Toast.makeText(getApplicationContext(), "Select a music to play", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button logout = binding.logout;
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userViewModel.setLoggedOut(true);
                clearBackStack();
            }
        });

        Button upload_tracks = binding.uploadTracks;
        upload_tracks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker.launch("audio/*");

                // Close the drawer and enable the bottom nav view
                drawer.closeDrawer(GravityCompat.END);
                layout.setVisibility(View.VISIBLE);
            }
        });
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        // Check if the requested permission is granted
//        if (requestCode == REQUEST_EXTERNAL_STORAGE_PERMISSION) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted
//                initTrackPicker();
//            }
//        } else {
//            // Permission denied
//            Toast.makeText(this, "Permission denied. You won't be able to access audio files.", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void addListeningAdapter()
    {
        if(adapter == null)
        {
            adapter = ListeningHistoryMusicAdapter.getInstance(new ArrayList<>());
            RecyclerView historyView = requireViewById(R.id.song_history_view);
            historyView.setAdapter(adapter);
            historyView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        }
    }

    private void updateGetUsername()
    {
        // get username from login and update below
        AccessProfile accessProfile = userViewModel.getProfile();
        if(accessProfile != null)
        {
            String displayName = accessProfile.getDisplayName();

            usernameText.setText(displayName);
        }
    }

    private void initTrackPicker()
    {
        picker = registerForActivityResult(new ActivityResultContracts.GetContent(),
                track -> {
                    if(track != null)
                    {
                        AccessSong accessSong = new AccessSong();

                        String trackName = getTrackNameFromFile(track);

                        File dir = new File(String.valueOf(Environment.getExternalStoragePublicDirectory("Download")));
                        String path = new File(dir, trackName).getPath();

                        try{
                            MediaPlayer mediaPlayer = new MediaPlayer();
                            mediaPlayer.setDataSource(path.substring(path.indexOf("/")+1));
                            mediaPlayer.prepare();

                            mediaPlayer.start();
                        }catch (IOException e)
                        {
                            e.printStackTrace();
                        }

                        boolean inserted = accessSong.insertSong(trackName);
                        if(inserted)
                        {
                            Toast.makeText(this, "Track uploaded. Can be viewed in the Home/Browse Page", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(this, "Track didn't upload properly.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private String getTrackNameFromFile(Uri uri)
    {
        String fileName = "";

        // Gets the path of the selected music
        String path = Arrays.toString(uri.getPathSegments().toArray());

        // Retries the trackname from that path
        String trackName = path.split(",")[1].substring(0,path.split(",")[1].length() - 1).trim();
        fileName = trackName.substring(trackName.lastIndexOf("/")+1);

        return fileName;
    }

    private void clearBackStack()
    {
        // Creating new main activity intent
        Intent activity = new Intent(MainActivity.this, MainActivity.class);

        //flag for our activity to clear the history stack.
        activity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(activity);
        finish();
    }
}