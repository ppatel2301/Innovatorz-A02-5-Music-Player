package comp3350.sonicmatic.interfaces;

public interface ISong {

    String getPath();
    String getTitle();
    ISongLength getDuration();
    IArtist getArtist();

}
