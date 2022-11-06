package com.nbstocks.nbstocks.common.constants

import com.nbstocks.nbstocks.BuildConfig

object ApiServiceHelpers {

    object YahooFinanceService {
        const val BASE_URL = "https://query1.finance.yahoo.com/"
        const val PATH_QUOTE_SUMMARY = "v11/finance/quoteSummary/{symbol}"
        const val PATH_CHART = "v8/finance/chart/{symbol}"
        const val PATH_QUOTE = "v7/finance/quote"

        object ServiceFunctions {
            const val FINANCIAL_DATA = "financialData"
        }
        object ServiceMetrics{
            const val HIGH = "high"
        }
        object ServiceTimestamps{
            const val DAY1 = "1d"
            const val MONTH1 = "1mo"
            const val YEAR1 = "1y"
        }
    }

    object RapidApiService {
        const val BASE_URL = "https://twelve-data1.p.rapidapi.com/"
        const val RAPID_API_KEY = BuildConfig.RAPID_API_KEY
        const val PATH_QUOTE = "stocks"
    }
}