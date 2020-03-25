package sample;

import java.io.File;

public class Song {

    private String songName;
    private String songPath;
    private String songTime;

    private File song;

    public Song(File song_) {
        this.song = song_;

        this.songName = this.song.getName();
        this.songPath = this.song.getPath();
        this.songTime = "NaN";

    }

    public String getSongPath() { return this.songPath; }

    @Override
    public String toString() {
        return this.songName;
    }

    /**
     * This function gives possibility to change
     * name of a song in a player but it doesn't
     * change a file's name.
     *
     * @param name new name of a song.
     *
     */

    public void setName(String name) {
        this.songName = name;
    }
}
