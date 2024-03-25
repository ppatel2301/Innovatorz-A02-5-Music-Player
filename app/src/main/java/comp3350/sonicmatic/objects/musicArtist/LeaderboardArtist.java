package comp3350.sonicmatic.objects.musicArtist;

import comp3350.sonicmatic.interfaces.IArtist;

public class LeaderboardArtist extends MusicArtist implements Comparable<LeaderboardArtist>{

    int metric;
    public LeaderboardArtist(String name, int metric) {
        super(name);
        this.metric = metric;
    }

    public LeaderboardArtist(IArtist artist, int metric){
        super(artist.getName());
        this.metric = metric;
    }

    public int getMetric(){ return this.metric;}

    @Override
    public int compareTo(LeaderboardArtist o) {
        return Integer.compare(this.metric, o.metric);
    }
}
