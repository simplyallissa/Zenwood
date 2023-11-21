package edu.ucdenver.hertzallissa.zenwood.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Entry {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "date")
    private int date;
    @ColumnInfo(name = "last_update")
    private int lastUpdate;
    @ColumnInfo(name = "emoji")
    private String emoji;
    @ColumnInfo(name = "rating")
    private int rating;
    @ColumnInfo(name = "first_line")
    private String firstLine;
    public Entry(int date, int lastUpdate, String firstLine, String emoji, int rating) {
        this.lastUpdate=lastUpdate;
        this.firstLine = firstLine;
        this.emoji = emoji;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(int lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getFirstLine() {
        return firstLine;
    }

    public void setFirstLine(String firstLine) {
        this.firstLine = firstLine;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
