package comp3350.sonicmatic.objects;

import comp3350.sonicmatic.interfaces.ISongLength;

public class SongDuration implements ISongLength
{

    // ** class constants **
    public static int NO_DATA = -1;

    // ** instance variables **
    private int minutes = NO_DATA;
    private int lastSeconds = NO_DATA;

    // ** constructors **

    public SongDuration(ISongLength copy)
    {
        this.minutes = copy.getMinutes();
        this.lastSeconds = copy.getLastSeconds();
    }

    public SongDuration(String millisDurtation)
    {
        // Note that this is a String and not a long or int because the Metadata reader for our files returns Strings for all pieces of metadata.

        // take a string representation of duration in millisecs and load it into this object
        createFromMillisString(millisDurtation);
    }

    // ** accessors **
    @Override
    public int getMinutes()
    {
        return minutes;
    }

    @Override
    public int getLastSeconds()
    {
        return lastSeconds;
    }

    @Override
    public int getTotalSeconds()
    {
        return (minutes * 60) + lastSeconds;
    }

    // ** mutators **
    private void createFromMillisString(String millisDurtation)
    {
        int roundedSeconds;

        try
        {
            // for this representation, we don't neeed any decimamls with the seconds
            roundedSeconds = Math.round(((float)Integer.parseInt(millisDurtation) / 1000.0f));

            if (roundedSeconds >= 0)
            {
                // truncate to only minutes
                minutes = roundedSeconds/60;

                // get the remaining fraction of minute and convert that to real seconds
                lastSeconds = Math.round(((roundedSeconds/60.0f) - minutes) * 60);
            }


        } catch (NumberFormatException nfe)
        {
            minutes = NO_DATA;
            lastSeconds = NO_DATA;
        }
    }
}
