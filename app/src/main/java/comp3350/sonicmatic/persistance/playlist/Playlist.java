package comp3350.sonicmatic.persistance.playlist;

import java.util.ArrayList;

import comp3350.sonicmatic.interfaces.IPersistentItem;
import comp3350.sonicmatic.interfaces.ISong;

public class Playlist implements IPersistentItem
{
    // ** instance variables **
    private int id;
    private String creatorUsername;
    private String name;

    // ** constructors **
    public Playlist(int id, String creatorUsername, String name)
    {
        this.id = id;
        this.creatorUsername = creatorUsername;
        this.name = name;
    }

    // ** accessors **
    @Override
    public String getPrimaryKey()
    {
        return ""+id;
    }

    public String getCreatorUsername()
    {
        return creatorUsername;
    }

    public String getName() {
        return name;
    }

}

