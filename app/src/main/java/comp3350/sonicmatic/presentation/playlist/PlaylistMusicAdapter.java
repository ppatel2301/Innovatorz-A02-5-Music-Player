package comp3350.sonicmatic.presentation.playlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.objects.MusicTrack;
import comp3350.sonicmatic.objects.Playlist;

public class PlaylistMusicAdapter extends RecyclerView.Adapter<PlaylistMusicAdapter.PlaylistMusicViewHolder> {

    private ArrayList<ISong> tracks;
    private PlaylistViewModel playlistViewModel;

    public PlaylistMusicAdapter(ArrayList<ISong> tracks)
    {
        this.tracks = tracks;
    }

    @NonNull
    @Override
    public PlaylistMusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the music layout for the user
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist_music, parent, false);

        ViewModelProvider viewModelProvider = new ViewModelProvider((ViewModelStoreOwner) parent.getContext());
        playlistViewModel = viewModelProvider.get(PlaylistViewModel.class);

        return new PlaylistMusicViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistMusicViewHolder holder, int position) {
        ISong musicTrack = tracks.get(position);

        holder.trackImg.setBackgroundResource(R.drawable.baseline_library_music_24);
        holder.trackName.setText(musicTrack.getTitle());
        holder.trackArtist.setText(musicTrack.getArtist().getName());

        holder.removeTrackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Update this to have remove song from the current playlist and update the database
                // to have the updated database
                Playlist currentList = playlistViewModel.getSelectedPlaylist();
                ArrayList<Playlist> playlists = playlistViewModel.getPlaylist().getValue();

                if(playlists != null)
                {
                    for(Playlist list: playlists)
                    {
                        if(list.getPlaylistName().equalsIgnoreCase(currentList.getPlaylistName()))
                        {
                            list.removeMusicTracks(musicTrack);
                        }
                    }
                }
                notifyItemRemoved(holder.getAdapterPosition());
                Toast.makeText(view.getContext(), "Remove Song",Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open music player for the clicked music by user
                Navigation.findNavController(view).navigate(R.id.musicFragment, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public class PlaylistMusicViewHolder extends RecyclerView.ViewHolder {

        private ImageView trackImg;
        private TextView trackName;
        private TextView trackArtist;
        private ImageView removeTrackImg;

        public PlaylistMusicViewHolder(@NonNull View view)
        {
            super(view);

            trackImg = itemView.findViewById(R.id.playlist_music_img);
            trackName = itemView.findViewById(R.id.playlist_music_song_name);
            trackArtist = itemView.findViewById(R.id.playlist_music_artist);
            removeTrackImg = itemView.findViewById(R.id.playlist_music_remove);
        }
    }
}
