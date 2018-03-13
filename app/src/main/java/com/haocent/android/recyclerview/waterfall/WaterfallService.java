package com.haocent.android.recyclerview.waterfall;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Tnno Wu on 2018/03/13.
 */

public interface WaterfallService {

    /**
     * 福利
     *
     * e.g. http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1
     */
    @GET("{type}/{count}/{page}")
    Observable<WaterfallBean> getWaterfallData(@Path("type") String type,
                                               @Path("count") int count,
                                               @Path("page") int page);
}
