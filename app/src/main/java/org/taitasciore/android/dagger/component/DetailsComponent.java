package org.taitasciore.android.dagger.component;

import org.taitasciore.android.dagger.CustomScope;
import org.taitasciore.android.dagger.module.DetailsModule;
import org.taitasciore.android.paymettest.details.DetailsFragment;

import dagger.Component;

/**
 * Created by roberto on 11/05/17.
 */

@CustomScope
@Component(dependencies = NetworkComponent.class, modules = DetailsModule.class)
public interface DetailsComponent {

    void inject(DetailsFragment detailsFragment);
}
