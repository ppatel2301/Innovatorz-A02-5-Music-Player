package comp3350.sonicmatic.objects;

import comp3350.sonicmatic.interfaces.Artist;

public class MusicArtist implements Artist
{

    // ** instance variables **
    private String name;

    // ** constructors **
    public MusicArtist(String name)
    {
        this.name = name;
    }

    // ** accessors **

    @Override
    public String getName() {
        return name;
    }

}
