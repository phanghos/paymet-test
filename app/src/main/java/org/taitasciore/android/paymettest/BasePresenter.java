package org.taitasciore.android.paymettest;

/**
 * Created by roberto on 10/05/17.
 * Base interface which every presenter must implement
 */

public interface BasePresenter<V> {

    void bindView(V view);
    void unbindView();
}
