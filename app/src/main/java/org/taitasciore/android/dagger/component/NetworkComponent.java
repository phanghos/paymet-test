package org.taitasciore.android.dagger.component;

import org.taitasciore.android.dagger.module.NetworkModule;
import org.taitasciore.android.network.MarvelService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by roberto on 11/05/17.
 */

@Singleton
@Component(modules = NetworkModule.class)
public interface NetworkComponent {

    MarvelService marvelService();
}
