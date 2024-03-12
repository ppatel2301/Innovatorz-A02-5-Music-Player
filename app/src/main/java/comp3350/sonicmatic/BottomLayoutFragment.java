package comp3350.sonicmatic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import comp3350.sonicmatic.databinding.TestBottomSheetLayoutBinding;
import comp3350.sonicmatic.interfaces.IPlaylist;
import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.presentation.playlist.PlaylistViewModel;

public class BottomLayoutFragment extends BottomSheetDialogFragment {

    private int peekHeight = -1; // Default peekHeight
    private TestBottomSheetLayoutBinding binding;
    private PlaylistViewModel playlistViewModel;
    private IPlaylist list;
    private ArrayList<IPlaylist> playlists;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playlistViewModel = new ViewModelProvider(requireActivity()).get(PlaylistViewModel.class);
        list = playlistViewModel.getSelectedPlaylist();
        playlists = playlistViewModel.getPlaylist().getValue();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = TestBottomSheetLayoutBinding.inflate(inflater, container,false);
        View root = binding.getRoot();

        Button by_title = root.findViewById(R.id.sort_by_title);
        by_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list != null)
                {
                    ArrayList<ISong> updated = list.filterByTitle();
                    updatePlaylist(updated);
                }
            }
        });

        Button by_artist = root.findViewById(R.id.sort_by_artist);
        by_artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list != null)
                {
                    ArrayList<ISong> updated = list.filterByArtist();
                    updatePlaylist(updated);
                }
            }
        });

        Button by_added_Seq = root.findViewById(R.id.sort_by_recently_added);
        by_added_Seq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePlaylist(playlistViewModel.getDefaultSelectedPlaylist().getPlaylist());
            }
        });
        return root;
    }

    private void updatePlaylist(ArrayList<ISong> updatedPlaylist)
    {
        if(playlists != null)
        {
            IPlaylist playlist = playlists.get(playlists.indexOf(list));

            playlist.updatePlaylist(updatedPlaylist);

            playlistViewModel.updateList(playlists);
        }
    }

    public void setPeekHeight(int peekHeight) {
        this.peekHeight = peekHeight;
        // Apply the peekHeight immediately if the fragment is already created
        if (getView() != null && getView().getParent() != null) {
            applyPeekHeight();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Apply the peekHeight if it's set
        if (peekHeight != -1) {
            applyPeekHeight();
        }
    }

    private void applyPeekHeight() {
        View view = getView();
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent instanceof View) {
                View bottomSheet = (View) parent;
                ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
                if (layoutParams instanceof CoordinatorLayout.LayoutParams) {
                    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) layoutParams;
                    CoordinatorLayout.Behavior behavior = params.getBehavior();
                    if (behavior instanceof BottomSheetBehavior) {
                        BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) behavior;
                        bottomSheetBehavior.setPeekHeight(peekHeight);
                    }
                }
            }
        }
    }

    private void observePlaylist(PlaylistViewModel viewModel)
    {
        viewModel.getPlaylist().observe(getViewLifecycleOwner(), new Observer<ArrayList<IPlaylist>>() {
            @Override
            public void onChanged(ArrayList<IPlaylist> updatedPlaylist) {
                playlists = updatedPlaylist;
            }
        });
    }
}
