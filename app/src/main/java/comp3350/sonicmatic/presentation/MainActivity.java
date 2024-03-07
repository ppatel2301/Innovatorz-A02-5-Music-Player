package comp3350.sonicmatic.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.application.Services;
import comp3350.sonicmatic.business.AccessProfile;
import comp3350.sonicmatic.databinding.ActivityMainBinding;
import comp3350.sonicmatic.exceptions.NoMusicException;
import comp3350.sonicmatic.interfaces.IPlayer;
import comp3350.sonicmatic.persistance.profile.Profile;
import comp3350.sonicmatic.presentation.player.ListeningHistoryMusicAdapter;
import comp3350.sonicmatic.presentation.player.MusicViewModel;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private ListeningHistoryMusicAdapter adapter;
    private TextView usernameText;
    private View layout;
    private IPlayer player;
    private MusicViewModel musicViewModel;
    private View trackHistoryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // required that all instances of the music player have access to the context
        // services need application context to access assets folder
        Services.setContext(getApplicationContext());

        musicViewModel = new ViewModelProvider(this).get(MusicViewModel.class);

        //Hide the status bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        addListeningAdapter();

        drawer = binding.drawerProfileLayout;
        usernameText =  drawer.findViewById(R.id.profile_username);
        trackHistoryView = findViewById(R.id.track_history_layout);

        updateGetUsername();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_profile, R.id.navigation_browse)
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
                    drawer.openDrawer(GravityCompat.END);
                    layout.setVisibility(View.GONE);
                }else{
                    drawer.closeDrawer(GravityCompat.END);
                }
                return true;
            }else{
                if(drawer.isDrawerOpen(GravityCompat.END))
                {
                    drawer.closeDrawer(GravityCompat.END);
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
                layout.setVisibility(View.VISIBLE);
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
                            play_pause_collasped.setImageResource(R.drawable.baseline_pause_circle_outline_24);
                        } catch (NoMusicException e) {
                            throw new RuntimeException(e);
                        }
                    }else{
                        try {
                            player.pause();
                            play_pause_collasped.setImageResource(R.drawable.baseline_play_circle_outline_24);
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
        AccessProfile accessProfile = new AccessProfile();
        String displayName = accessProfile.getDisplayName();

        usernameText.setText(displayName);
    }
}