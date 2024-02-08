package comp3350.sonicmatic.ui.player;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import comp3350.sonicmatic.R;

import comp3350.sonicmatic.databinding.FragmentMusicPlayerBinding;
public class MusicFragment extends Fragment {

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

        String title = getArguments().getString("MusicTitle");
        String artist = getArguments().getString("musicArtist");

        trackName = root.findViewById(R.id.player_name);
        trackArtist = root.findViewById(R.id.player_artist);
        play_pause = root.findViewById(R.id.play_pause_button);
        trackImage = root.findViewById(R.id.player_music_image);

        trackName.setText(title);
        trackArtist.setText(artist);
        trackImage.setBackgroundResource(R.drawable.baseline_library_music_24);

        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(getContext(), "Playing Music", duration).show();
            }
        });


        return root;
    }
}
