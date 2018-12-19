package com.fundamentos.abisu.basedatosrealm.Aplicacion;

import android.app.Application;

import com.fundamentos.abisu.basedatosrealm.Modelos.WaifuModelo;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MiAplicacion extends Application {
    public static AtomicInteger waifuId = new AtomicInteger();

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
        setUpRealConfig();
        Realm realm = Realm.getDefaultInstance();
        waifuId = getByTabla(realm, WaifuModelo.class);
        realm.close();
    }

    private void setUpRealConfig() {
        RealmConfiguration configuration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
    }

    private <T extends RealmObject> AtomicInteger getByTabla(Realm realm, Class<T> tClass){
        RealmResults<T> results = realm.where(tClass).findAll();
        return (results.size()>0)? new AtomicInteger(results.max("id").intValue()): new AtomicInteger();
    }
}
