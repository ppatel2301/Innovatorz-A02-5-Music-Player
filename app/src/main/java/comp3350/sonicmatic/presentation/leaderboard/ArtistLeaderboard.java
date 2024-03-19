package comp3350.sonicmatic.presentation.leaderboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.application.Services;
import comp3350.sonicmatic.business.AccessLeaderboard;
import comp3350.sonicmatic.databinding.FragmentArtistLeaderboardBinding;
import comp3350.sonicmatic.objects.musicArtist.LeaderboardArtist;
import comp3350.sonicmatic.persistance.leaderboard.Leaderboard;

public class ArtistLeaderboard extends Fragment {

    private RecyclerView recyclerView;

    private LeaderboardAdapter adapter;

    private FragmentArtistLeaderboardBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Services.setContext(getContext());
    }

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

        AccessLeaderboard accessLeaderboard = new AccessLeaderboard();
        Leaderboard leaderboard = accessLeaderboard.getLeaderboard();

        ArrayList<LeaderboardArtist> artists = leaderboard.getLeaderboard();
        System.out.println(artists);

        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}