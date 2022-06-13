package com.banerjeec713.githubassignment.ui.home;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.banerjeec713.githubassignment.App;
import com.banerjeec713.githubassignment.R;
import com.banerjeec713.githubassignment.data.DataManager;
import com.banerjeec713.githubassignment.data.models.TrendingItemModel;
import com.banerjeec713.githubassignment.ui.base.BaseFragment;
import com.banerjeec713.githubassignment.utils.Constants;
import com.banerjeec713.githubassignment.utils.Util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;


public class MainFragment extends BaseFragment<MainViewModel> implements SwipeRefreshLayout.OnRefreshListener {
    private View mView;
    private MainAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MainViewModel viewModel;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private static final ArrayList<TrendingItemModel> trendingItemModels = new ArrayList();

    public static MainFragment getInstance() {
        return new MainFragment();
    }

    @NonNull
    @Override
    public MainViewModel getViewModel() {
        Log.d(Constants.TAG, "getViewModel: ");
        MainViewModelFactory factory = new MainViewModelFactory(DataManager.Companion.getInstance(App.Companion.getInstance()));
        return new ViewModelProvider(requireActivity(), factory).get(MainViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(Constants.TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        viewModel = getViewModel();
    }

    @Override
    public void onPause() {
        Log.d(Constants.TAG, "onPause: ");
        super.onPause();
        Parcelable state = Objects.requireNonNull(mRecyclerView.getLayoutManager()).onSaveInstanceState();
        assert state != null;
        viewModel.saveRecyclerViewState(state);
    }

    @Override
    public void onResume() {
        Log.d(Constants.TAG, "onResume: ");
        super.onResume();
        if (viewModel.stateInitialized()) {
            Objects.requireNonNull(mRecyclerView.getLayoutManager()).onRestoreInstanceState(
                    viewModel.restoreRecyclerViewState()
            );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(Constants.TAG, "onCreateView: ");
        mView = inflater.inflate(R.layout.main_fragment, container, false);
        mSwipeRefreshLayout = mView.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = mView.findViewById(R.id.recyclerView);

        initView();
        setupRecycler();

        return mView;
    }

    @Override
    public void onStart() {
        Log.d(Constants.TAG, "onStart: "+viewModel.stateInitialized());
        super.onStart();
        viewModel.getRepos().observe(this, itemModels -> {
            trendingItemModels.clear();
            trendingItemModels.addAll(itemModels);
            mAdapter.addData(itemModels);
            updateRefreshLayout(false);
        });
        viewModel.getError().observe(this, isError -> {
            if (isError) {
                displaySnackbar(true, "Can't load more github repos");
                updateRefreshLayout(false);
            }
        });

        updateRefreshLayout(true);
        displaySnackbar(false, "Loading...");
        viewModel.loadRepos();

    }

    private void initView() {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void setupRecycler() {
        mLayoutManager = new LinearLayoutManager(App.Companion.getInstance());
        mAdapter = new MainAdapter();
        mRecyclerView.setLayoutManager(mLayoutManager);
        int scrollPosition = 0;
        mRecyclerView.scrollToPosition(scrollPosition);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onRefresh() {
        Log.d(Constants.TAG, "onRefresh: ");
        Constants.PAGE_COUNT = 1;
        mAdapter.clearData();

        if (Util.INSTANCE.isNetworkAvailable(App.Companion.getInstance())) {
            showError(View.GONE);
            displaySnackbar(false, "Loading...");
            viewModel.loadRepos();
        } else {
            updateRefreshLayout(false);
            showError(View.VISIBLE);
            displaySnackbar(true, "No Internet Connection :(");
        }
    }

    private void updateRefreshLayout(boolean refresh) {
        Log.d(Constants.TAG, "updateRefreshLayout: ");
        mSwipeRefreshLayout.setRefreshing(refresh);
    }

    private void showError(int Visibility) {
        Log.d(Constants.TAG, "showError: ");
        getActivity().findViewById(R.id.main_layout).findViewById(R.id.imgview).setVisibility(Visibility);
    }

    private void displaySnackbar(boolean isError, String message) {
        Log.d(Constants.TAG, "displaySnackbar: ");
        Util.INSTANCE.showSnack(mView, isError, message);
    }

    @Override
    public void onDestroy() {
        Log.d(Constants.TAG, "onDestroy: ");
        super.onDestroy();
        viewModel.onClear();
    }

    ArrayList<TrendingItemModel> list = new ArrayList<>();
    public void filter(@NotNull String name) {
        Log.d(Constants.TAG, "search: "+name);
        list.clear();
        for(int i = 0; i < trendingItemModels.size(); i++){
            TrendingItemModel item = trendingItemModels.get(i);
            if(item.getName().contains(name)){
                list.add(item);
            }
        }
        mAdapter.filterList(list);
    }

    public void reset() {
        list.clear();
        mAdapter.resetList(trendingItemModels);
    }
}
