package org.taitasciore.android.dagger.component;

import org.taitasciore.android.dagger.module.ComicsModule;
import org.taitasciore.android.dagger.CustomScope;
import org.taitasciore.android.paymettest.comics.ComicsFragment;

import dagger.Component;

/**
 * Created by roberto on 11/05/17.
 */

@CustomScope
@Component(dependencies = NetworkComponent.class, modules = ComicsModule.class)
public interface ComicsComponent {

    void inject(ComicsFragment comicsFragment);
}
