package comp3350.sonicmatic.business.comparator;

import java.util.Comparator;

import comp3350.sonicmatic.interfaces.ISong;

public class MusicComparatorByTitle implements Comparator<ISong> {

    @Override
    public int compare(ISong musicTrack, ISong t1) {
        return musicTrack.getTitle().compareTo(t1.getTitle());
    }
}
