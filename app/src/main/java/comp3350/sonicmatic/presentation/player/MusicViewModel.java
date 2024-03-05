package comp3350.sonicmatic.presentation.player;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.objects.MusicTrack;

public class MusicViewModel extends ViewModel {

    private ISong musicTrack;
    private MutableLiveData<ArrayList<ISong>> listeningHistory;

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
        }
        currentList.add(track);
        listeningHistory.setValue(currentList);
    }

    public ArrayList<ISong> getListeningHistory()
    {
        return listeningHistory.getValue();
    }

    public void clearListeningHistory()
    {
        listeningHistory = new MutableLiveData<>();
    }
}