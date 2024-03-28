package comp3350.sonicmatic.presentation.leaderboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.objects.musicArtist.LeaderboardArtist;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder> {

    private ArrayList<LeaderboardArtist> artistsList;

    public LeaderboardAdapter(ArrayList<LeaderboardArtist> artistLst){
        this.artistsList = artistLst;
    }

    public void setArtistsList(ArrayList<LeaderboardArtist> artistsList)
    {
        this.artistsList = artistsList;
    }

    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_items, parent, false);
        return new LeaderboardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {
        LeaderboardArtist artist = artistsList.get(position);

        holder.nameText.setText(artist.getName());
        holder.metric.setText(String.valueOf(artist.getMetric()));
        holder.rank.setText(String.valueOf(position+1));
    }

    @Override
    public int getItemCount() {
        return this.artistsList.size();
    }

    public class LeaderboardViewHolder extends RecyclerView.ViewHolder{
        private TextView nameText;
        private TextView rank;
        private ImageView image;
        private TextView metric;

        public LeaderboardViewHolder(final View view){
            super(view);
            nameText = view.findViewById(R.id.leader_board_artist_name);
            rank = view.findViewById(R.id.leader_board_artist_rank);
            image = view.findViewById(R.id.leader_board_img);
            metric = view.findViewById(R.id.leaderBoard_metric);
        }
    }
}
