package ru.megboyzz.vk.audionotes.repository

import ru.megboyzz.vk.audionotes.entities.Recording
import ru.megboyzz.vk.audionotes.entities.convertFilesToRecordings
import java.io.File

class HomeRepository {

    fun getRecordings(directory: String): List<Recording> {
        val file = File(directory)
        return convertFilesToRecordings(file.listFiles()?.toList() ?: listOf())
    }

}