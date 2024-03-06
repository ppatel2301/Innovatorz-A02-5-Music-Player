package comp3350.sonicmatic.business;

import comp3350.sonicmatic.application.Services;
import comp3350.sonicmatic.persistance.song.SongPersistence;

public class AccessSong
{

    // ** instance variables **
    private SongPersistence songPersistence;

    // ** constructors **
    public AccessSong()
    {
        songPersistence = Services.getSongPersistence();
    }

    public AccessSong(final SongPersistence songPersistence)
    {
        this.songPersistence = songPersistence;
    }

}
