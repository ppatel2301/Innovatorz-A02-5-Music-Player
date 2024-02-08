package comp3350.sonicmatic.ui.playlist;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.databinding.FragmentPlaylistDetailBinding;
import comp3350.sonicmatic.objects.Playlist;

public class PlaylistDetailFragment extends Fragment {
    private PlaylistViewModel playlistViewModel;
    private Playlist playlist;

    private FragmentPlaylistDetailBinding binding;

    private ImageView playlistImage;
    private ImageView backbutton;
    private TextView playlistName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playlistViewModel = new ViewModelProvider(requireActivity()).get(PlaylistViewModel.class);
        playlist = playlistViewModel.getSelectedPlaylist().getValue();
        System.out.println(playlist);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPlaylistDetailBinding.inflate(inflater, container,false);
        View root = binding.getRoot();

        playlistImage = root.findViewById(R.id.list_detail_img);
        backbutton = root.findViewById(R.id.detail_back_button);

        backbutton.setImageResource(R.drawable.baseline_arrow_back_24);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });

        playlistImage.setImageResource(R.drawable.default_playlist_img);
//        // Setting the playlist text
//        playlistName.setText(playlist.getPlaylistName());

        return root;
    }
}
