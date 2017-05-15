package org.taitasciore.android.paymettest.details;

import android.content.Context;
import android.text.SpannableStringBuilder;

import org.taitasciore.android.model.Comic;
import org.taitasciore.android.paymettest.BasePresenter;
import org.taitasciore.android.paymettest.NetworkView;

/**
 * Created by roberto on 10/05/17.
 */

public interface DetailsContract {

    interface View extends NetworkView {

        void setComicDetails(Comic comic);
        void showComicDetails(Comic comic);
    }

    interface Presenter extends BasePresenter<View> {

        void getComicDetails(int comicId);
        void setState(int state);
        SpannableStringBuilder buildTitleSpan(Context context, String title);
        SpannableStringBuilder buildDescriptionSpan(Context context, String description);
    }
}
