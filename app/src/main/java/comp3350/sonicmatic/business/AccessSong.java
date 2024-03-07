package comp3350.sonicmatic.business;

import java.lang.reflect.Array;
import java.util.ArrayList;

import comp3350.sonicmatic.application.Services;
import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.musicplayer.MusicPlayer;
import comp3350.sonicmatic.objects.MusicTrackPlaylist;
import comp3350.sonicmatic.objects.musictrack.MusicTrack;
import comp3350.sonicmatic.objects.musictrack.NullMusicTrack;
import comp3350.sonicmatic.persistance.Persistence;
import comp3350.sonicmatic.persistance.song.Song;
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

    public ArrayList<ISong> getAllSongs()
    {
        // getAllSongs returns song rows
        // convert these to ISong instances

        ArrayList<ISong> music_tracks = new ArrayList<ISong>();
        ArrayList<Song> song_rows = songPersistence.getAll();

        for (Song s : song_rows)
        {
            music_tracks.add(Services.createSongFromPath(s.getFileNameExt()));
        }

        return music_tracks;
    }

    public ISong getSong(String path)
    {
        ISong get_me = NullMusicTrack.getNullMusicTrack();
        Song from_db;

        if (Persistence.isStringOkay(path)) {
            from_db = songPersistence.get(path);

            // if this file is in our database, then we can be sure there is an audio file to match it
            if (from_db.getFileNameExt().equals(path))
            {
                get_me = Services.createSongFromPath(path);
            }
        }

        return get_me;
    }

    public boolean insertSong(String path)
    {
        return songPersistence.insert(new Song(path));
    }
    public boolean deleteSong(String path)
    {
        return songPersistence.delete(new Song(path));
    }

}
