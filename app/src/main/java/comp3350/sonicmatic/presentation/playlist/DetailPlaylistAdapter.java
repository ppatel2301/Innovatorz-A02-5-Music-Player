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
import comp3350.sonicmatic.presentation.login.UserViewModel;

public class DetailPlaylistAdapter extends  RecyclerView.Adapter<DetailPlaylistAdapter.DetailPlaylistViewHolder>{

    private ArrayList<IPlaylist> playlists;
    private PlaylistViewModel playlistViewModel;
    private UserViewModel userViewModel;

    public DetailPlaylistAdapter(ArrayList<IPlaylist> playlists)
    {
        this.playlists = playlists;
    }

    public void setPlaylists(ArrayList<IPlaylist> playlists)
    {
        this.playlists = playlists;
    }

    @NonNull
    @Override
    public DetailPlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the item layout for the user
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist_detail, parent, false);

        ViewModelProvider viewModelProvider = new ViewModelProvider((ViewModelStoreOwner) parent.getContext());
        playlistViewModel = viewModelProvider.get(PlaylistViewModel.class);
        userViewModel = viewModelProvider.get(UserViewModel.class);

        return new DetailPlaylistViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailPlaylistViewHolder holder, int position) {
        IPlaylist playlist = playlists.get(position);

        holder.image.setImageResource(R.drawable.default_playlist_img);
        holder.playlistName.setText(playlist.getPlaylistName());


        String user = "Playlist - " + userViewModel.getProfile().getDisplayName();
        holder.user.setText(user);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get the playlist object
                IPlaylist playlist = playlists.get(holder.getAdapterPosition());

                // Creating a bundle to pass data to the playlistDetail fragment
                Bundle bundle = new Bundle();
                bundle.putString("playlistName", playlist.getPlaylistName());
                bundle.putInt("playlistId", holder.getAdapterPosition());

                // Setting the selected playlist
                playlistViewModel.setSelectedPlaylist(playlist);

                View layout = view.getRootView().findViewById(R.id.collasped_music_layout1);
                layout.setVisibility(View.GONE);

                Navigation.findNavController(view).navigate(R.id.playlistDetailFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    public class DetailPlaylistViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView playlistName;
        private TextView user;

        public DetailPlaylistViewHolder (@NonNull View view)
        {
            super(view);
            image = view.findViewById(R.id.playlist_image);
            playlistName = view.findViewById(R.id.playlist_name);
            user = view.findViewById(R.id.add_user);
        }
    }
}

