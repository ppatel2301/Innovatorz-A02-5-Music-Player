package comp3350.sonicmatic.presentation.player;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.interfaces.ISong;

public class ListeningHistoryMusicAdapter extends RecyclerView.Adapter<ListeningHistoryMusicAdapter.ListeningViewHolder> {

    private static ListeningHistoryMusicAdapter instance;

    private ArrayList<ISong> history;

    public ListeningHistoryMusicAdapter(ArrayList<ISong> history)
    {
        this.history = history;
    }

    public static ListeningHistoryMusicAdapter getInstance(ArrayList<ISong> items)
    {
        if(instance == null)
        {
            instance = new ListeningHistoryMusicAdapter(items);
        }
        return instance;
    }


    public void updateHistory(ArrayList<ISong> history)
    {
        this.history = history;
    }

    @NonNull
    @Override
    public ListeningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music_history,parent, false);
        return new ListeningViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListeningViewHolder holder, int position) {
        ISong track = history.get(position);

        holder.historyTrackImage.setImageResource(R.drawable.baseline_library_music_24);
        holder.historyTrackName.setText(track.getTitle());
    }

    @Override
    public int getItemCount() {
        return history.size();
    }

    public class ListeningViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView historyTrackImage;
        private TextView historyTrackName;

        public ListeningViewHolder(@NonNull View itemView)
        {
            super(itemView);

            historyTrackImage = itemView.findViewById(R.id.history_track_img);
            historyTrackName = itemView.findViewById(R.id.history_track_name);
        }
    }
}