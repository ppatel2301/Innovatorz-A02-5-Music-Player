package comp3350.sonicmatic.presentation.player;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.interfaces.ISong;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder>{

    private ArrayList<ISong> tracks;
    private MusicViewModel musicViewModel;

    public MusicAdapter(ArrayList<ISong> tracks) {
        this.tracks = tracks;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_song, parent,false);
        musicViewModel = new ViewModelProvider((FragmentActivity) view.getContext()).get(MusicViewModel.class);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.MusicViewHolder holder, int position) {
//        if(tracks != null)
//        {
            ISong track = tracks.get(position);

            holder.musicImage.setImageResource(R.drawable.baseline_library_music_24);
            holder.title.setText(track.getTitle());
            holder.artist.setText(track.getArtist().getName());

            holder.addToPlayist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Adding the selected music to the musicViewModel
                    musicViewModel.setSelectedTrack(track);

                    Navigation.findNavController(view).navigate(R.id.addToPlaylistFragment, null);

                    Toast.makeText(view.getContext(), "Add To Playlist",Toast.LENGTH_SHORT).show();
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    musicViewModel.setSelectedTrack(track);
                    Navigation.findNavController(view).navigate(R.id.musicFragment, null);
                }
            });
//        }
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView musicImage;
        private TextView title;
        private TextView artist;
        private ImageView addToPlayist;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);

            musicImage = itemView.findViewById(R.id.song_image);
            title = itemView.findViewById(R.id.song_title);
            artist = itemView.findViewById(R.id.song_artist);
            addToPlayist = itemView.findViewById(R.id.add_to_playlist);
        }
    }
}