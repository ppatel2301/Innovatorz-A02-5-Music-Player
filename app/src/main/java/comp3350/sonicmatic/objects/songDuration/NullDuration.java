package comp3350.sonicmatic.objects.songDuration;

public class NullDuration extends SongDuration
{
    private static final String NULL_DUR = "0";

    private static NullDuration nullDuration = null;

    public static NullDuration getNullDuration()
    {
        if (nullDuration == null)
        {
            nullDuration = new NullDuration();
        }

        return nullDuration;
    }

    private NullDuration()
    {
        super(NULL_DUR);
    }
}
