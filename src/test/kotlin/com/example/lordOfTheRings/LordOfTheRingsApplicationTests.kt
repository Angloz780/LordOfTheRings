package com.example.lordOfTheRings

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.File

@SpringBootApplication
class ServetApplication

fun main(args: Array<String>) {
	runApplication<ServetApplication>(*args)

	val filePath = "character.json"
	val characterList = Json.decodeFromString<CharacterList>(File(filePath).readText())

	characterList.docs.forEach{
		println(it.name)
	}
}
