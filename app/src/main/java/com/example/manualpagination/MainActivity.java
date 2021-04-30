package com.example.manualpagination;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manualpagination.network.Api;
import com.example.manualpagination.network.RetrofitInstance;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    boolean userScrolled = true;
    int pastVisibleItems = 0;
    int visibleItemCount = 0;
    int totalItemCount = 0;
    String query = "";
    private Api api;
    int page = 1;
    private ReposAdapter reposAdapter;
    private RecyclerView reposRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private EditText searchEditText;
    private ImageButton searchImageButton;
    private ProgressBar progressBar;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        api = RetrofitInstance.getRetrofitInstance().create(Api.class);
        initViews();

    }

    private void initViews(){
        progressBar = findViewById(R.id.progressBar);
        searchEditText = findViewById(R.id.searchEditText);
        //Search button
        searchImageButton = findViewById(R.id.searchImageButton);
        searchImageButton.setOnClickListener(v -> {
            query = searchEditText.getText().toString();
            if (reposAdapter != null){
                reposAdapter.items.clear();
                reposAdapter.notifyDataSetChanged();
            }
            if (!query.isEmpty()){
                getReposByName(query);
            }
        });

        //RecyclerView
        linearLayoutManager = new LinearLayoutManager(this);
        reposRecyclerView = findViewById(R.id.repositoriesRecyclerView);
        reposRecyclerView.setLayoutManager(linearLayoutManager);
        reposRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        reposRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@androidx.annotation.NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;
                }
            }

            @Override
            public void onScrolled(@androidx.annotation.NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

                if (userScrolled && visibleItemCount + pastVisibleItems == totalItemCount) {
                    userScrolled = false;
                    getReposByName(query);
                }
            }
        });
    }

    private void getReposByName(String repoName) {
        progressBar.setVisibility(View.VISIBLE);
        Observable<Repository> reposObservable = api.getReposByName(repoName, page++, 10);
        reposObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Repository>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {}

                    @Override
                    public void onNext(@NonNull Repository repository) {
                        //this is for not loading empty pages over and over
                        if (repository.getItems().isEmpty()){
                            reposRecyclerView.clearOnScrollListeners();
                        }

                        if (reposAdapter == null) {
                            reposAdapter = new ReposAdapter(repository.getItems());
                            reposRecyclerView.setAdapter(reposAdapter);
                        } else {
                            reposAdapter.items.addAll(repository.getItems());
                            reposAdapter.notifyDataSetChanged();
                        }
                        progressBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}