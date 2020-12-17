package com.bank.application.utils

import org.springframework.util.ResourceUtils
import kotlin.streams.toList

/**
 * Set of methods to operate on csv files.
 */
object CSVCollectionFileReader {

    /**
     * It reads csv file and returns list of (maps) with records.
     * @param fileName name of file in initialData directory.
     *
     * @return Collection of Maps of records from file where first line is the map keys and rest of values
     */
    fun extractCollectionDataFromFile(fileName: String): List<Map<String,String>> {
        val fileResource = ResourceUtils.getFile("classpath:initialData/$fileName")

        return fileResource
                .bufferedReader().use { bufferedReader ->
            val paramNames = bufferedReader.readLine().split(",")
            bufferedReader.lines()
                    .map { it.replace("\"", "").split(",") }
                    .map { paramNames.zip(it).toMap() }
                    .toList()
        }
    }
}