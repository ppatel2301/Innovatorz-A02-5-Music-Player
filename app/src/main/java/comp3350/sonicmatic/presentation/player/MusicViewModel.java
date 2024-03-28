package comp3350.sonicmatic.presentation.player;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import comp3350.sonicmatic.interfaces.IPlayer;
import comp3350.sonicmatic.interfaces.ISong;

public class MusicViewModel extends ViewModel {

    private ISong musicTrack;
    private MutableLiveData<ArrayList<ISong>> listeningHistory;
    private IPlayer player;

    // used to keep track of music playing
    private int currentSongIndex = 0;
    private ArrayList<ISong> songs;

    public MusicViewModel()
    {
        listeningHistory = new MutableLiveData<>(new ArrayList<>());
    }

    public void setSelectedTrack(ISong musicTrack) {
        this.musicTrack = musicTrack;
    }

    public ISong getSelectedMusicTrack() {
        return musicTrack;
    }

    public void updatedListeningHistory(ISong track) {
        ArrayList<ISong> currentList = listeningHistory.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }else{
            if(!currentList.contains(track))
            {
                currentList.add(track);
            }
        }
        listeningHistory.setValue(currentList);
    }

    public void setPlayer(IPlayer player)
    {
        this.player = player;
    }

    public void setSongs(ArrayList<ISong> songs)
    {
        this.songs = songs;
    }

    public void setCurrentSongIndex(int currentSongIndex) {
        this.currentSongIndex = currentSongIndex;
    }

    public IPlayer getPlayer()
    {
        return player;
    }

    public MutableLiveData<ArrayList<ISong>> getHistory()
    {
        return listeningHistory;
    }

    public ArrayList<ISong> getSongs()
    {
        return songs;
    }

    public int getCurrentSongIndex() {
        return currentSongIndex;
    }

    public void clearListeningHistory()
    {
        listeningHistory = new MutableLiveData<>();
    }
}