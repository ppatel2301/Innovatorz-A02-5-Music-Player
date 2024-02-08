package comp3350.sonicmatic.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import comp3350.sonicmatic.R;

public class PlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_music_player);
    }
}