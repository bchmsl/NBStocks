package com.nbstocks.nbstocks.csv

import java.io.InputStream

interface  CSVParser<T> {
    suspend fun parse(stream:InputStream): List<T>
}