package comp3350.sonicmatic.ui.player;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.interfaces.Player;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder>
{
    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView songName;
        SeekBar seek;
        Button playPause;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }
    }


    // ** instance variables **
    private Context context;
    private Player player;

    // ** accessors **

    @Override
    public int getItemCount()
    {
        return 1;
    }

    // ** mutators **

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_music_player, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {

    }

}
