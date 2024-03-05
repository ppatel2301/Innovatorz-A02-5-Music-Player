package comp3350.sonicmatic.presentation.player;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.databinding.FragmentCollapsedMusicPlayerBinding;

public class CollaspedMusicFragment extends Fragment {

    // update the the recycler view with the listening history
    private FragmentCollapsedMusicPlayerBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCollapsedMusicPlayerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ConstraintLayout layout = root.findViewById(R.id.collasped_music_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Yes");
//                Navigation.findNavController(view).navigate(R.id.musicFragment, null);
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
