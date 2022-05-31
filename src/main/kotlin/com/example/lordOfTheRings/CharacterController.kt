package com.example.lordOfTheRings

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CharacterController {

    @GetMapping("getHumans")
    fun getHumans(): List<Doc>{
        val listaFiltrada = characterList.docs.filter {

            var encontrado = false

            if (it.race == "Human"){
                encontrado = true
            }
            encontrado
        }
        return listaFiltrada
    }

    //TODO: getElfs
    @GetMapping("getElfs")
    fun getElfs(): List<Doc>{
        val listaFiltrada = characterList.docs.filter {

            var encontrado = false

            if (it.race == "Elf" && it.gender == "female"){
                encontrado = true
            }
            encontrado
        }
        return listaFiltrada
    }

    //TODO: getOrcs
    @GetMapping("getOrcs")
    fun getOrcs(): List<Doc>{
        val listaFiltrada = characterList.docs.filter {

            var encontrado = false

            if (it.race == "Orc" || it.race == "Orcs"){
                encontrado = true
            }
            encontrado
        }
        return listaFiltrada
    }

    //TODO: getDragons
    @GetMapping("getDragons")
    fun getDragons(): List<Doc>{
        val listaFiltrada = characterList.docs.filter {

            var encontrado = false

            if (it.race == "Dragon" || it.race == "Dragons"){
                encontrado = true
            }
            encontrado
        }
        return listaFiltrada
    }

    //TODO: getGoodGuys (Elfs + Humans)
    @GetMapping("getGoodGuys")
    fun getGoodGuys(): List<Doc>{
        val listaFiltrada = characterList.docs.filter {

            var encontrado = false

            if (it.race == "Elf" || it.race == "Human"){
                encontrado = true
            }
            encontrado
        }
        return listaFiltrada
    }

    //TODO: getBadGuys (Orcs + Dragons)
    @GetMapping("getBadGuys")
    fun getBadGuys(): List<Doc>{
        val listaFiltrada = characterList.docs.filter {

            var encontrado = false

            if (it.race == "Orc" || it.race == "Orcs" || it.race == "Dragon" || it.race == "Dragons"){
                encontrado = true
            }
            encontrado
        }
        return listaFiltrada
    }

    //TODO: getOthers (no good guy, no bad guy)
    @GetMapping("getOthers")
    fun getOthers(): List<Doc>{
        val listaFiltrada = characterList.docs.filter {

            var encontrado = false

            if (it.race != "Orc" && it.race != "Dragon" && it.race != "Human" && it.race != "Elf" && it.race != "Orcs" && it.race != "Dragons"){
                encontrado = true
            }
            encontrado
        }
        return listaFiltrada
    }
}