package org.taitasciore.android.paymettest.comics;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.pnikosis.materialishprogress.ProgressWheel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.taitasciore.android.busevents.ComicClickEvent;
import org.taitasciore.android.dagger.component.ComicsComponent;
import org.taitasciore.android.dagger.component.DaggerComicsComponent;
import org.taitasciore.android.dagger.module.ComicsModule;
import org.taitasciore.android.model.Comic;
import org.taitasciore.android.paymettest.MarvelApp;
import org.taitasciore.android.paymettest.R;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by roberto on 10/05/17.
 */

public class ComicsFragment extends Fragment implements ComicsContract.View {

    private ArrayList<Comic> mComics;

    @Inject ComicsContract.Presenter mPresenter;

    @BindView(R.id.list) RecyclerView mRecyclerView;
    private ComicAdapter mComicAdapter;

    @BindView(R.id.wheel) ProgressWheel mWheel;
    @BindView(R.id.btnRetry) Button mBtnRetry;

    /**
     * Presenter is injected via Dagger
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        MarvelApp marvelApp = ((MarvelApp) getActivity().getApplication());
        ComicsComponent component = DaggerComicsComponent.builder()
                .networkComponent(marvelApp.getNetworkComponent())
                .comicsModule(new ComicsModule(this))
                .build();

        component.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_comics, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mComics == null) mPresenter.getComics();
        else showComics(mComics);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bindPresenter();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbindPresenter();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onComicClicked(ComicClickEvent event) {
        mPresenter.handleComicClick(getActivity(), event.comic);
    }

    @Override
    public void bindPresenter() {
        if (mPresenter != null) mPresenter.bindView(this);
    }

    @Override
    public void unbindPresenter() {
        mPresenter.unbindView();
    }

    @Override
    public void setComics(ArrayList<Comic> comics) {
        mComics = comics;
    }

    @Override
    public void showComics(ArrayList<Comic> comics) {
        if (mComicAdapter == null) {
            mComicAdapter = new ComicAdapter(comics);
            mRecyclerView.setAdapter(mComicAdapter);
        } else {
            mRecyclerView.setAdapter(mComicAdapter);
        }
    }

    @Override
    public void launchDetailsView(Intent intent) {
        getActivity().startActivity(intent);
    }

    @Override
    public void showProgress() {
        mWheel.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mWheel.setVisibility(View.GONE);
    }

    @Override
    public void showRetryView() {
        mBtnRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetryView() {
        mBtnRetry.setVisibility(View.GONE);
    }

    @Override
    public void showNetworkError() {
        Toast.makeText(getActivity(), getString(R.string.network_error),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNetworkFailedError() {
        Toast.makeText(getActivity(), getString(R.string.network_failed_error),
                Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnRetry) void onRetryClicked() {
        mPresenter.setState(ComicsPresenter.STATE_NORMAL);
        mPresenter.getComics();
    }
}
