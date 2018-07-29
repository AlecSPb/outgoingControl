package com.example.jorgegonzalezcabrera.outgoing.applications;

import android.app.Application;

import com.example.jorgegonzalezcabrera.outgoing.models.appConfiguration;
import com.example.jorgegonzalezcabrera.outgoing.models.periodicEntry;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class myApplication extends Application {

    public static AtomicInteger periodicEntryId;
    public static AtomicInteger appConfigurationId;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);

        Number higherPeriodicEntryId = Realm.getDefaultInstance().where(periodicEntry.class).max("id");
        periodicEntryId = new AtomicInteger((higherPeriodicEntryId == null) ? 0 : higherPeriodicEntryId.intValue());

        Number higherAppConfigurationId = Realm.getDefaultInstance().where(appConfiguration.class).max("id");
        appConfigurationId = new AtomicInteger((higherAppConfigurationId == null) ? 0 : higherAppConfigurationId.intValue());
    }
}
//TODO: explore configuration possibilities