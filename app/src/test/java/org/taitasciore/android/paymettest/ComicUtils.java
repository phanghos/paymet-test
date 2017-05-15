package org.taitasciore.android.paymettest;

import org.taitasciore.android.model.Comic;
import org.taitasciore.android.model.ComicDataContainer;
import org.taitasciore.android.model.ComicDataWrapper;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by roberto on 11/05/17.
 */

public final class ComicUtils {

    public static Comic createComic() {
        Comic comic = new Comic();
        comic.setTitle("");
        comic.setDescription("");
        return comic;
    }

    public static ArrayList<Comic> createComics(Comic comic) {
        ArrayList<Comic> comics = new ArrayList<>();
        comics.add(comic);
        return comics;
    }

    public static ComicDataWrapper createResponse(Comic comic) {
        ComicDataWrapper wrapper = new ComicDataWrapper();
        ComicDataContainer container = new ComicDataContainer();
        container.setResults(createComics(comic));
        wrapper.setData(container);
        return wrapper;
    }

    public static ResponseBody createJsonResponse() {
        MediaType mediaType = MediaType.parse("application/json");
        return ResponseBody.create(mediaType, "{}");
    }

    public static Observable<Response<ComicDataWrapper>> createObservableWithComics(Comic comic) {
        return Observable.just(Response.success(createResponse(comic)));
    }
}
