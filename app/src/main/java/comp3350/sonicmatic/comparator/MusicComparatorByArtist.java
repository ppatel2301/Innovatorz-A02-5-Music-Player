package comp3350.sonicmatic.comparator;

import java.util.Comparator;

import comp3350.sonicmatic.interfaces.ISong;

public class MusicComparatorByArtist implements Comparator<ISong> {

    @Override
    public int compare(ISong musicTrack, ISong t1) {
        return musicTrack.getArtist().getName().compareTo(t1.getArtist().getName());
    }
}