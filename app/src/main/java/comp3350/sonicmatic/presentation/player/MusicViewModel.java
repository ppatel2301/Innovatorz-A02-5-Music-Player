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

    public MutableLiveData<ArrayList<ISong>> getHistory()
    {
        return listeningHistory;
    }

    public void setPlayer(IPlayer player)
    {
        this.player = player;
    }

    public IPlayer getPlayer()
    {
        return player;
    }
    public void clearListeningHistory()
    {
        listeningHistory = new MutableLiveData<>();
    }
}