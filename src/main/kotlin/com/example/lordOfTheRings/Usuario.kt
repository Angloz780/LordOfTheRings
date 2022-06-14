package com.example.lordOfTheRings

import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Usuario (@Id var nombre : String, var pass : String) {

    var token = nombre + pass

    @ElementCollection
    var personList = mutableListOf<String>()

    var ganadaFacil: Boolean = false
    var ganadaMedio: Boolean = false
    var ganadaDificil: Boolean = false

    var sizeTeam: Int = 6
    var selecDif = DifDungeon.Facil

    enum class DifDungeon(){
        Facil,
        Medio,
        Dificil
    }

    fun elegirDif(dif: String){
        when(dif){
            "Facil" -> selecDif = DifDungeon.Facil
            "Medio" -> selecDif = DifDungeon.Medio
            "Dificil" -> selecDif = DifDungeon.Dificil
            else ->
                println("Nivel de dificultad mal escrita")
        }
    }
}