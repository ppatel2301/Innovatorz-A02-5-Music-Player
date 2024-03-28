package comp3350.sonicmatic.business.access;

import java.util.ArrayList;

import comp3350.sonicmatic.application.Services;
import comp3350.sonicmatic.interfaces.IPlaylist;
import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.objects.MusicTrackPlaylist;
import comp3350.sonicmatic.persistance.Persistence;
import comp3350.sonicmatic.persistance.playlist.NullPlaylist;
import comp3350.sonicmatic.persistance.playlist.Playlist;
import comp3350.sonicmatic.persistance.playlist.PlaylistPersistence;
import comp3350.sonicmatic.persistance.playlistSong.PlaylistSong;
import comp3350.sonicmatic.persistance.playlistSong.PlaylistSongPersistence;
import comp3350.sonicmatic.persistance.profile.GuestProfile;
import comp3350.sonicmatic.persistance.profile.NullProfile;

public class AccessPlaylist {

    // ** instance variables **
    private PlaylistPersistence playlistPersistence;
    private PlaylistSongPersistence playlistSongPersistence;

    // ** constructors **
    public AccessPlaylist(){
        this.playlistPersistence = Services.getPlaylistPersistence();
        this.playlistSongPersistence = Services.getPlaylistSongPersistence();
    }

    public AccessPlaylist(final PlaylistPersistence playlistPersistence, final PlaylistSongPersistence playlistSongPersistence)
    {
        this.playlistPersistence = playlistPersistence;
        this.playlistSongPersistence = playlistSongPersistence;
    }

    // ** mutators **
    public ArrayList<IPlaylist> getPlaylists(AccessProfile profileAccess)
    {
        ArrayList<IPlaylist> playlists = new ArrayList<IPlaylist>();
        ArrayList<Playlist> playlists_from_db; // the list of playlists the user who is logged in has
        ArrayList<PlaylistSong> playlist_songs_from_db; // songs in the playlist from a database

        int curr_playlist;
        String username = profileAccess.getUsername();

        if (!(username.equals(GuestProfile.getGuestProfile().getUsername()) || username.equals(NullProfile.getNullProfile().getUsername())))
        {
            playlists_from_db = playlistPersistence.getPlaylistsOfUser(profileAccess.getUsername());

            for (Playlist p : playlists_from_db)
            {

                try
                {
                    curr_playlist = Integer.parseInt(p.getPrimaryKey());
                }
                catch (NumberFormatException nfe)
                {
                    playlists.clear();
                    return playlists;
                }

                playlists.add(new MusicTrackPlaylist(p.getName())); // add a new playlist in

                playlist_songs_from_db = playlistSongPersistence.getPlaylistSongs(curr_playlist); // get all the songs for this playlist

                for (PlaylistSong s : playlist_songs_from_db) // iterate through them and make usable data with them
                {
                    playlists.get(playlists.size()-1).addMusicTracks(Services.createSongFromPath(s.getFileNameExt()));
                }
            }
        }

        return playlists;
    }

    public boolean newPlaylist(String name, AccessProfile profileAccess)
    {
        boolean success = false;
        String username;

        if (profileAccess != null)
        {
            username = profileAccess.getUsername();

            if (!(username.equals(GuestProfile.getGuestProfile().getUsername()) || username.equals(NullProfile.getNullProfile().getUsername())))
            {
                if (Persistence.isStringOkay(name))
                {
                    success = playlistPersistence.insert(new Playlist(username, name));
                }
            }
        }
        return success;
    }

    public boolean deletePlaylist(String name, AccessProfile profileAccess)
    {
        boolean success = false;
        ArrayList<PlaylistSong> all_songs = new ArrayList<PlaylistSong>();
        Playlist delete_me;

        if (profileAccess != null && !(profileAccess.getUsername().equals(GuestProfile.getGuestProfile().getUsername()) || profileAccess.getUsername().equals(NullProfile.getNullProfile().getUsername())))
        {
            delete_me = playlistPersistence.get(name, profileAccess.getUsername());

            // delete any playlist song rows associated with this playlist
            all_songs = playlistSongPersistence.getPlaylistSongs(delete_me.getId());
            for (PlaylistSong p : all_songs)
            {
                playlistPersistence.delete(p);
            }


            success = playlistPersistence.delete(delete_me);
        }

        return success;
    }

    public boolean insertIntoPlaylist(String name, ISong song, AccessProfile profileAccess)
    {
        boolean success = false;
        String username;
        Playlist from_db;
        PlaylistSong new_song;

        if (profileAccess != null && song != null)
        {
            username = profileAccess.getUsername();

            if (!(username.equals(GuestProfile.getGuestProfile().getUsername()) || username.equals(NullProfile.getNullProfile().getUsername())))
            {
                from_db = playlistPersistence.get(name, username); // need to get ID of playlist from DB

                new_song = new PlaylistSong(song.getPath(), from_db.getId());
                
                success = playlistSongPersistence.insert(new_song);
            }
        }
        return success;
    }

    public boolean deleteFromPlaylist(String name, ISong song, AccessProfile profileAccess)
    {
        boolean success = false;
        String username;
        Playlist from_db;
        PlaylistSong delete_song;

        if (profileAccess != null && song != null)
        {
            username = profileAccess.getUsername();

            if (!(username.equals(GuestProfile.getGuestProfile().getUsername()) || username.equals(NullProfile.getNullProfile().getUsername())))
            {
                from_db = playlistPersistence.get(name, username); // get ID of playlist from db

                delete_song = new PlaylistSong(song.getPath(), from_db.getId());
                
                success = playlistSongPersistence.delete(delete_song);
            }
        }
        return success;
    }
}