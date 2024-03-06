package comp3350.sonicmatic.objects.musicartist;

import comp3350.sonicmatic.interfaces.IArtist;

public class MusicArtist implements IArtist
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
