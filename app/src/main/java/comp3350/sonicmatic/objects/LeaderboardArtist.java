package comp3350.sonicmatic.objects;

public class LeaderboardArtist extends MusicArtist{

    int metric;
    public LeaderboardArtist(String name, int metric) {
        super(name);
        this.metric = metric;
    }
}
