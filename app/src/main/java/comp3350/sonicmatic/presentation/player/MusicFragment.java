package comp3350.sonicmatic.presentation.player;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import comp3350.sonicmatic.R;

import comp3350.sonicmatic.musicplayer.MusicPlayer;
import comp3350.sonicmatic.databinding.FragmentMusicPlayerBinding;
import comp3350.sonicmatic.exceptions.NoMusicException;
import comp3350.sonicmatic.interfaces.IPlayer;
import comp3350.sonicmatic.interfaces.ISong;

public class MusicFragment extends Fragment {

    private IPlayer player;

    private FragmentMusicPlayerBinding binding;

    // Ui
    private TextView trackName;
    private TextView trackArtist;
    private ImageView play_pause;
    private ImageView trackImage;
    private ImageView collaspePlayer;
    private Boolean playingMusic = false;

    private MusicViewModel musicViewModel;
    private ListeningHistoryMusicAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        player = new MusicPlayer();
        musicViewModel = new ViewModelProvider(requireActivity()).get(MusicViewModel.class);
        player.loadSongFromPath(musicViewModel.getSelectedMusicTrack().getPath());
        adapter = ListeningHistoryMusicAdapter.getInstance(null);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMusicPlayerBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        // load just one song to play for now
        ISong loaded = player.getCurrentSong();

        trackName = root.findViewById(R.id.player_name);
        trackArtist = root.findViewById(R.id.player_artist);
        play_pause = root.findViewById(R.id.play_pause_button);
        trackImage = root.findViewById(R.id.player_music_image);
        collaspePlayer = root.findViewById(R.id.player_back_button);

        trackName.setText(loaded.getTitle());
        trackArtist.setText(loaded.getArtist().getName());

        trackImage.setBackgroundResource(R.drawable.default_playlist_img);

        ImageView collaspsed_play_button = root.findViewById(R.id.collasped_play_button);

        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               try
               {
                   if (player.isStopped() || player.isPaused())
                   {
                       player.start();
                       playingMusic = true;

                       musicViewModel.updatedListeningHistory(loaded);

                       observeListeningHistory();

                       play_pause.setBackgroundResource(R.drawable.baseline_pause_circle_outline_24);
                   }
                   else if (player.isPlaying())
                   {
                       player.pause();
                       playingMusic = false;
                       play_pause.setBackgroundResource(R.drawable.baseline_play_circle_outline_24);
                   }

               } catch (NoMusicException nme)
               {

               }
            }
        });

        collaspePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Trying to have the collapsed version of music player so user can play music from there and pause

//                if(playingMusic) {
//                    collaspsed_play_button.setBackgroundResource(R.drawable.baseline_pause_circle_outline_24);
//                }else{
//                    collaspsed_play_button.setBackgroundResource(R.drawable.baseline_play_circle_outline_24);
//                }
                getParentFragmentManager().popBackStack();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void observeListeningHistory()
    {
        musicViewModel.getHistory().observe(getViewLifecycleOwner(), new Observer<ArrayList<ISong>>() {
            @Override
            public void onChanged(ArrayList<ISong> iSongs) {
                adapter.updateHistory(iSongs);
                adapter.notifyDataSetChanged();
            }
        });
    }
}