package comp3350.sonicmatic.presentation.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.databinding.FragmentLibraryBinding;
import comp3350.sonicmatic.objects.Playlist;
import comp3350.sonicmatic.presentation.playlist.PlaylistAdapter;
import comp3350.sonicmatic.presentation.playlist.PlaylistViewModel;

public class LibraryFragment extends Fragment{

    // Declaring the views
    private RecyclerView recyclerView;
    private Button buttonView; // Add button in the library fragments
    private PlaylistAdapter adapter; // Adapter for the recycler view
    private FragmentLibraryBinding binding;
    private PlaylistViewModel playlistViewModel;

    public LibraryFragment(){};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLibraryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        playlistViewModel = new ViewModelProvider(requireActivity()).get(PlaylistViewModel.class);

        if(adapter == null) {
            adapter = new PlaylistAdapter(new ArrayList<>());
        }

        // Getting the ui components by Id
        recyclerView = root.findViewById(R.id.libRecyclerView); // List
        Button addButton = root.findViewById(R.id.add_playlist); // Add Button

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((getContext())));

        observePlaylist();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openCreatePlaylist();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void observePlaylist()
    {
        playlistViewModel.getPlaylist().observe(getViewLifecycleOwner(), new Observer<ArrayList<Playlist>>() {
            @Override
            public void onChanged(ArrayList<Playlist> playlists) {
                adapter.setPlaylists(playlists);
                adapter.notifyDataSetChanged();

                recyclerView.scrollToPosition(0); // scroll to the first item added on list
            }
        });
    }

    private void openCreatePlaylist()
    {
        Navigation.findNavController(requireView()).navigate(R.id.navigation_create_playlist,null);
    }
}