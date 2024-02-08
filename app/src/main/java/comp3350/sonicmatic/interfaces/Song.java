package comp3350.sonicmatic.interfaces;

public interface Song {

    String getPath();
    String getTitle();
    SongLength getDuration();
    Artist getArtist();

}
