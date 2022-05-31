package com.example.lordOfTheRings

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UsuarioController (private  val usuarioRepository: UsuarioRepository) {

    @PostMapping("registrarUsuario")
    @Synchronized
    fun requestRegistroUsuario(@RequestBody usuario: Usuario): Any {

        val userOpcional = usuarioRepository.findById(usuario.nombre)

        return if (userOpcional.isPresent) {

            val user = userOpcional.get()

            if (user.pass == usuario.pass) {
                user
            } else {
                "Contraseña incorrecta"
            }

        } else {
            usuarioRepository.save(usuario)
            usuario
        }
    }

    @PostMapping("asignarEquipo/{token}")
    fun asignarEquipo(@PathVariable token: String): Any {

        usuarioRepository.findAll().forEach { user ->

            if (user.token == token) {
                for(i in 1 .. 6){

                    var perRandom = characterList.docs.random()
                    user.personList.add(perRandom.name)
                    usuarioRepository.save(user)
                }
                return user.personList
            }
        }
        return "TOKEN NO ENCONTRADO"
    }
}