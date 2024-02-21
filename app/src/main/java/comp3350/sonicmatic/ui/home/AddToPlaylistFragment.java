package comp3350.sonicmatic.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.databinding.FragmentAddToPlaylistBinding;
import comp3350.sonicmatic.objects.Playlist;
import comp3350.sonicmatic.ui.playlist.PlaylistViewModel;

public class AddToPlaylistFragment extends Fragment {

    private PlaylistViewModel playlistViewModel;
    private ArrayList<Playlist> playlists;
    private Button addToPlayist;
    private RecyclerView recyclerView;

    public AddToPlaylistFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playlistViewModel = new ViewModelProvider(requireActivity()).get(PlaylistViewModel.class);
        playlists = playlistViewModel.getPlaylist().getValue();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentAddToPlaylistBinding binding = FragmentAddToPlaylistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.add_list);
        addToPlayist = root.findViewById(R.id.add_done);

        AddToPlaylistAdapter adapter = new AddToPlaylistAdapter(playlists);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        addToPlayist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getParentFragmentManager().popBackStack();
                // Add update the playlist chosen by the user with the correct song.
            }
        });
        return root;
    }
}