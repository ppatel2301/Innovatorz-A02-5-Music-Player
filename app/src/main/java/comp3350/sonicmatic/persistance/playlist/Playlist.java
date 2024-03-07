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
    private ArrayList<ISong> songs;

    // ** constructors **
    public Playlist(int id, String creatorUsername, String name, ArrayList<ISong> songs)
    {
        this.id = id;
        this.creatorUsername = creatorUsername;
        this.name = name;
        this.songs = songs;
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

    public ArrayList<ISong> getSongs() {
        return songs;
    }
}

