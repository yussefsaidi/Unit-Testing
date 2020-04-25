package com.yussefsaidi.unittesting.di;


import androidx.lifecycle.ViewModelProvider;

import com.yussefsaidi.unittesting.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);

}
