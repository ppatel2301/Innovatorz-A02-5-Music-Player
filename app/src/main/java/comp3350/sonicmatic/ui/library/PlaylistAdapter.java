package comp3350.sonicmatic.ui.library;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.objects.MusicTrackPlaylist;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>{

    private ArrayList<MusicTrackPlaylist> playlists;

    public PlaylistAdapter(ArrayList<MusicTrackPlaylist> playlists) {
        this.playlists = playlists;
    }

    public void setPlaylists(ArrayList<MusicTrackPlaylist> playlists)
    {
        this.playlists = playlists;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflates the item layout for the user
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist, parent, false);

        return new PlaylistViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position){

        MusicTrackPlaylist playlist = playlists.get(position);

        holder.playlistImage.setImageResource(R.drawable.default_playlist_img);
        holder.title.setText(playlist.getPlaylistName());
        holder.user.setText("Playlist - User");
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
            title = itemView.findViewById(R.id.playlist_title);
            user = itemView.findViewById(R.id.textView3);
        }
    }
}