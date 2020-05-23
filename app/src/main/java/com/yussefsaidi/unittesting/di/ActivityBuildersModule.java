package com.yussefsaidi.unittesting.di;

import com.yussefsaidi.unittesting.ui.note.NoteActivity;
import com.yussefsaidi.unittesting.ui.noteslist.NotesListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract NotesListActivity contributesNotesListActivity();

    @ContributesAndroidInjector
    abstract NoteActivity contributesNoteActivity();
}
