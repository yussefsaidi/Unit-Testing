package com.yussefsaidi.unittesting.di;

import android.app.Application;

import androidx.room.Room;

import com.yussefsaidi.unittesting.persistence.NoteDao;
import com.yussefsaidi.unittesting.persistence.NoteDatabase;
import com.yussefsaidi.unittesting.repository.NoteRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.yussefsaidi.unittesting.persistence.NoteDatabase.DATABASE_NAME;

@Module
public class AppModule {

    @Singleton
    @Provides
    static NoteDatabase provideNoteDatabase(Application application){
        return Room.databaseBuilder(
                application,
                NoteDatabase.class,
                DATABASE_NAME
        ).build();
    }

    @Singleton
    @Provides
    static NoteDao provideNoteDao(NoteDatabase noteDatabase){
        return noteDatabase.getNoteDao();
    }
}
