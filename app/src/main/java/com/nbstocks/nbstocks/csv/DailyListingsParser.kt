package com.nbstocks.nbstocks.csv

import com.nbstocks.nbstocks.data.remote.model.DailyStockDto
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DailyListingsParser @Inject constructor() : CSVParser<DailyStockDto> {
    override suspend fun parse(stream: InputStream): List<DailyStockDto> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0)
                    val open = line.getOrNull(1)
                    val high = line.getOrNull(2)
                    val low = line.getOrNull(3)
                    val close = line.getOrNull(4)
                    val volume = line.getOrNull(5)
                    DailyStockDto(
                        timestamp = timestamp ?: return@mapNotNull null,
                        open = open ?: return@mapNotNull null,
                        high = high ?: return@mapNotNull null,
                        low = low ?: return@mapNotNull null,
                        close = close ?: return@mapNotNull null,
                        volume = volume ?: return@mapNotNull null
                    )
                }.also {
                    csvReader.close()
                }
        }
    }
}