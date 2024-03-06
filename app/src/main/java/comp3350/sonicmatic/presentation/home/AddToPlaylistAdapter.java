package comp3350.sonicmatic.presentation.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.objects.MusicTrackPlaylist;

public class AddToPlaylistAdapter extends RecyclerView.Adapter<AddToPlaylistAdapter.AddToPlaylistHolder> {

    private ArrayList<MusicTrackPlaylist> playlists;
    private ArrayList<MusicTrackPlaylist> playlistsSelected;

    public AddToPlaylistAdapter(ArrayList<MusicTrackPlaylist> playlists){
        this.playlists = playlists;
        playlistsSelected = new ArrayList<>();
    }

    @NonNull
    @Override
    public AddToPlaylistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the itemAddToPlaylist
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_music_to_playlist, parent, false);
        return new AddToPlaylistHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AddToPlaylistHolder holder, int position) {
        // Add Listener to the checkbox
        // and keep track of the selected playlists

        MusicTrackPlaylist playlist = playlists.get(position);

        //Setting the playlist name
        holder.title.setText(playlist.getPlaylistName());
        holder.image.setBackgroundResource(R.drawable.default_playlist_img);
        holder.user.setText("Sample User");

        // Checkbox to add music to playlists
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                {
                    playlistsSelected.add(playlist);
                    // Update the playlist songs in the playlistViewModel and the database
                    // database update but don't know how???
                }else{
                    playlistsSelected.remove(playlist);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    public ArrayList<MusicTrackPlaylist> getPlaylistsSelected()
    {
        return playlistsSelected;
    }

    public class AddToPlaylistHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView title;
        private TextView user;
        private CheckBox checkBox;

        public AddToPlaylistHolder(@NonNull View view)
        {
            super(view);

            image = itemView.findViewById(R.id.add_image);
            title = itemView.findViewById(R.id.add_title);
            user = itemView.findViewById(R.id.add_user);
            checkBox = itemView.findViewById(R.id.add_check_box);
        }
    }
}