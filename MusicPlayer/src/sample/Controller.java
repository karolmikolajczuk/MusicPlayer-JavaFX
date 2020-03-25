package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.io.File;
import java.util.List;


public class Controller {

    /**
     * Label where is printed currently playing song's Name.
     */
    @FXML private Label songName;

    /**
     * Slider which is song's time progress Bar.
     */
    @FXML private Slider songLengthBar;

    /**
     * Label where is printed song's present Time.
     * Frequently refreshing.
     */
    @FXML private Label songPresentTime;

    /**
     * Label where is set song's full time.
     */
    @FXML private Label songTime;

    /**
     * Object for media player. It gives developer option to
     *  manipulate currently playing song like Play, Pause, Stop etc.
     */
    private MediaPlayer mediaPlayer;

    /**
     * Flag which tells us if media player is currently playing.
     */
    private boolean isPlaying = false;

    /**
     * Flag which tells us if user want to resume song.
     */
    private boolean isPaused = false;

    /**
     * Part of UI where user picks auto play.
     */
    @FXML private RadioButton playlistRoll;

    /**
     * Part of UI. "Snake"
     */
    @FXML private RadioButton playlistJustNext;

    /**
     * Part of UI. Just this Song(currently playing one).
     */
    @FXML private RadioButton playlistSingleSong;

    /**
     * Playlist which is a list of songs currently
     *  displayed at the list.
     */
    private Playlist currentPlaylist;

    /**
     * Button for start playing and pausing a song.
     */
    @FXML private Button songPlayPause;

    /**
     * Button for stopping playing. Resetting to 00:00.
     */
    @FXML private Button songStop;

    /**
     * Button for picking song before current one.
     */
    @FXML private Button songBefore;

    /**
     * Button for picking next song in the list.
     */
    @FXML private Button songNext;

    /**
     * Button for picking next song randomly.
     */
    @FXML private Button songRandom;

    /**
     * ListView widget which contains a list of a song's.
     * Song's are a Song class wrapper for File.
     */
    @FXML private ListView<Song> songList;

    /**
     * initialize method which makes things at the
     *  beginning of an application. We're here handling an
     *  event's and stuff.
     */
    public void initialize() {
        System.out.println("initialize");

        mediaPlayer = new MediaPlayer(new Media(""));

        /*
         * When something is dragged over the songList.
         */
        songList.setOnDragOver(dragEvent ->  {
                if (dragEvent.getDragboard().hasFiles()) {
                    System.out.println("Accepted");
                    dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                dragEvent.consume();
        });

        /*
         * Handling an event for dragging and dropping file into
         *  list of widget's which is a list of Song's.
         */
        songList.setOnDragDropped(dragEvent ->  {
                Dragboard dragboard = dragEvent.getDragboard();
                if (!dragboard.hasFiles()) { return; } // no file.

                List<File> listOfFIles = dragboard.getFiles();
                for (File f:
                     listOfFIles) {
                    System.out.println("Dragged and dropped a file");
                    System.out.println(f.getName());
                    System.out.println(f.getPath());
                    System.out.println("> size of list: " + songList.getItems().size());
                    songList.getItems().add(new Song(f));
                    System.out.println("> size of list: " + songList.getItems().size());
                    if (!currentPlaylist.addSong(new Song(f))) {
                        System.out.println("wtf");
                        return;
                    }

                }
                dragEvent.setDropCompleted(true);
                dragEvent.consume();
        });

        /*
         * Event for Play/Pause button.
         */
        songPlayPause.setOnAction(actionEvent ->  {
                System.out.println("Test:");
                System.out.println(songList.getSelectionModel()
                        .getSelectedItem()
                        .toString());

                if (isPlaying) { mediaPlayer.pause(); isPaused = true; isPlaying = false; }
                else if (isPaused) { mediaPlayer.play(); isPaused = false; isPlaying = true; }
                else {
                    playSelectedSong();
                }
        });

        /*
         * Event for Stop button.
         */
        songStop.setOnAction(actionEvent ->  {
                System.out.println("Stopping");

                mediaPlayer.stop();
                isPlaying = false;
        });

        /*
         * Event for Before button.
         */
        songBefore.setOnAction(actionEvent -> {
                songList.getSelectionModel()
                        .select(songList
                                .getSelectionModel()
                                .getSelectedIndex());

                playSelectedSong();
        });

        /*
         * Event for Next button.
         */
        songNext.setOnAction(actionEvent -> {
                if (songList.getItems().iterator().hasNext()) {
                    songList.getSelectionModel().select(songList.getSelectionModel().getSelectedIndex()+1);
                }

                playSelectedSong();
        });

        songRandom.setOnAction(actionEvent -> {
            int number = (int)(Math.random() * songList.getItems().size());

            System.out.println("Randomly picked: " + number + "When songList has " + songList.getItems().size() + " items.");

            songList.getSelectionModel().select(number);

            playSelectedSong();
        });
    }

    private void playSelectedSong() {

        if (mediaPlayer.getStatus().equals(MediaPlayer.Status.READY))
        // Prepare player with a song in cache.
        mediaPlayer = new MediaPlayer(new Media(new File(
                songList.getSelectionModel()
                        .getSelectedItem()
                        .getSongPath())
                .toURI()
                .toString()));

        // Set song's name
        songName.setText(songList.getSelectionModel()
                                 .getSelectedItem()
                                 .toString());

        // Set song's time
        songTime.setText("/" + mediaPlayer.getTotalDuration()
                                    .toString());

        // Play song.
        mediaPlayer.play();

        // Set flags for correct button's work.
        isPlaying = true;
        isPaused = false;
    }
}
