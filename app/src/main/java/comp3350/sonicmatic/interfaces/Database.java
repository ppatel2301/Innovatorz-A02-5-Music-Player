package comp3350.sonicmatic.interfaces;

import comp3350.sonicmatic.objects.musictrack.MusicTrack;

public interface Database {
    public void uploadSong(MusicTrack song);

    public MusicTrack getSong(String name) throws Exception;
}
