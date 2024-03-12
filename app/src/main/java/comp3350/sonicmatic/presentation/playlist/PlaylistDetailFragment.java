package comp3350.sonicmatic.presentation.playlist;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.sonicmatic.BottomLayoutFragment;
import comp3350.sonicmatic.R;
import comp3350.sonicmatic.databinding.FragmentPlaylistDetailBinding;
import comp3350.sonicmatic.interfaces.IPlaylist;
import comp3350.sonicmatic.interfaces.ISong;

public class PlaylistDetailFragment extends Fragment {
    private FragmentPlaylistDetailBinding binding;
    private ImageView playlistImage;
    private ImageView backbutton;
    private TextView playlistName;
    private RecyclerView trackList;
    private ArrayList<IPlaylist> playlists;
    private PlaylistViewModel playlistViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playlistViewModel = new ViewModelProvider(requireActivity()).get(PlaylistViewModel.class);
        playlists = playlistViewModel.getPlaylist().getValue();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPlaylistDetailBinding.inflate(inflater, container,false);
        View root = binding.getRoot();

        playlistImage = root.findViewById(R.id.list_detail_img);
        backbutton = root.findViewById(R.id.detail_back_button);
        trackList = root.findViewById(R.id.list_detail_view);
        playlistName = root.findViewById(R.id.list_detail_name);

        backbutton.setImageResource(R.drawable.baseline_arrow_back_24);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View layout = requireActivity().findViewById(R.id.collasped_music_layout1);
                layout.setVisibility(View.VISIBLE);
                getParentFragmentManager().popBackStack();
            }
        });

        playlistImage.setImageResource(R.drawable.default_playlist_img);

        String listName = getArguments().getString("playlistName");
        playlistName.setText(listName);

        // Using an adapter to upload a list of music tracks which are in the current list chosen
        // by the user
        observePlaylist(playlistViewModel);

        Button button = root.findViewById(R.id.more_playlist_info);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomLayoutFragment bottomLayoutFragment = new BottomLayoutFragment();
                bottomLayoutFragment.setPeekHeight(1000);
                bottomLayoutFragment.show(getParentFragmentManager(), bottomLayoutFragment.getTag());
            }
        });

        addMusicToList(listName, trackList);

        return root;
    }

    private void addMusicToList(String playlistName,RecyclerView list)
    {
        if(playlists != null)
        {
            for(IPlaylist currentList : playlists)
            {
                if(currentList.getPlaylistName().equalsIgnoreCase(playlistName))
                {
                    ArrayList<ISong> tracks = currentList.getPlaylist();
                    if(tracks != null)
                    {
                        PlaylistMusicAdapter musicAdapter = new PlaylistMusicAdapter(tracks);
                        list.setAdapter(musicAdapter);
                        list.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                }
            }
        }
    }

    private void observePlaylist(PlaylistViewModel viewModel)
    {
        viewModel.getPlaylist().observe(getViewLifecycleOwner(), new Observer<ArrayList<IPlaylist>>() {
            @Override
            public void onChanged(ArrayList<IPlaylist> updatedPlaylist) {
                playlists = updatedPlaylist;

                addMusicToList(playlistName.getText().toString(), trackList);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
