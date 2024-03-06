package comp3350.sonicmatic.persistance.playlist;

import java.util.ArrayList;

import comp3350.sonicmatic.interfaces.IPersistentItem;

public class Playlist implements IPersistentItem
{
    // ** instance variables **
    private int id;
    private String creatorUsername;
    private String name;

    private ArrayList songs;

    // ** constructors **


    // ** accessors **
    @Override
    public String getPrimaryKey()
    {
        return ""+id;
    }


}
