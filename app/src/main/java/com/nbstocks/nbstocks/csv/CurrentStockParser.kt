package com.nbstocks.nbstocks.csv

import com.nbstocks.nbstocks.data.remote.model.CurrentStockDto
import com.nbstocks.nbstocks.data.remote.model.StockPricesDto
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

class CurrentStockParser @Inject constructor(): CSVParser<CurrentStockDto> {
    override suspend fun parse(stream: InputStream): List<CurrentStockDto> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val symbol = line.getOrNull(0)
                    val open = line.getOrNull(1)
                    val high = line.getOrNull(2)
                    val low = line.getOrNull(3)
                    val price = line.getOrNull(4)
                    val volume = line.getOrNull(5)
                    val latestDay = line.getOrNull(6)
                    val previousClose = line.getOrNull(7)
                    val change = line.getOrNull(8)
                    val changePercent = line.getOrNull(9)
                    CurrentStockDto(
                        symbol = symbol ?: return@mapNotNull null,
                        open = open ?: return@mapNotNull null,
                        high = high ?: return@mapNotNull null,
                        low = low ?: return@mapNotNull null,
                        price = price ?: return@mapNotNull null,
                        volume = volume ?: return@mapNotNull null,
                        latestDay = latestDay ?: return@mapNotNull null,
                        previousClose = previousClose ?: return@mapNotNull null,
                        change = change ?: return@mapNotNull null,
                        changePercent = changePercent ?: return@mapNotNull null
                    )
                }.also {
                    csvReader.close()
                }
        }
    }
}