package comp3350.sonicmatic.application;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.musicPlayer.MusicPlayer;
import comp3350.sonicmatic.objects.musicArtist.MusicArtist;
import comp3350.sonicmatic.objects.musicArtist.NullMusicArtist;
import comp3350.sonicmatic.objects.musicTrack.MusicTrack;
import comp3350.sonicmatic.objects.musicTrack.NullMusicTrack;
import comp3350.sonicmatic.objects.songDuration.SongDuration;
import comp3350.sonicmatic.persistance.Persistence;
import comp3350.sonicmatic.persistance.playlist.PlaylistPersistence;
import comp3350.sonicmatic.persistance.playlistSong.PlaylistSongPersistence;
import comp3350.sonicmatic.persistance.leaderboard.LeaderboardPersistence;
import comp3350.sonicmatic.persistance.profile.ProfilePersistence;
import comp3350.sonicmatic.persistance.song.SongPersistence;

public class Services
{

    // ** class constants **
    private static final String DB_NAME = "SMDB";
    private static final String DB_PATH = "database";

    private static Context context = null;

    // ** class variables **
    private static ProfilePersistence profilePersistence = null;
    private static SongPersistence songPersistence = null;
    private static PlaylistPersistence playlistPersistence = null;
    private static PlaylistSongPersistence playlistSongPersistence = null;


    private static LeaderboardPersistence leaderboardPersistence = null;


    // ** class methods **
    public static boolean hasContext()
    {
        return context != null;
    }

    public static void setContext(Context env)
    {
        if (env != null)
        {
            context = env;

            Persistence.context = env;
            MusicPlayer.context = env;
        }
    }

    public static ISong createSongPathFromAssets(String path)
    {
        ISong create_me = null;

        path = "music/"+path;

        if (context != null)
        {
            AssetFileDescriptor afd = null;
            MediaMetadataRetriever metadata = new MediaMetadataRetriever();

            FileDescriptor fd;

            long start;
            long length;

            String title;
            String artist;
            String duration;

            try {
                afd = context.getAssets().openFd(path);
                fd = afd.getFileDescriptor();
                start = afd.getStartOffset();
                length = afd.getLength();

                metadata.setDataSource(fd, start, length);

                afd.close();

                title = metadata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                artist = metadata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                duration = metadata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

                create_me = new MusicTrack(title, new MusicArtist(artist), new SongDuration(duration), path);

            } catch (IOException ioe)
            {
                create_me = NullMusicTrack.getNullMusicTrack();
            }
        }
        else
        {
            create_me = NullMusicTrack.getNullMusicTrack();
        }
        return create_me;
    }

    public static ISong createSongFromPath(String path)
    {
        ISong created = NullMusicTrack.getNullMusicTrack();
        try
        {
            String pathToTest = "music/" + path;
            AssetFileDescriptor assetFileDescriptor = context.getAssets().openFd(pathToTest);
            assetFileDescriptor.close();

            // if in assets load from assets
            created = createSongPathFromAssets(path);
        }catch (IOException e)
        {
            // If not in assets
            created = createSongPathFromUserUpload(path);
        }
        return created;
    }

    public static ISong createSongPathFromUserUpload(String trackName)
    {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), trackName);
        Uri uri = Uri.fromFile(dir);
        int offset = 4;

        ISong create_me = NullMusicTrack.getNullMusicTrack();

        try(MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever())
        {
            mediaMetadataRetriever.setDataSource(context, uri);

            String title = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);

            if(title == null)
            {
                title = trackName.substring(0, trackName.length() - offset);
            }

            String artist = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);

            if(artist == null)
            {
                artist = NullMusicArtist.getNullMusicArtist().getName();
            }

            String duration = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            create_me = new MusicTrack(title, new MusicArtist(artist), new SongDuration(duration), trackName);

        }catch (Exception e) {
            // Handle any exceptions
            // Log or display an error message
            System.out.println(e.getMessage());
        }

        return create_me;
    }

    // ** locked access methods **
    public static synchronized ProfilePersistence getProfilePersistence()
    {
        if (context != null && profilePersistence == null)
        {
            profilePersistence = new ProfilePersistence(DB_NAME, DB_PATH);
        }

        return profilePersistence;
    }

    public static synchronized SongPersistence getSongPersistence()
    {
        if (context != null && songPersistence == null)
        {
            songPersistence = new SongPersistence(DB_NAME, DB_PATH);
        }

        return songPersistence;
    }

    public static synchronized PlaylistPersistence getPlaylistPersistence()
    {
        if(playlistPersistence == null)
        {
            playlistPersistence = new PlaylistPersistence(DB_NAME, DB_PATH);
        }
        return playlistPersistence;
    }

    public static synchronized PlaylistSongPersistence getPlaylistSongPersistence()
    {
        if(playlistSongPersistence == null)
        {
            playlistSongPersistence = new PlaylistSongPersistence(DB_NAME, DB_PATH);
        }
        return playlistSongPersistence;
    }


    public static synchronized LeaderboardPersistence getLeaderboardPersistence()
    {
        if (leaderboardPersistence == null){
            leaderboardPersistence = new LeaderboardPersistence(DB_NAME, DB_PATH);
        }

        return leaderboardPersistence;
    }
}