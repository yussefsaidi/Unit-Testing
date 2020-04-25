package com.yussefsaidi.unittesting.di;

import com.yussefsaidi.unittesting.NotesListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract NotesListActivity contributesNotesListActivity();
}
