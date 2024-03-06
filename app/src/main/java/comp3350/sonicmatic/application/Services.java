package comp3350.sonicmatic.application;

import android.content.Context;

import comp3350.sonicmatic.musicplayer.MusicPlayer;
import comp3350.sonicmatic.persistance.Persistence;
import comp3350.sonicmatic.persistance.profile.ProfilePersistence;
import comp3350.sonicmatic.persistance.song.SongPersistence;

public class Services
{

    // ** class constants **
    private static final String DB_NAME = "SMDB";
    private static final String DB_PATH = "database";

    private static Context context;

    // ** class variables **
    private static ProfilePersistence profilePersistence = null;
    private static SongPersistence songPersistence = null;


    // ** class methods **
    public static void setContext(Context env)
    {
        if (env != null)
        {
            context = env;

            Persistence.context = env;
            MusicPlayer.context = env;
        }
    }

    // ** locked access methods **
    public static synchronized ProfilePersistence getProfilePersistence()
    {
        if (profilePersistence == null)
        {
            profilePersistence = new ProfilePersistence(DB_NAME, DB_PATH);
        }

        return profilePersistence;
    }

    public static synchronized SongPersistence getSongPersistence()
    {
        if (songPersistence == null)
        {
            songPersistence = new SongPersistence(DB_NAME, DB_PATH);
        }

        return songPersistence;
    }


}
