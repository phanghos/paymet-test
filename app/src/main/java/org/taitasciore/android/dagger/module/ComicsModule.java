package org.taitasciore.android.dagger.module;

import org.taitasciore.android.dagger.CustomScope;
import org.taitasciore.android.network.MarvelService;
import org.taitasciore.android.paymettest.comics.ComicsContract;
import org.taitasciore.android.paymettest.comics.ComicsPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roberto on 11/05/17.
 */

@Module
public class ComicsModule {

    private ComicsContract.View mView;

    public ComicsModule(ComicsContract.View view) {
        mView = view;
    }

    @Provides @CustomScope
    public ComicsContract.View provideComicsView() {
        return mView;
    }

    @Provides @CustomScope
    public ComicsContract.Presenter provideComicsPresenter(
            ComicsContract.View view, MarvelService service) {
        return new ComicsPresenter(view, service);
    }
}
