package comp3350.sonicmatic.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.objects.Playlist;

public class AddToPlaylistAdapter extends RecyclerView.Adapter<AddToPlaylistAdapter.AddToPlaylistHolder> {

    private ArrayList<Playlist> playlists;

    public AddToPlaylistAdapter(ArrayList<Playlist> playlists){this.playlists = playlists;}

    @NonNull
    @Override
    public AddToPlaylistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflates the itemAddToPlaylist
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_music_to_playlist, null);
        return new AddToPlaylistHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AddToPlaylistHolder holder, int position) {
        // Add Listener to the checkbox
        // and keep track of the selected playlists
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    public class AddToPlaylistHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView title;
        private TextView artist;
        private CheckBox checkBox;

        public AddToPlaylistHolder(@NonNull View view)
        {
            super(view);

            image = itemView.findViewById(R.id.add_image);
            title = itemView.findViewById(R.id.add_title);
            artist = itemView.findViewById(R.id.add_user);
            checkBox = itemView.findViewById(R.id.add_check_box);
        }
    }
}