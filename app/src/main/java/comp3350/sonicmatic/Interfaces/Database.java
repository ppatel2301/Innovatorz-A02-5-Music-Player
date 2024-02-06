package comp3350.sonicmatic.Interfaces;

import comp3350.sonicmatic.objects.MusicTrack;

public interface Database {
    public void uploadSong(MusicTrack song);

    public MusicTrack getSong(String name) throws Exception;
}
