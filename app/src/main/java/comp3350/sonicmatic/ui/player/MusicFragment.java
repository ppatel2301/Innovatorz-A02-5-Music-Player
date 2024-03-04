package comp3350.sonicmatic.ui.player;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import comp3350.sonicmatic.R;

import comp3350.sonicmatic.musicplayer.MusicPlayer;
import comp3350.sonicmatic.databinding.FragmentMusicPlayerBinding;
import comp3350.sonicmatic.exceptions.NoMusicException;
import comp3350.sonicmatic.interfaces.IPlayer;
import comp3350.sonicmatic.interfaces.ISong;

public class MusicFragment extends Fragment {

    private IPlayer player = new MusicPlayer();

    private FragmentMusicPlayerBinding binding;

    // Ui
    private TextView trackName;
    private TextView trackArtist;
    private Button play_pause;
    private ImageView trackImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMusicPlayerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // load just one song to play for now
        player.loadSongFromPath("music/Archetype.mp3");
        ISong loaded = player.getCurrentSong();

        trackName = root.findViewById(R.id.player_name);
        trackArtist = root.findViewById(R.id.player_artist);
        play_pause = root.findViewById(R.id.play_pause_button);
        trackImage = root.findViewById(R.id.player_music_image);


        trackName.setText(loaded.getTitle());
        trackArtist.setText(loaded.getArtist().getName());


        trackImage.setBackgroundResource(R.drawable.baseline_library_music_24);

        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(getContext(), "Playing Music", duration).show();
                */

               try
               {

                   if (player.isStopped() || player.isPaused())
                   {
                       player.start();
                       play_pause.setText("Pause");
                   }
                   else if (player.isPlaying())
                   {
                       player.pause();
                       play_pause.setText("Resume");
                   }


               } catch (NoMusicException nme)
               {

               }

            }
        });


        return root;
    }
}
