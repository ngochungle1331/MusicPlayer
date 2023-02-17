package com.fox.training.data.model;

public class Music {

    private final int songImage;
    private final String songName;
    private final String songAuthor;

    public Music(int songImage, String songName, String songAuthor) {
        this.songImage = songImage;
        this.songName = songName;
        this.songAuthor = songAuthor;
    }

    public int getSongImage() {
        return songImage;
    }

    public String getSongName() {
        return songName;
    }

    public String getSongAuthor() {
        return songAuthor;
    }

}
