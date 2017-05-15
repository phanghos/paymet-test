package org.taitasciore.android.dagger.module;

import org.taitasciore.android.dagger.CustomScope;
import org.taitasciore.android.network.MarvelService;
import org.taitasciore.android.paymettest.details.DetailsContract;
import org.taitasciore.android.paymettest.details.DetailsPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roberto on 11/05/17.
 */

@Module
public class DetailsModule {

    private DetailsContract.View mView;

    public DetailsModule(DetailsContract.View view) {
        mView = view;
    }

    @Provides @CustomScope
    public DetailsContract.View provideDetailsView() {
        return mView;
    }

    @Provides @CustomScope
    public DetailsContract.Presenter provideDetailsPresenter(
            DetailsContract.View view, MarvelService service) {
        return new DetailsPresenter(view, service);
    }
}
