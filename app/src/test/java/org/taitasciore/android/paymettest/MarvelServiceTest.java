package org.taitasciore.android.paymettest;

import org.junit.Before;
import org.junit.Test;
import org.taitasciore.android.network.MarvelService;

import javax.inject.Inject;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by roberto on 12/05/17.
 */

public class MarvelServiceTest {

    @Inject MarvelService service;

    @Test
    public void testNotNull() {
        assertNotNull(service);
    }

    @Test
    public void testObservablesNotNull() {
        assertNotNull(service.getComics());
        assertNotNull(service.getComicDetails(0));
    }
}
