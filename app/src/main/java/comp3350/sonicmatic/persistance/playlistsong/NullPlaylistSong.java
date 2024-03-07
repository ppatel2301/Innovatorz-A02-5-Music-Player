package comp3350.sonicmatic.persistance.playlistsong;

public class NullPlaylistSong extends PlaylistSong
{
    private static String NULL_PLAYLIST_SONG = "NULL PLAYLIST SONG";
    private static int NULL_ID = -1;
    private static PlaylistSong nullPlaylistSong = null;

    public static PlaylistSong getNullPlaylistSong()
    {
        if (nullPlaylistSong == null)
        {
            nullPlaylistSong = new NullPlaylistSong();
        }

        return nullPlaylistSong;
    }

    private NullPlaylistSong()
    {
        super(NULL_PLAYLIST_SONG, NULL_ID);
    }
}
