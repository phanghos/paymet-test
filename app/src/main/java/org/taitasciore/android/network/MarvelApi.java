package org.taitasciore.android.network;

import org.taitasciore.android.model.CharacterDataWrapper;
import org.taitasciore.android.model.ComicDataWrapper;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by roberto on 10/05/17.
 * Marvel API
 */

public interface MarvelApi {

    String BASE_URL = "http://gateway.marvel.com/v1/public/"; // Marvel API base URL
    String API_PUBLIC_KEY = "d5227a377cc68762ff9ca30d1352341b"; // Marvel API public key
    String API_PRIVATE_KEY = "5d47fa53aa486084ed3caf13161b813e13558102"; // Marvel API private key

    @GET("characters")
    Observable<Response<CharacterDataWrapper>> getCharacters(@Query("ts") long ts,
                                                             @Query("apikey") String apiKey,
                                                             @Query("hash") String hash);

    /**
     * This method fetches a list of comics of a given character (by ID)
     * @param characterId Character ID
     * @param ts Unique timestamp value
     * @param apiKey Marvel API public key
     * @param hash MD5-encrypted hash
     * @return List of comics of the given character
     */
    @GET("characters/{characterId}/comics")
    Observable<Response<ComicDataWrapper>> getComics(
            @Path("characterId") int characterId, @Query("ts") long ts,
            @Query("apikey") String apiKey, @Query("hash") String hash);

    /**
     * This method fetches a comic's details/info given its ID
     * @param comicId Comic ID
     * @param ts Unique timestamp value
     * @param apiKey Marvel API public key
     * @param hash MD5-encrypted hash
     * @return A comic's details/info
     */
    @GET("comics/{comicId}")
    Observable<Response<ComicDataWrapper>> getComicDetails(
            @Path("comicId") int comicId, @Query("ts") long ts,
            @Query("apikey") String apiKey, @Query("hash") String hash);
}
