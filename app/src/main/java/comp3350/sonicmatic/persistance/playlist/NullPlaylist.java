package comp3350.sonicmatic.persistance.playlist;

public class NullPlaylist extends Playlist
{
    private static final String NULL_CREATOR = "NULL CREATOR";
    private static final String NULL_NAME = "NULL NAME";
    private static final int NULL_ID = -1;
    private static NullPlaylist nullPlaylist = null;

    public static NullPlaylist getNullPlaylist()
    {
        if (nullPlaylist == null)
        {
            nullPlaylist = new NullPlaylist();
        }

        return nullPlaylist;
    }

    private NullPlaylist()
    {
        super(NULL_ID, NULL_CREATOR, NULL_NAME);
    }
}
