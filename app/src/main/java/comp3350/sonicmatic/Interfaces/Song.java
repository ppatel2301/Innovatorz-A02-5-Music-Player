package comp3350.sonicmatic.Interfaces;

public interface Song {

    public String getPath();
    public String getName();
    public int getLength();
    public Artist getArtist();

    public int getSizeInBytes();

}
