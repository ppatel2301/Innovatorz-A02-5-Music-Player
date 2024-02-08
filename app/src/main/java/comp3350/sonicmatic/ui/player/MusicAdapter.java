package comp3350.sonicmatic.ui.player;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.objects.MusicTrack;
import comp3350.sonicmatic.objects.Playlist;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder>{

    ArrayList<MusicTrack> tracks;

    public MusicAdapter(ArrayList<MusicTrack> tracks) {this.tracks = tracks;}

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_song, parent,false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.MusicViewHolder holder, int position) {
        MusicTrack track = tracks.get(position);

        holder.musicImage.setImageResource(R.drawable.baseline_library_music_24);
        holder.title.setText(track.getTitle());
        holder.artist.setText(track.getArtist().getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get the playlist object
                MusicTrack track = tracks.get(holder.getAdapterPosition());

                // Creating a bundle to pass data to the playlistDetail framgment
                Bundle bundle = new Bundle();
                bundle.putString("musicTitle", track.getTitle());
                bundle.putString("musicArtist", track.getArtist().getName());

                Navigation.findNavController(view).navigate(R.id.musicFragment, bundle);
            }
        });

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

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);

            musicImage = itemView.findViewById(R.id.song_image);
            title = itemView.findViewById(R.id.song_title);
            artist = itemView.findViewById(R.id.song_artist);
        }
    }
}