package edu.ucdenver.hertzallissa.zenwood.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EntryDao {
    @Query("SELECT * FROM entry ORDER BY date DESC")
    List<Entry> getAllEntries();

    @Query("SELECT * FROM entry WHERE id = :entryId")
    Entry getEntryById(long entryId);

    @Insert
    long insertEntry(Entry entry);

    @Update
    void updateEntry(Entry entry);

    @Delete
    void deleteEntry(Entry entry);
}
