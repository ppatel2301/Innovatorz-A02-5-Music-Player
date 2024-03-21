package comp3350.sonicmatic.persistance.song;

import comp3350.sonicmatic.interfaces.IPersistentItem;

public class Song implements IPersistentItem
{

    // ** instance variables **
    private String fileNameExt; // store the file_name.extension in the database
    private int origin; // 0 for pre-loaded music and 1 for user uploaded music

    // ** constructors **
    public Song(String fname, int origin)
    {
        fileNameExt = fname;
        this.origin = origin;
    }

    // ** instance methods **
    public String getFileNameExt()
    {
        return fileNameExt;
    }

    @Override
    public String getPrimaryKey()
    {
        return getFileNameExt();
    }

    public int getOrigin()
    {
        return origin;
    }
}
