package comp3350.sonicmatic.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import comp3350.sonicmatic.databinding.FragmentHomeBinding;
import comp3350.sonicmatic.ui.library.PlaylistAdapter;
import comp3350.sonicmatic.ui.library.PlaylistViewModel;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private MusicAdapter musicAdapter; // will use this later to veiw ui on phone

    private PlaylistAdapter playlistAdapter;
    private PlaylistViewModel playlistViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        playlistViewModel = new ViewModelProvider(requireActivity()).get(PlaylistViewModel.class);

        if(playlistAdapter == null) {
            playlistAdapter = new PlaylistAdapter(new ArrayList<>());
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}