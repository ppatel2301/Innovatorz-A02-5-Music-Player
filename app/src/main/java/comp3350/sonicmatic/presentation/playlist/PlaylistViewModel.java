package comp3350.sonicmatic.presentation.playlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import comp3350.sonicmatic.interfaces.IPlaylist;
public class PlaylistViewModel extends  ViewModel{

    private final MutableLiveData<ArrayList<IPlaylist>> playlists;
    private final MutableLiveData<IPlaylist> selectedPlaylist;

    public PlaylistViewModel ()
    {
        playlists = new MutableLiveData<>(new ArrayList<>());
        selectedPlaylist = new MutableLiveData<>();
    }

    public LiveData<ArrayList<IPlaylist>> getPlaylist(){return playlists;}

    public void addPlaylist(IPlaylist playlist)
    {
        ArrayList<IPlaylist> currentList = playlists.getValue();
        if(currentList == null)
        {
            currentList = new ArrayList<>();
        }
        currentList.add(playlist);
        playlists.setValue(currentList);
    }

    public void setSelectedPlaylist(IPlaylist playlist)
    {
        selectedPlaylist.setValue(playlist);
    }

    public IPlaylist getSelectedPlaylist() {
        return selectedPlaylist.getValue();
    }
}