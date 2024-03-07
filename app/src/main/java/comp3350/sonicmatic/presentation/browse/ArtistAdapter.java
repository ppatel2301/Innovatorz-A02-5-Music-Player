package comp3350.sonicmatic.presentation.browse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open the drawer layout with artist header layout
                View historyView = view.getRootView().findViewById(R.id.track_history_layout);
                DrawerLayout layout = view.getRootView().findViewById(R.id.drawer_profile_layout);
                View musicPlayer = view.getRootView().findViewById(R.id.collasped_music_layout1);

                NavigationView drawerNavView = view.getRootView().findViewById(R.id.drawer_nav_view);

                TextView profileName = drawerNavView.getHeaderView(0).findViewById(R.id.profile_username);
                profileName.setText(artist.getName());

                musicPlayer.setVisibility(View.GONE);
                historyView.setVisibility(View.GONE);
                layout.openDrawer(GravityCompat.END);
            }
        });
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