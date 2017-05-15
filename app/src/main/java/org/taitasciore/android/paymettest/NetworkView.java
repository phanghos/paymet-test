package org.taitasciore.android.paymettest;

/**
 * Created by roberto on 10/05/17.
 * Base interface which every view that performs network calls must implement
 * These are some of the requirements/methods that you would expect such a view to handle
 */

public interface NetworkView extends BaseView {

    void showProgress();
    void hideProgress();
    void showRetryView();
    void hideRetryView();
    void showNetworkError();
    void showNetworkFailedError();
}
