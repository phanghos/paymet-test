package org.taitasciore.android.paymettest;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.taitasciore.android.dagger.component.DaggerNetworkComponent;
import org.taitasciore.android.dagger.component.NetworkComponent;
import org.taitasciore.android.dagger.module.NetworkModule;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by roberto on 10/05/17.
 */

public class MarvelApp extends Application {

    private NetworkComponent mNetworkComponent;

    /**
     * Fresco initialization
     * Dagger component initialization
     * Calligraphy initialization, specifying default font thoughout the whole app
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        setNetworkComponent();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/OpenSans-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public NetworkComponent getNetworkComponent() {
        return mNetworkComponent;
    }

    public void setNetworkComponent() {
        mNetworkComponent = DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule())
                .build();
    }
}
