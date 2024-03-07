package comp3350.sonicmatic.presentation.playlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import comp3350.sonicmatic.objects.abstracts.MusicTrackPlaylist;
public class PlaylistViewModel extends  ViewModel{

    private final MutableLiveData<ArrayList<MusicTrackPlaylist>> playlists;
    private final MutableLiveData<MusicTrackPlaylist> selectedPlaylist;

    public PlaylistViewModel ()
    {
        playlists = new MutableLiveData<>(new ArrayList<>());
        selectedPlaylist = new MutableLiveData<>();
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

    public void setSelectedPlaylist(MusicTrackPlaylist playlist)
    {
        selectedPlaylist.setValue(playlist);
    }

    public MusicTrackPlaylist getSelectedPlaylist() {
        return selectedPlaylist.getValue();
    }
}