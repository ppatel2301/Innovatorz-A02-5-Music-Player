package comp3350.sonicmatic.presentation.player;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import comp3350.sonicmatic.R;

import comp3350.sonicmatic.musicPlayer.MusicPlayer;
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
    private MusicViewModel musicViewModel;
    private ListeningHistoryMusicAdapter adapter;
    private SeekBar seekBar;
    private ImageView restart;
    private ImageView next;

    private View collaspedMusicPlayer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        musicViewModel = new ViewModelProvider(requireActivity()).get(MusicViewModel.class);

        // Calling load song to load the correct song
        try {
            loadSong();
        } catch (NoMusicException e) {
            Toast.makeText(getContext(), "Music didn't load properly", Toast.LENGTH_SHORT).show();
        }

        adapter = ListeningHistoryMusicAdapter.getInstance(null);
        collaspedMusicPlayer = requireActivity().findViewById(R.id.collasped_music_layout1);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMusicPlayerBinding.inflate(inflater, container, false);

        collaspedMusicPlayer.setVisibility(View.GONE);

        View root = binding.getRoot();

        // load just one song to play for now
        ISong loaded = player.getCurrentSong();

        trackName = root.findViewById(R.id.player_name);
        trackArtist = root.findViewById(R.id.player_artist);
        play_pause = root.findViewById(R.id.play_pause_button);
        trackImage = root.findViewById(R.id.player_music_image);
        collaspePlayer = root.findViewById(R.id.player_back_button);
        restart = root.findViewById(R.id.player_prev);
        next = root.findViewById(R.id.player_next);

        next.setVisibility(View.GONE);

        seekBar = root.findViewById(R.id.seekBar);
        seekBar.setMax(player.getMillisecDuration());

        trackName.setText(loaded.getTitle());
        trackArtist.setText(loaded.getArtist().getName());

        trackImage.setBackgroundResource(R.drawable.default_playlist_img);

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    player.seek(0);
                    seekBar.setProgress(0);
                } catch (NoMusicException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(player != null)
                {
                    if(fromUser)
                    {
                        // by the user
                        try {
                            player.seek(progress);
                        } catch (NoMusicException e) {

                        }
                    }
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               try
               {
                   if (player.isStopped() || player.isPaused())
                   {
                       player.start();
                       musicViewModel.updatedListeningHistory(loaded);

                       observeListeningHistory();

                       play_pause.setImageResource(R.drawable.baseline_pause_circle_outline_24);
                   }
                   else if (player.isPlaying())
                   {
                       player.pause();
                       play_pause.setImageResource(R.drawable.baseline_play_circle_outline_24);
                   }

               } catch (NoMusicException nme)
               {
                   Toast.makeText(getContext(), "Sorry, we're having trouble playing your music right now :(", Toast.LENGTH_SHORT).show();
               }
            }
        });

        collaspePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collaspedMusicPlayer.setVisibility(View.VISIBLE);

                View layout = root.getRootView().findViewById(R.id.collasped_music_layout1);
                TextView trackName = layout.findViewById(R.id.collasped_music_name);
                TextView artist = layout.findViewById(R.id.collasped_music_artist);
                ImageView play_pause = layout.findViewById(R.id.collasped_play_button);

                if(player.isPaused())
                {
                    play_pause.setImageResource(R.drawable.baseline_play_circle_outline_white_24);
                }else{
                    play_pause.setImageResource(R.drawable.baseline_pause_circle_outline_white_24);
                }

                musicViewModel.setPlayer(player);

                trackName.setText(player.getCurrentSong().getTitle());
                artist.setText(player.getCurrentSong().getArtist().getName());

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

    private void loadSong() throws NoMusicException {
        if(musicViewModel.getPlayer() == null)
        {
            player = new MusicPlayer();
            player.loadSongFromPath(musicViewModel.getSelectedMusicTrack().getPath());
        }else{
            this.player = musicViewModel.getPlayer();
            ISong trackToPlay = musicViewModel.getSelectedMusicTrack();
            ISong trackPlaying = player.getCurrentSong();

            if(!trackToPlay.getPath().equals(trackPlaying.getPath())) {
                player.stop();
                player.loadSongFromPath(trackToPlay.getPath());
            }
        }
    }
}