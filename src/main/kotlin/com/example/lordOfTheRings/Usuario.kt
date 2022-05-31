package com.example.lordOfTheRings

import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Usuario (@Id var nombre : String, var pass : String) {

    var token = nombre + pass

    @ElementCollection
    var personList = mutableListOf<String>()
}