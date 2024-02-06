package comp3350.sonicmatic.interfaces;

public interface Song {

    String getPath();
    String getName();
    int getLength();
    Artist getArtist();

    int getSizeInBytes();

}
