package comp3350.sonicmatic.presentation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.System.musicplayer.MusicPlayer;
import comp3350.sonicmatic.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Hide the status bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        drawer = binding.drawerProfileLayout;
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_profile, R.id.navigation_browse)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // required that all instances of the music player have access to the context
        MusicPlayer.context = getApplicationContext();

        binding.navView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.navigation_profile)
            {
                if(!drawer.isDrawerOpen(GravityCompat.END))
                {
                    drawer.openDrawer(GravityCompat.END);
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
    }
}