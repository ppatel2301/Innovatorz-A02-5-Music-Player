package comp3350.sonicmatic.ui.library;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import comp3350.sonicmatic.objects.MusicTrackPlaylist;
public class PlaylistViewModel extends  ViewModel{

    private final MutableLiveData<ArrayList<MusicTrackPlaylist>> playlists;

    public PlaylistViewModel ()
    {
        playlists = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<ArrayList<MusicTrackPlaylist>> getPlaylist(){return playlists;}

    public void addPlaylist(MusicTrackPlaylist playlist)
    {
        ArrayList<MusicTrackPlaylist> currentList = playlists.getValue();
        if(currentList == null)
        {
            currentList = new ArrayList<>();
        }
        currentList.add(playlist);
        playlists.setValue(currentList);
    }
}
