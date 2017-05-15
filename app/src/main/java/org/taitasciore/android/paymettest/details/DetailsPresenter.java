package org.taitasciore.android.paymettest.details;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.VisibleForTesting;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;

import org.taitasciore.android.model.Comic;
import org.taitasciore.android.model.ComicDataContainer;
import org.taitasciore.android.model.ComicDataWrapper;
import org.taitasciore.android.network.MarvelService;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

/**
 * Created by roberto on 10/05/17.
 */

public class DetailsPresenter implements DetailsContract.Presenter {

    public static final int STATE_NORMAL = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_ERROR = -1;

    private DetailsContract.View mView;
    private MarvelService mService;
    private int mState;

    @Inject
    public DetailsPresenter(DetailsContract.View view, MarvelService service) {
        bindView(view);
        mService = service;
        setState(STATE_NORMAL);
    }

    @Override
    public void bindView(DetailsContract.View view) {
        mView = view;
    }

    @Override
    public void unbindView() {
        mView = null;
    }

    @Override
    public void getComicDetails(int comicId) {
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

        mService.getComicDetails(comicId)
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
                                ComicDataContainer container = wrapper.getData();
                                Comic comic = container.getResults().get(0);
                                mView.setComicDetails(comic);
                                mView.showComicDetails(comic);
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

    public int getState() {
        return mState;
    }

    @Override
    public void setState(int state) {
        mState = state;
    }

    @Override
    public SpannableStringBuilder buildTitleSpan(Context context, String title) {
        SpannableStringBuilder ssb;
        CalligraphyTypefaceSpan typefaceSpan = new CalligraphyTypefaceSpan(
                TypefaceUtils.load(context.getAssets(), "fonts/OpenSans-Semibold.ttf"));
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.1f);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.BLACK);

        String span = "Title: " + title;
        ssb = new SpannableStringBuilder(span);
        int start = span.indexOf(":") + 1;
        int end = span.length();
        ssb.setSpan(typefaceSpan, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ssb.setSpan(sizeSpan, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ssb.setSpan(colorSpan, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        return ssb;
    }

    /**
     * This method builds a span for the description field of a comic.
     * Note that the description may be null, so we need to check for that and return
     * immediately in that case or an exception will be thrown.
     * @param context Current context
     * @param description Description text
     * @return Built span
     */
    @Override
    public SpannableStringBuilder buildDescriptionSpan(Context context, String description) {
        if (description == null) {
            return null;
        }

        SpannableStringBuilder ssb;
        CalligraphyTypefaceSpan typefaceSpan = new CalligraphyTypefaceSpan(
                TypefaceUtils.load(context.getAssets(), "fonts/OpenSans-Semibold.ttf"));
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.1f);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.BLACK);

        String descriptionSpan = "Description: " + Html.fromHtml(description);
        ssb = new SpannableStringBuilder(descriptionSpan);
        int start = descriptionSpan.indexOf(":") + 1;
        int end = descriptionSpan.length();
        ssb.setSpan(typefaceSpan, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ssb.setSpan(sizeSpan, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ssb.setSpan(colorSpan, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        return ssb;
    }

    @VisibleForTesting
    public DetailsContract.View getView() {
        return mView;
    }

    @VisibleForTesting
    public MarvelService getService() {
        return mService;
    }
}
