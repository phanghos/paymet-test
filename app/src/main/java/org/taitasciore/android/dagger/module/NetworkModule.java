package org.taitasciore.android.dagger.module;

import org.taitasciore.android.network.MarvelApi;
import org.taitasciore.android.network.MarvelService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by roberto on 11/05/17.
 */

@Module
public class NetworkModule {

    @Provides @Singleton
    public Retrofit provideRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MarvelApi.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    @Provides @Singleton
    public MarvelService provideMarvelService(Retrofit retrofit) {
        return new MarvelService(retrofit);
    }
}
