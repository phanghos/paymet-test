package org.taitasciore.android.paymettest;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.taitasciore.android.model.Comic;
import org.taitasciore.android.model.ComicDataWrapper;
import org.taitasciore.android.network.MarvelService;
import org.taitasciore.android.paymettest.details.DetailsContract;
import org.taitasciore.android.paymettest.details.DetailsPresenter;

import java.io.IOException;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by roberto on 11/05/17.
 */

public class DetailsPresenterTest {

    MarvelService service;
    DetailsContract.View view;
    DetailsPresenter presenter;

    @BeforeClass
    public static void setupClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(Callable<Scheduler> schedulerCallable) throws Exception {
                return Schedulers.trampoline();
            }
        });
    }

    @Before
    public void setup() {
        service = mock(MarvelService.class);
        view = mock(DetailsContract.View.class);
        presenter = new DetailsPresenter(view, service);
    }

    @Test
    public void testNotNull() {
        assertNotNull(service);
        assertNotNull(view);
        assertNotNull(presenter);
    }

    @Test
    public void testMockViewIsUsed() {
        assertEquals(view, presenter.getView());
    }

    @Test
    public void testMockServiceIsUsed() {
        assertEquals(service, presenter.getService());
    }

    @Test
    public void testLoaderIsShown() {
        presenter.setState(1);
        presenter.getComicDetails(0);
        verify(view).showProgress();
    }

    @Test
    public void testRetryButtonIsShown() {
        presenter.setState(-1);
        presenter.getComicDetails(0);
        verify(view).showRetryView();
    }

    @Test
    public void testRequestSuccess() {
        Comic comic = ComicUtils.createComic();
        Observable<Response<ComicDataWrapper>> observable =
                ComicUtils.createObservableWithComics(comic);

        when(service.getComicDetails(0)).thenReturn(observable);
        presenter.setState(0);
        presenter.getComicDetails(0);
        observable.test().awaitTerminalEvent();
        verify(service).getComicDetails(0);
        verify(view).hideRetryView();
        verify(view).showProgress();
        verify(view).hideProgress();
        verify(view).setComicDetails(comic);
        verify(view).showComicDetails(comic);
        assertEquals(0, presenter.getState());
    }

    @Test
    public void testRequestFailure() throws IOException {
        Response<ComicDataWrapper> response = Response.error(400, ComicUtils.createJsonResponse());
        Observable<Response<ComicDataWrapper>> observable = Observable.just(response);

        when(service.getComicDetails(0)).thenReturn(observable);
        presenter.setState(0);
        presenter.getComicDetails(0);
        observable.test().awaitTerminalEvent();
        verify(service).getComicDetails(0);
        verify(view).hideRetryView();
        verify(view).showProgress();
        verify(view).hideProgress();
        verify(view).showNetworkFailedError();
        verify(view).showRetryView();
        assertEquals(-1, presenter.getState());
    }

    @Test
    public void testState() {
        presenter.setState(0);
        assertEquals(0, presenter.getState());
    }
}
