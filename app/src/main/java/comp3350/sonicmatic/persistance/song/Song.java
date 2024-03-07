package comp3350.sonicmatic.persistance.song;

import comp3350.sonicmatic.interfaces.IPersistentItem;

public class Song implements IPersistentItem
{

    // ** instance variables **
    private String fileNameExt; // store the file_name.extension in the database

    // ** constructors **
    public Song(String fname)
    {
        fileNameExt = fname;
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
}
