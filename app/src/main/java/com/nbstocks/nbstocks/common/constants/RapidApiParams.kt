package com.nbstocks.nbstocks.common.constants

import com.nbstocks.nbstocks.BuildConfig


object RapidApiParams {
    const val ALPHA_VANTAGE_RAPID_API_HOST = "alpha-vantage"
    const val TWELVE_DATA_RAPID_API_HOST = "twelve-data1"
    private const val RAPID_API_KEY = BuildConfig.RAPID_API_KEY

    fun getRapidApiBaseUrl(host: String): String = "https://$host.p.rapidapi.com/"
    fun getRapidApiKey(): String = RAPID_API_KEY
}