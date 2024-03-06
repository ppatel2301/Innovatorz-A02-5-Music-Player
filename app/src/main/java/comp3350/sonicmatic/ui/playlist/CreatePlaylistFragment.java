package comp3350.sonicmatic.ui.playlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import comp3350.sonicmatic.R;

import comp3350.sonicmatic.databinding.FragmentCreatePlaylistBinding;
import comp3350.sonicmatic.objects.MusicTrackPlaylist;

public class CreatePlaylistFragment extends BottomSheetDialogFragment {

    private PlaylistViewModel playlistViewModel;

    public CreatePlaylistFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playlistViewModel = new ViewModelProvider(requireActivity()).get(PlaylistViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FragmentCreatePlaylistBinding binding = FragmentCreatePlaylistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Logic to create a playlist for the user
        EditText playlistName = root.findViewById(R.id.playlistName);
        Button createPlaylist = root.findViewById(R.id.create_playlist);
        Button cancelPlaylist = root.findViewById(R.id.cancel_playlist);

        createPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Gets the playlist name from the text input by the user
                String listName = playlistName.getText().toString();

                // Creating a playlist in the ui with the given name
                playlistViewModel.addPlaylist(new MusicTrackPlaylist(listName, new ArrayList<>()));

                dismiss();

                getParentFragmentManager().popBackStack();
            }
        });

        cancelPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // dismiss the bottom fragment
                dismiss();

                getParentFragmentManager().popBackStack();
            }
        });

        return root;
    }
}