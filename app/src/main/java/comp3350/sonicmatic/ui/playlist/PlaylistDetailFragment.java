package comp3350.sonicmatic.ui.playlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.databinding.FragmentPlaylistDetailBinding;
import comp3350.sonicmatic.interfaces.IArtist;
import comp3350.sonicmatic.interfaces.ISongLength;
import comp3350.sonicmatic.objects.musicartist.MusicArtist;
import comp3350.sonicmatic.objects.musictrack.MusicTrack;
import comp3350.sonicmatic.objects.songduration.SongDuration;
import comp3350.sonicmatic.ui.player.MusicAdapter;

public class PlaylistDetailFragment extends Fragment {
    private FragmentPlaylistDetailBinding binding;
    private ImageView playlistImage;
    private ImageView backbutton;
    private TextView playlistName;

    private RecyclerView trackList;

    private ISongLength songLength;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPlaylistDetailBinding.inflate(inflater, container,false);
        View root = binding.getRoot();

        playlistImage = root.findViewById(R.id.list_detail_img);
        backbutton = root.findViewById(R.id.detail_back_button);
        trackList  = root.findViewById(R.id.list_detail_view);
        playlistName = root.findViewById(R.id.list_detail_name);

        backbutton.setImageResource(R.drawable.baseline_arrow_back_24);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });

        playlistImage.setImageResource(R.drawable.default_playlist_img);

        String listName = getArguments().getString("playlistName");
        playlistName.setText(listName);

        // Using an adapter to upload a list of music tracks
        addMuicToList(trackList);

        return root;
    }

    private void addMuicToList(RecyclerView list)
    {
        // Creating a list to display muisic to list
        IArtist artist = new MusicArtist("Bob");

        ArrayList<MusicTrack> tracks = new ArrayList<>();
        songLength = new SongDuration("34");
        tracks.add(new MusicTrack("Name", artist, songLength, "Path"));
        tracks.add(new MusicTrack("Name1", artist, songLength, "Path"));
        tracks.add(new MusicTrack("Name2", artist, songLength, "Path"));

        // Updating the ui of the playlist
        MusicAdapter musicAdapter = new MusicAdapter(tracks);
        list.setAdapter(musicAdapter);
        trackList.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
