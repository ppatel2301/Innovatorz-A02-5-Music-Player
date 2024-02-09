package comp3350.sonicmatic.ui.library;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import comp3350.sonicmatic.objects.Playlist;
public class PlaylistViewModel extends  ViewModel{

    private final MutableLiveData<ArrayList<Playlist>> playlists;

    public PlaylistViewModel ()
    {
        playlists = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<ArrayList<Playlist>> getPlaylist(){return playlists;}

    public void addPlaylist(Playlist playlist)
    {
        ArrayList<Playlist> currentList = playlists.getValue();
        if(currentList == null)
        {
            currentList = new ArrayList<>();
        }
        currentList.add(playlist);
        playlists.setValue(currentList);
    }
}
