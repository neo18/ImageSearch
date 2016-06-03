package com.naveen.android.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Naveen Rawat on 01-06-2016.
 */
public interface NetworkTask {

    @GET("/w/api.php?action=query&prop=pageimages&format=json&piprop=thumbnail&pilimit=50&generator=prefixsearch")
    Call<String> getDetails(@Query("pithumbsize") int size, @Query("gpssearch") String searchText);
}
