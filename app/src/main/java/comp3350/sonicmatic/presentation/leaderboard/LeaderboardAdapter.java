package comp3350.sonicmatic.presentation.leaderboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.interfaces.IArtist;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder> {

    private ArrayList<IArtist> artistsList;

    public LeaderboardAdapter(ArrayList<IArtist> artistLst){
        this.artistsList = artistLst;
    }

    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_items, parent, false);
        return new LeaderboardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {
        String name = this.artistsList.get(position).getName();
        holder.nameText.setText(name);
    }

    @Override
    public int getItemCount() {
        return this.artistsList.size();
    }

    public class LeaderboardViewHolder extends RecyclerView.ViewHolder{
        private TextView nameText;

        public LeaderboardViewHolder(final View view){
            super(view);
            nameText = view.findViewById(R.id.ArtistName);
        }
    }
}
