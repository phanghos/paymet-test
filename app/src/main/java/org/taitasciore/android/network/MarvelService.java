package org.taitasciore.android.network;

import org.taitasciore.android.auth.AuthData;
import org.taitasciore.android.auth.AuthUtils;
import org.taitasciore.android.model.CharacterDataWrapper;
import org.taitasciore.android.model.ComicDataWrapper;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by roberto on 10/05/17.
 * This class represents the Model layer in the MVP arquitecture
 */

public class MarvelService {

    private static final int ID_CAPTAIN_AMERICA = 1011334; // 1009220;

    private MarvelApi mApi;

    public MarvelService(Retrofit retrofit) {
        mApi = retrofit.create(MarvelApi.class);
    }

    public Observable<Response<CharacterDataWrapper>> getCharacters() {
        AuthData authData = AuthUtils.generateHash();
        return mApi.getCharacters(authData.getTimestamp(), MarvelApi.API_PUBLIC_KEY, authData.getHash());
    }

    public Observable<Response<ComicDataWrapper>> getComics() {
        AuthData authData = AuthUtils.generateHash();
        return mApi.getComics(ID_CAPTAIN_AMERICA, authData.getTimestamp(),
                MarvelApi.API_PUBLIC_KEY, authData.getHash());
    }

    public Observable<Response<ComicDataWrapper>> getComicDetails(int comicId) {
        AuthData authData = AuthUtils.generateHash();
        return mApi.getComicDetails(comicId, authData.getTimestamp(),
                MarvelApi.API_PUBLIC_KEY, authData.getHash());
    }
}
