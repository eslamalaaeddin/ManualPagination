package com.example.manualpagination.network;
import com.example.manualpagination.Repository;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("search/repositories")
    Observable<Repository> getReposByName(
            @Query("q") String query,
            @Query("page") int page,
            @Query("per_page") int perPage
    );

}
