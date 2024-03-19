package comp3350.sonicmatic.presentation.player;

import static comp3350.sonicmatic.musicPlayer.MusicPlayer.context;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
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

import com.google.android.material.snackbar.Snackbar;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.presentation.login.UserViewModel;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder>{

    private ArrayList<ISong> tracks;
    private MusicViewModel musicViewModel;
    private UserViewModel userViewModel;

    public MusicAdapter(ArrayList<ISong> tracks) {
        this.tracks = tracks;
    }

    public void setFilteredList(ArrayList<ISong> tracks)
    {
        this.tracks = tracks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_song, parent,false);
        musicViewModel = new ViewModelProvider((FragmentActivity) view.getContext()).get(MusicViewModel.class);
        userViewModel = new ViewModelProvider((FragmentActivity) view.getContext()).get(UserViewModel.class);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.MusicViewHolder holder, int position) {
        if(tracks != null)
        {
            ISong track = tracks.get(position);

//            AssetFileDescriptor afd = null;
//            try {
//                MediaMetadataRetriever metadata = new MediaMetadataRetriever();
//                afd = context.getAssets().openFd(track.getPath());
//                FileDescriptor fd = afd.getFileDescriptor();
//                metadata.setDataSource(fd, afd.getStartOffset(), afd.getLength());
//
//                byte[] bitmap = metadata.getEmbeddedPicture();
//
//                if(bitmap != null)
//                {
//                    Bitmap bitmap1 = BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);
//                    holder.musicImage.setImageBitmap(bitmap1);
//                }else{
//                    holder.musicImage.setImageResource(R.drawable.music_img);
//                }
//
//            } catch (IOException e) {
//            }

            holder.musicImage.setImageResource(R.drawable.music_img);
            holder.title.setText(track.getTitle());
            holder.artist.setText(track.getArtist().getName());

            holder.addToPlayist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!userViewModel.getProfile().getDisplayName().equals("Guest"))
                    {
                        // Adding the selected music to the musicViewModel
                        musicViewModel.setSelectedTrack(track);

                        Navigation.findNavController(view).navigate(R.id.addToPlaylistFragment, null);

                        Toast.makeText(view.getContext(), "Add To Playlist",Toast.LENGTH_SHORT).show();
                    }else{
                        Snackbar.make(view, "Guests cannot create playlists. Login to access the playlists", Snackbar.LENGTH_LONG).show();
                    }
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    musicViewModel.setSelectedTrack(track);
                    Navigation.findNavController(view).navigate(R.id.musicFragment, null);
                }
            });
        }
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