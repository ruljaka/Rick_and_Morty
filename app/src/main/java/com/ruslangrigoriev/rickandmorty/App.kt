package com.ruslangrigoriev.rickandmorty

import android.app.Application
import com.ruslangrigoriev.rickandmorty.di.AppComponent
import com.ruslangrigoriev.rickandmorty.di.AppModule
import com.ruslangrigoriev.rickandmorty.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule( AppModule(this))
            .build();
    }
}