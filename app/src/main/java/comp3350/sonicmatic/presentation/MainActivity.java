package comp3350.sonicmatic.presentation;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.Manifest;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
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

    private static final int REQUEST_BLUETOOTH_AUDIO = 124;

    private static final String[] AUDIO_BLUETOOTH_ = {
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_PRIVILEGED
    };

    private static final String[] AUDIO = {
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE
    };

    private static final String DARK_MODE = "night_mode";

    private DrawerLayout drawer;
    private ListeningHistoryMusicAdapter adapter;
    private View layout;
    private TextView usernameText;
    private IPlayer player;
    private MusicViewModel musicViewModel;
    private UserViewModel userViewModel;
    private View trackHistoryView;
    private ActivityResultLauncher<String> picker;
    private ActivityResultLauncher<Intent> bluetoothEnableLauncher;
    private ActivityResultLauncher<Intent> audioEnableLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

        SharedPreferences sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE);

        boolean isNightMode = sharedPreferences.getBoolean(DARK_MODE, false);
        if(isNightMode)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            binding.darkLightModeToggle.setChecked(true);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            binding.darkLightModeToggle.setChecked(false);
        }

        setContentView(binding.getRoot());

        // required that all instances of the music player have access to the context
        // services need application context to access assets folder
        Services.setContext(getApplicationContext());

        musicViewModel = new ViewModelProvider(this).get(MusicViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        //Hide the status bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Initialize launcher for enabling Bluetooth
        bluetoothEnableLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                // Bluetooth was enabled
                Toast.makeText(this, "Bluetooth enabled", Toast.LENGTH_SHORT).show();
            } else {
                // User didn't enable Bluetooth
                Toast.makeText(this, "Bluetooth not enabled", Toast.LENGTH_SHORT).show();
            }
        });

        audioEnableLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                // Bluetooth was enabled
                Toast.makeText(this, "Audio enabled", Toast.LENGTH_SHORT).show();
            } else {
                // User didn't enable Bluetooth
                Toast.makeText(this, "Audio not enabled", Toast.LENGTH_SHORT).show();
            }
        });

        addListeningAdapter();
        requestPermissions();
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
                    binding.navView.setVisibility(View.VISIBLE);
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

        Switch toggle_dark_light_mode = binding.darkLightModeToggle;
        toggle_dark_light_mode.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if(isChecked)
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            sharedPreferences.edit().putBoolean(DARK_MODE, isChecked).apply();
        });
    }

    private void enableBluetooth() {

        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter == null) {
            // Device does not support Bluetooth
            Toast.makeText(this, "Bluetooth is not supported on this device", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!bluetoothAdapter.isEnabled()) {
            // Bluetooth is not enabled, request to enable it
            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (enableBluetoothIntent.resolveActivity(getPackageManager()) != null) {
                bluetoothEnableLauncher.launch(enableBluetoothIntent);
            } else {
                Toast.makeText(this, "No activity found to handle Bluetooth enable request", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Bluetooth is already enabled
            Toast.makeText(this, "Bluetooth is already enabled", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestPermissions()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, AUDIO_BLUETOOTH_, REQUEST_BLUETOOTH_AUDIO);
        }else if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, AUDIO, REQUEST_BLUETOOTH_AUDIO);
        } else{
            enableBluetooth();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_BLUETOOTH_AUDIO)
        {
            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableBluetooth();
            }else{
                Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

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

            disableEnablePlaylistFeature(displayName.equals("GUEST"));

            usernameText.setText(displayName);
        }
    }

    private void initTrackPicker()
    {
        picker = registerForActivityResult(new ActivityResultContracts.GetContent(),
                track -> {
                    if(track != null)
                    {
                        String trackName = getFileName(getApplicationContext(), track);

                        AccessSong accessSong = new AccessSong();

                        boolean inserted = accessSong.insertSong(trackName, 1);
                        if(inserted)
                        {
                            Toast.makeText(this, "Track uploaded. Can be viewed in the Home/Browse Page", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(this, "Track already uploaded. View in Home/Browse", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void disableEnablePlaylistFeature(boolean bool)
    {
        BottomNavigationView layout = findViewById(R.id.nav_view);
        Menu menu = layout.getMenu();
        menu.findItem(R.id.navigation_dashboard).setVisible(bool);
    }

    private String getFileName(Context context, Uri uri)
    {
        Cursor cursor = context.getContentResolver().query(uri, null,null, null);
        int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        cursor.moveToFirst();

        return cursor.getString(index);
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