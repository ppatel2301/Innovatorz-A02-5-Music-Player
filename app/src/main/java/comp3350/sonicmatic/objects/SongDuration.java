package comp3350.sonicmatic.objects;

import comp3350.sonicmatic.interfaces.ISongLength;

public class SongDuration implements ISongLength
{

    // ** class constants **
    private static int NO_DATA = -1;

    // ** instance variables **
    private int minutes;
    private int lastSeconds;

    // ** constructors **

    public SongDuration(ISongLength copy)
    {
        this.minutes = copy.getMinutes();
        this.lastSeconds = copy.getLastSeconds();
    }

    public SongDuration(String millisDurtation)
    {
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

            // truncate to only minutes
            minutes = roundedSeconds/60;

            // get the remaining fraction of minute and convert that to real seconds
            lastSeconds = Math.round(((roundedSeconds/60.0f) - minutes) * 60);

        } catch (NumberFormatException nfe)
        {
            minutes = NO_DATA;
            lastSeconds = NO_DATA;
        }
    }
}
