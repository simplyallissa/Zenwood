package edu.ucdenver.hertzallissa.zenwood.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Entry {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "date")
    private long date;
    @ColumnInfo(name = "last_update")
    private long lastUpdate;
    @ColumnInfo(name = "emoji")
    private String emoji;
    @ColumnInfo(name = "rating")
    private int rating;
    @ColumnInfo(name = "first_line")
    private String firstLine;
    public Entry(long date, long lastUpdate, String firstLine, String emoji, int rating) {
        this.date = date;
        this.lastUpdate = lastUpdate;
        this.firstLine = firstLine;
        this.emoji = emoji;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Long lastUpdate) {
        this.lastUpdate = Long.parseLong(String.valueOf(lastUpdate));
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
