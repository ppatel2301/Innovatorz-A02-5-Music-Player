package comp3350.sonicmatic.presentation.browse;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.os.FileUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import comp3350.sonicmatic.R;

import comp3350.sonicmatic.business.AccessSong;
import comp3350.sonicmatic.databinding.FragmentBrowseBinding;
import comp3350.sonicmatic.interfaces.IArtist;
import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.presentation.player.MusicAdapter;

public class BrowseFragment extends Fragment {

    private FragmentBrowseBinding binding;
    private RecyclerView trackList;
    private RecyclerView artistList;
    private SearchView seachview;
    private MusicAdapter musicAdapter;
    private ArtistAdapter artistAdapter;

    private ArrayList<ISong> tracks;
    private ArrayAdapter<String> trackNames;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBrowseBinding.inflate(inflater,container, false);
        View root = binding.getRoot();

        trackList = root.findViewById(R.id.browse_tracks);
        artistList = root.findViewById(R.id.browse_artists);
        seachview = root.findViewById(R.id.searchView);
        seachview.clearFocus();

        updateTrackList(trackList);
        updateAristList(artistList);

        seachview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                seachview.clearFocus();
                updateSearchView(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                trackList.smoothScrollToPosition(0);
                updateSearchView(newText);
                return true;
            }
        });


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
            if(!artists.contains(songs.getArtist()))
            {
                artists.add(songs.getArtist());
            }
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
        list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL ,false));
        list.scrollToPosition(0);
    }

    private void updateSearchView(String text)
    {
        ArrayList<ISong> filtered = new ArrayList<>();

        for(ISong song: tracks)
        {
            if(song.getTitle().toLowerCase().contains(text.toLowerCase()))
            {
                filtered.add(song);
            }
        }

        if(filtered.isEmpty())
        {
            musicAdapter.setFilteredList(getSongs());
        }else{
           musicAdapter.setFilteredList(filtered);
        }
    }
}