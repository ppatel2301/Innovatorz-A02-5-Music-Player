package comp3350.sonicmatic.presentation.playlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.interfaces.IPlaylist;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>{

    private ArrayList<IPlaylist> playlists;
    private PlaylistViewModel playlistViewModel;

    public PlaylistAdapter(ArrayList<IPlaylist> playlists) {
        this.playlists = playlists;
    }

    public void setPlaylists(ArrayList<IPlaylist> playlists)
    {
        this.playlists = playlists;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflates the item layout for the user
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist, parent, false);

        ViewModelProvider viewModelProvider = new ViewModelProvider((ViewModelStoreOwner) parent.getContext());
        playlistViewModel = viewModelProvider.get(PlaylistViewModel.class);

        return new PlaylistViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position){

        IPlaylist playlist = playlists.get(position);

        holder.playlistImage.setImageResource(R.drawable.default_playlist_img);
        holder.title.setText(playlist.getPlaylistName());
        holder.user.setText("Playlist - User");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get the playlist object
                IPlaylist playlist = playlists.get(holder.getAdapterPosition());

                // Creating a bundle to pass data to the playlistDetail fragment
                Bundle bundle = new Bundle();
                bundle.putString("playlistName", playlist.getPlaylistName());

                // Setting the selected playlist
                playlistViewModel.setSelectedPlaylist(playlist);

                Navigation.findNavController(view).navigate(R.id.playlistDetailFragment, bundle);
            }
        });
    }

    public int getItemCount()
    {
        return playlists.size();
    }

    public class PlaylistViewHolder extends RecyclerView.ViewHolder {

        private ImageView playlistImage;
        private TextView title;
        private TextView user;

        public PlaylistViewHolder (@NonNull View view)
        {
            super(view);

            playlistImage = itemView.findViewById(R.id.playlist_img);
            title = itemView.findViewById(R.id.add_title);
            user = itemView.findViewById(R.id.add_user);
        }
    }
}