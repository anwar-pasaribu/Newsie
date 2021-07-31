package com.anwar.test.detail.data

import com.anwar.test.utils.commons.AppConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Ahmed Atwa on 10/17/2019.
 */

interface CommentApi {


    companion object {

        const val GET_MOVIE_TRAILERS: String = ("movie/{movie_id}/videos")
    }

}