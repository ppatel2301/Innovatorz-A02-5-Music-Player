package comp3350.sonicmatic.presentation.player;

import androidx.lifecycle.ViewModel;

import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.objects.MusicTrack;

public class MusicViewModel extends ViewModel {

    private MusicTrack musicTrack;

    public void setSelectedTrack(MusicTrack musicTrack)
    {
        this.musicTrack = musicTrack;
    }

    public MusicTrack getSelectedMusicTrack()
    {
        return musicTrack;
    }
}