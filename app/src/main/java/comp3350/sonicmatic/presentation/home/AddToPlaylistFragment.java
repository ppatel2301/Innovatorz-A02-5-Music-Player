package comp3350.sonicmatic.presentation.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.business.access.AccessPlaylist;
import comp3350.sonicmatic.databinding.FragmentAddToPlaylistBinding;
import comp3350.sonicmatic.interfaces.IPlaylist;
import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.presentation.login.UserViewModel;
import comp3350.sonicmatic.presentation.player.MusicViewModel;
import comp3350.sonicmatic.presentation.playlist.PlaylistViewModel;

public class AddToPlaylistFragment extends Fragment {

    private FragmentAddToPlaylistBinding binding;
    private PlaylistViewModel playlistViewModel;
    private UserViewModel userViewModel;

    private ArrayList<IPlaylist> playlists;
    private Button addToPlayist;
    private RecyclerView recyclerView;
    private ISong selectedMusicTrack;
    private MusicViewModel model;

    public AddToPlaylistFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playlistViewModel = new ViewModelProvider(requireActivity()).get(PlaylistViewModel.class);
        model = new ViewModelProvider(requireActivity()).get(MusicViewModel.class);
        playlists = playlistViewModel.getPlaylist().getValue();
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddToPlaylistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.add_list);
        addToPlayist = root.findViewById(R.id.add_done);

        AddToPlaylistAdapter adapter = new AddToPlaylistAdapter(playlists);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        addToPlayist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Add update the playlist chosen by the user with the correct song.
                ArrayList<IPlaylist> lists = adapter.getPlaylistsSelected();
                // Update the user data with access of logic layer and then data layer

                // Getting the music to add to the playlist/s selected by the user
                selectedMusicTrack = model.getSelectedMusicTrack();

                AccessPlaylist accessPlaylist = new AccessPlaylist();

                // Update the playlist data into the playlistViewModel for the ui based
                for(IPlaylist selectPlaylist: lists)
                {
                    boolean success = accessPlaylist.insertIntoPlaylist(selectPlaylist.getPlaylistName(), selectedMusicTrack, userViewModel.getProfile());

                    if(success)
                    {
                        Toast.makeText(getContext(), selectedMusicTrack.getTitle() + " added to " + selectPlaylist.getPlaylistName(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), selectedMusicTrack.getTitle() + " already added to " + selectPlaylist.getPlaylistName(), Toast.LENGTH_SHORT).show();
                    }
                }

                // Update the list
                playlistViewModel.updateList(accessPlaylist.getPlaylists(userViewModel.getProfile()));

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
}