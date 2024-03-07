package comp3350.sonicmatic.presentation.browse;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import comp3350.sonicmatic.R;

import comp3350.sonicmatic.business.AccessSong;
import comp3350.sonicmatic.databinding.FragmentBrowseBinding;
import comp3350.sonicmatic.interfaces.IArtist;
import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.objects.musicArtist.MusicArtist;
import comp3350.sonicmatic.objects.musicTrack.MusicTrack;
import comp3350.sonicmatic.objects.songDuration.SongDuration;
import comp3350.sonicmatic.presentation.player.MusicAdapter;

public class BrowseFragment extends Fragment {

    private FragmentBrowseBinding binding;
    private RecyclerView trackList;
    private RecyclerView artistList;

    private MusicAdapter musicAdapter;
    private ArtistAdapter artistAdapter;

    private ArrayList<ISong> tracks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBrowseBinding.inflate(inflater,container, false);
        View root = binding.getRoot();

        trackList = root.findViewById(R.id.browse_tracks);
        artistList = root.findViewById(R.id.browse_artists);

        updateTrackList(trackList);
        updateAristList(artistList);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private ArrayList<ISong> getSongs()
    {
        AccessSong accessSong = new AccessSong();
        tracks = accessSong.getAllSongs();
        return tracks;
    }

    private void updateTrackList(RecyclerView list)
    {
        if(musicAdapter == null)
        {
            ArrayList<ISong> tracks = getSongs();
            musicAdapter = new MusicAdapter(tracks);
        }

        list.setAdapter(musicAdapter);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.scrollToPosition(0);
    }

    private ArrayList<IArtist> getArtists()
    {
        ArrayList<IArtist> artists = new ArrayList<>();

        for(ISong songs: tracks)
        {
            artists.add(songs.getArtist());
        }

        return artists;
    }

    private void updateAristList(RecyclerView list)
    {
        if(artistAdapter == null)
        {
            ArrayList<IArtist> artists = getArtists();
            artistAdapter = new ArtistAdapter(artists);
        }
        list.setAdapter(artistAdapter);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.scrollToPosition(0);
    }
}