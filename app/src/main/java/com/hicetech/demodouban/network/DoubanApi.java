package com.hicetech.demodouban.network;


import com.hicetech.demodouban.movie.module.Celebrity;
import com.hicetech.demodouban.movie.module.Movie;
import com.hicetech.demodouban.movie.module.Moviedetail;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by Administrator on 2017/6/24.
 */

public interface DoubanApi {
    @GET("/v2/movie/top250")
    Observable<Movie> getMovie(@Query("start") int start, @Query("count") int count);

    @GET("/v2/movie/subject/{id}")
    Observable<Moviedetail> getMovieDetail(@Path("id") String id);

    @GET("/v2/movie/celebrity/{id}")
    Observable<Celebrity> getCelebrity(@Path("id") String id);

}
