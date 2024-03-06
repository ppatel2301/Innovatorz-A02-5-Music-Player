package comp3350.sonicmatic.presentation.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.databinding.FragmentHomeBinding;
import comp3350.sonicmatic.interfaces.IArtist;
import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.objects.MusicArtist;
import comp3350.sonicmatic.objects.MusicTrack;
import comp3350.sonicmatic.objects.Playlist;
import comp3350.sonicmatic.objects.SongDuration;
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
    private ArrayList<MusicTrack> tracks;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Model to observe any changes to playlist
        playlistViewModel = new ViewModelProvider(requireActivity()).get(PlaylistViewModel.class);

        if (playlistAdapter == null) {
            playlistAdapter = new PlaylistAdapter(new ArrayList<>());
        }

        // Setting the adapter for the playlist recycler view
        playlistView = root.findViewById(R.id.list_recyler_view);
        playlistView.setAdapter(playlistAdapter);
        playlistView.setLayoutManager(new LinearLayoutManager((getContext())));

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
        // Creating a list to display muisic to list
        IArtist artist = new MusicArtist("Bob");

        ArrayList<ISong> tracks = new ArrayList<>();
        SongDuration songLength = new SongDuration("34");
        tracks.add(new MusicTrack("Name", artist, songLength, "music/Archetype.mp3"));
        tracks.add(new MusicTrack("Name1", artist, songLength, "music/Cyberwaste.mp3"));
        tracks.add(new MusicTrack("Name2", artist, songLength, "music/Archetype.mp3"));

        // Updating the ui of the playlist
        MusicAdapter musicAdapter = new MusicAdapter(tracks);
        list.setAdapter(musicAdapter);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void observePlaylist()
    {
        playlistViewModel.getPlaylist().observe(getViewLifecycleOwner(), new Observer<ArrayList<Playlist>>() {
            @Override
            public void onChanged(ArrayList<Playlist> playlists) {
                playlistAdapter.setPlaylists(playlists);
                playlistAdapter.notifyDataSetChanged();

                playlistView.scrollToPosition(0); // scroll to the first item added on list
            }
        });
    }
}