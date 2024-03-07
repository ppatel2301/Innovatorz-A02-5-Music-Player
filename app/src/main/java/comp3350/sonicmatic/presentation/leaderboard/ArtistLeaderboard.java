package comp3350.sonicmatic.presentation.leaderboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.databinding.FragmentArtistLeaderboardBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArtistLeaderboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArtistLeaderboard extends Fragment {

    private RecyclerView recyclerView;

    private LeaderboardAdapter adapter;

    private FragmentArtistLeaderboardBinding binding;

    public ArtistLeaderboard() {
        // Required empty public constructor
    }

    /*

     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentArtistLeaderboardBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        if (adapter == null){
            adapter = new LeaderboardAdapter(new ArrayList<>());
        }

        // Get components
        recyclerView = root.findViewById(R.id.leaderboardRecyclerView);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((getContext())));

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artist_leaderboard, container, false);
    }
}