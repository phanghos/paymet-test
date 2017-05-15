package org.taitasciore.android.paymettest.details;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.taitasciore.android.dagger.component.DaggerDetailsComponent;
import org.taitasciore.android.dagger.component.DetailsComponent;
import org.taitasciore.android.dagger.module.DetailsModule;
import org.taitasciore.android.model.Comic;
import org.taitasciore.android.model.Image;
import org.taitasciore.android.paymettest.MarvelApp;
import org.taitasciore.android.paymettest.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by roberto on 10/05/17.
 */

public class DetailsFragment extends Fragment implements DetailsContract.View {

    private int mComicId;
    private Comic mComic;

    @Inject DetailsContract.Presenter mPresenter;

    @BindView(R.id.headerImage) SimpleDraweeView mHeaderImage;

    @BindView(R.id.tvTitle) TextView mTvTitle;
    @BindView(R.id.tvDescription) TextView mTvDescription;
    @BindView(R.id.wheel) ProgressWheel mWheel;
    @BindView(R.id.btnRetry) Button mBtnRetry;

    /**
     * We extract the comic ID sent by DetailsActivity through a Bundle
     * @param comicId Comic ID
     * @return DetailsFragment instance with arguments
     */
    public static DetailsFragment newInstance(int comicId) {
        DetailsFragment f = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt("comic_id", comicId);
        f.setArguments(args);
        return f;
    }

    /**
     * Presenter is injected via Dagger
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        MarvelApp marvelApp = ((MarvelApp) getActivity().getApplication());
        DetailsComponent detailsComponent = DaggerDetailsComponent.builder()
                .networkComponent(marvelApp.getNetworkComponent())
                .detailsModule(new DetailsModule(this))
                .build();
        detailsComponent.inject(this);

        mComicId = getArguments().getInt("comic_id");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, v);
        return v;
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
        if (mComic == null) mPresenter.getComicDetails(mComicId);
        else showComicDetails(mComic);
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
    public void setComicDetails(Comic comic) {
        mComic = comic;
    }

    @Override
    public void showComicDetails(Comic comic) {
        Image thumbnail = comic.getThumbnail();
        String thumbnailUrl = thumbnail.getPath() + "." + thumbnail.getExtension();
        mHeaderImage.setImageURI(Uri.parse(thumbnailUrl));

        SpannableStringBuilder titleSpan = mPresenter.buildTitleSpan(
                getActivity(), comic.getTitle());
        mTvTitle.setText(titleSpan, TextView.BufferType.SPANNABLE);

        SpannableStringBuilder descriptionSpan = mPresenter.buildDescriptionSpan(
                getActivity(), comic.getDescription());
        mTvDescription.setText(descriptionSpan, TextView.BufferType.SPANNABLE);
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
        mPresenter.setState(DetailsPresenter.STATE_NORMAL);
        mPresenter.getComicDetails(mComicId);
    }
}
