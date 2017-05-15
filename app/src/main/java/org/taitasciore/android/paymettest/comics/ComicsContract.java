package org.taitasciore.android.paymettest.comics;

import android.app.Activity;
import android.content.Intent;

import org.taitasciore.android.paymettest.BasePresenter;
import org.taitasciore.android.model.Comic;
import org.taitasciore.android.paymettest.NetworkView;

import java.util.ArrayList;

/**
 * Created by roberto on 10/05/17.
 */

public interface ComicsContract {

    interface View extends NetworkView {

        void setComics(ArrayList<Comic> comics);
        void showComics(ArrayList<Comic> comics);
        void launchDetailsView(Intent intent);
    }

    interface Presenter extends BasePresenter<View> {

        void getComics();
        void handleComicClick(Activity context, Comic comic);
        void setState(int state);
    }
}
