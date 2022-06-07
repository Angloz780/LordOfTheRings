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
                while (user.personList.size != 6) {
                    val personajesRandom = characterList.docs.random()

                    if (!personajesRandom.seleccion) {
                        user.personList.add(personajesRandom.id)
                        personajesRandom.seleccion = true
                        usuarioRepository.save(user)
                    }
                }
                return user.personList
            }
        }
        return "Token inválido "
    }

    @PostMapping("liberarPersona/{token}/{id}")
    fun liberarPersona(@PathVariable token: String, @PathVariable id: String): Any {
        usuarioRepository.findAll().forEach { user ->
            if (user.token == token) {
                user.personList.forEach {
                    if (it == id) {
                        user.personList.remove(it)
                        usuarioRepository.save(user)
                        return "Personaje liberado"
                    }
                }
                return "El personaje no pertence al equipo"
            }
        }
        return "Token no valido"
    }
}