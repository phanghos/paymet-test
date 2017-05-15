package org.taitasciore.android.paymettest.comics;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import org.taitasciore.android.model.Comic;
import org.taitasciore.android.model.ComicDataWrapper;
import org.taitasciore.android.network.MarvelService;
import org.taitasciore.android.paymettest.details.DetailsActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by roberto on 10/05/17.
 */

public class ComicsPresenter implements ComicsContract.Presenter {

    public static final int STATE_NORMAL = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_ERROR = -1;

    ComicsContract.View mView;
    MarvelService mService;
    private int mState;

    @Inject
    public ComicsPresenter(ComicsContract.View view, MarvelService service) {
        bindView(view);
        mService = service;
        setState(STATE_NORMAL);
    }

    @Override
    public void bindView(ComicsContract.View view) {
        mView = view;
    }

    @Override
    public void unbindView() {
        mView = null;
    }

    @Override
    public void getComics() {
        mView.hideRetryView();

        switch (getState()) {
            case STATE_LOADING:
                mView.showProgress();
                return;
            case STATE_ERROR:
                mView.showRetryView();
                return;
        }

        mView.showProgress();
        setState(STATE_LOADING);

        mService.getComics()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Response<ComicDataWrapper>>() {
                    @Override
                    public void onNext(Response<ComicDataWrapper> response) {
                        setState(STATE_NORMAL);

                        if (mView != null) {
                            mView.hideProgress();

                            if (response.isSuccessful()) {
                                ComicDataWrapper wrapper = response.body();
                                ArrayList<Comic> comics = wrapper.getData().getResults();
                                mView.setComics(comics);
                                mView.showComics(comics);
                            } else {
                                mView.showNetworkFailedError();
                                mView.showRetryView();
                                setState(STATE_ERROR);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView != null) {
                            mView.hideProgress();
                            mView.showNetworkError();
                            mView.showRetryView();
                            setState(STATE_ERROR);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * This method handles the click event on a comic. In this case,
     * it is responsible for setting up the intent to launch DetailsActivity
     * @param context Activity context
     * @param comic Comic instance
     */
    @Override
    public void handleComicClick(Activity context, Comic comic) {
        int comicId = comic.getId();
        String comicTitle = comic.getTitle();
        Intent i = new Intent(context, DetailsActivity.class);
        i.putExtra("comic_id", comicId);
        i.putExtra("comic_title", comicTitle);
        mView.launchDetailsView(i);
    }

    public int getState() {
        return mState;
    }

    @Override
    public void setState(int state) {
        this.mState = state;
    }

    @VisibleForTesting
    public ComicsContract.View getView() {
        return mView;
    }

    @VisibleForTesting
    public MarvelService getService() {
        return mService;
    }
}
