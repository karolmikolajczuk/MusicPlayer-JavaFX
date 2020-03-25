package sample;

import java.util.HashMap;
import java.util.Map;

public class Playlist {

    /**
     * It's name of playlist, that have been displayed.
     */
    private String playlistName;

    /**
     *
     */
    private String playlistDescription;

    /**
     * Map which architecture is:
     *  ID, SONG.
     *  Hierarchy is very important cause we decide
     *  the order of playing songs.
     */
    private Map<Integer, Song> listOfSongs = new HashMap<Integer, Song>();


    /**
     * Constructor of playlist. It's not kept like Singleton cause we can make bunch
     *  of playlist's.
     * @param name this is parameter which represents the name of playlist. User can
     *             name playlist i.e. the reason it was created or for which event or
     *             the artist / genre it contains.
     *
     */
    public Playlist(String name) {
        this.playlistName = name;
    }

    /**
     * In whole Player we are displaying the name of playlist cause it is the identifier
     *  of playlist. User now which playlist contains specific music.
     * @return String of playlist's Name.
     */

    public String getPlaylistName() {
        return this.playlistName;
    }

    /**
     * Playlist contains name and description. Description is user's sentences about
     *  the way this playlist was created, what music it contains etc. User in his
     *  history of using can create playlist's with similar names that is why
     *  description was made.
     * @return String of whole user explain.
     */

    public String getPlaylistDescription() {
        return this.playlistDescription;
    }

    /**
     * User can make miss click or just change his mind with playlist's name.
     * That's why this option has been added.
     * @param name it's new name for playlist.
     */

    public void changePlaylistName(final String name) {
        this.playlistName = name;
    }

    /**
     * In some point we need to unzip whole playlist. And I mean here get whole
     *  list of ID (order) and Song's (names, path, files).
     *
     * @return It's Map with Integer and Song class. Obviously both are wrapper's.
     *  One is for integer's and second one is for file's that contains song.
     */
    public Map<Integer, Song> getListOfSongs() {
        return listOfSongs;
    }

    /**
     * We're adding a Song to playlist for making possible to
     *  play it back and stuff.
     * @param song It's a Song we want to remember.
     * @return true if added, false if it didn't happen.
     */

    public boolean addSong(Song song) {
        try {
            this.listOfSongs.putIfAbsent(listOfSongs.size(), song);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
