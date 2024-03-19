package comp3350.sonicmatic.presentation.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.application.Services;
import comp3350.sonicmatic.business.AccessSong;
import comp3350.sonicmatic.databinding.FragmentHomeBinding;
import comp3350.sonicmatic.interfaces.IPlaylist;
import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.presentation.MainActivity;
import comp3350.sonicmatic.presentation.player.MusicAdapter;
import comp3350.sonicmatic.presentation.playlist.PlaylistAdapter;
import comp3350.sonicmatic.presentation.playlist.PlaylistViewModel;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    // Playlist instance variables
    private RecyclerView playlistView;
    private PlaylistAdapter playlistAdapter;
    private PlaylistViewModel playlistViewModel;

    // Music instance variables
    private MusicAdapter musicAdapter; // will use this later to view ui on phone
    private RecyclerView songListView;
    private ImageView addToPlaylist;
    private ArrayList<ISong> tracks;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Services.setContext(getContext());
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        playlistViewModel = new ViewModelProvider(requireActivity()).get(PlaylistViewModel.class);

        if (playlistAdapter == null) {
            playlistAdapter = new PlaylistAdapter(new ArrayList<>());
        }

        // Setting the adapter for the playlist recycler view
        playlistView = root.findViewById(R.id.list_recyler_view);
        playlistView.setAdapter(playlistAdapter);
        playlistView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        observePlaylist();

        // Setting the adapter for the song recyler view
        songListView = root.findViewById(R.id.song_recycler_view);

        addMuicToList(songListView);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void addMuicToList(RecyclerView list)
    {
        AccessSong accessSong = new AccessSong();
        tracks = accessSong.getAllSongs();

        // Updating the ui of the playlist
        MusicAdapter musicAdapter = new MusicAdapter(tracks);
        list.setAdapter(musicAdapter);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void observePlaylist()
    {
        playlistViewModel.getPlaylist().observe(getViewLifecycleOwner(), new Observer<ArrayList<IPlaylist>>() {
            @Override
            public void onChanged(ArrayList<IPlaylist> playlists) {
                playlistAdapter.setPlaylists(playlists);
                playlistAdapter.notifyDataSetChanged();

                playlistView.scrollToPosition(0); // scroll to the first item added on list
            }
        });
    }
}