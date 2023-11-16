package edu.ucdenver.hertzallissa.zenwood;

public class Entry {
    private int date;
    private int lastUpdate;
    private String firstLine;
    private String emoji;
    private int rating;

    public Entry(int date, int lastUpdate, String firstLine, String emoji, int rating) {
        this.date = 0;
        this.lastUpdate=lastUpdate;
        this.firstLine = firstLine;
        this.emoji = emoji;
        this.rating = rating;
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
