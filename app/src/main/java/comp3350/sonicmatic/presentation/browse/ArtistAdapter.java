package comp3350.sonicmatic.presentation.browse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.interfaces.IArtist;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistAdapterHolder>{

    private ArrayList<IArtist> artists;

    public ArtistAdapter(ArrayList<IArtist> artists)
    {
        this.artists = artists;
    }

    @NonNull
    @Override
    public ArtistAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_artist, parent, false);
        return new ArtistAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistAdapter.ArtistAdapterHolder holder, int position) {
        IArtist artist = artists.get(position);

        holder.artistImage.setImageResource(R.drawable.artist);
        holder.artistName.setText(artist.getName());
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public class ArtistAdapterHolder extends RecyclerView.ViewHolder
    {
        private ImageView artistImage;
        private TextView artistName;

        public ArtistAdapterHolder(@NonNull View itemView) {
            super(itemView);
            artistImage = itemView.findViewById(R.id.browse_artist_list_img);
            artistName = itemView.findViewById(R.id.browse_artist_list_name);
        }
    }
}