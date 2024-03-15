package comp3350.sonicmatic.presentation.playlist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import comp3350.sonicmatic.R;

import comp3350.sonicmatic.databinding.FragmentCreatePlaylistBinding;
import comp3350.sonicmatic.objects.MusicTrackPlaylist;

public class CreatePlaylistFragment extends BottomSheetDialogFragment {

    private FragmentCreatePlaylistBinding binding;
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

        binding = FragmentCreatePlaylistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Logic to create a playlist for the user
        EditText playlistName = root.findViewById(R.id.playlistName);
        Button createPlaylist = root.findViewById(R.id.create_playlist);
        ImageView cancelPlaylist = root.findViewById(R.id.cancel_playlist);

        createPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validitePlaylist(playlistName.getText().toString()))
                {
                    // Gets the playlist name from the text input by the user
                    String listName = playlistName.getText().toString();

                    // Creating a playlist in the ui with the given name
                    playlistViewModel.addPlaylist(new MusicTrackPlaylist(listName, new ArrayList<>()));

                    String user = "";
                    addPlaylistToUser(user);

                    dismiss();

                    getParentFragmentManager().popBackStack();
                }else{
                    showDialog("Not a valid Playlist name!! Maybe: Playlist name to big or contains a space");
                }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showDialog(String text)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Sonicmatic Warning")
                .setMessage(text)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private boolean validitePlaylist(String username)
    {
        return (!username.isEmpty() && !username.equals(" ") && username.length() < 20);
    }

    private void addPlaylistToUser(String user)
    {

    }
}