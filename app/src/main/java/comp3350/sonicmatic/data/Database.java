package comp3350.sonicmatic.data;

import comp3350.sonicmatic.objects.MusicTrack;

public interface Database {
    public void uploadSong(MusicTrack song);

    public MusicTrack getSong(int songID) throws Exception;
}
