package com.example.lordOfTheRings

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

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

    @GetMapping("adentrarnseEnMazmorra/{token}/{dificultad}")
    fun adentrarnseEnMazmorra(@PathVariable token: String, @PathVariable dificultad: String): Any{

        var prob: Int
        var lista = mutableListOf<Doc>()

        usuarioRepository.findAll().forEach{ usuario ->
            if(usuario.token == token){
                if (usuario.personList.size > 1){
                    usuario.elegirDif(dificultad)
                    when(usuario.selecDif){
                        Usuario.DifDungeon.Facil ->{
                            if (usuario.ganadaFacil)
                                return "Dungeon ya superada"
                            else{
                                for (i in 1 .. usuario.personList.size){
                                    prob = Random.nextInt(1, 6)
                                    if (prob == 1){
                                        usuarioRepository.findAll().forEach { usuario ->
                                            if (usuario.token == token){
                                                usuario.personList.forEach {
                                                    characterList.docs.forEach { personaje ->
                                                        if (it == personaje.id)
                                                            personaje.seleccion = false
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (usuario.personList.size < 1)
                                    return "Equipo muerto"
                                else{
                                    usuario.ganadaFacil = true

                                    usuarioRepository.findAll().forEach { usuario ->
                                        if (usuario.token == token){
                                            usuario.personList.forEach {
                                                characterList.docs.forEach { personaje ->
                                                    if(it == personaje.id)
                                                        lista.add(personaje)
                                                }
                                            }
                                        }
                                    }
                                    if (usuario.ganadaMedio && usuario.ganadaDificil){
                                        usuario.sizeTeam = usuario.sizeTeam + 1
                                        asignarEquipo(token)
                                    }
                                    return "Has ganado la dungeon de nivel Facil $lista"
                                }
                            }
                        }
                        Usuario.DifDungeon.Medio ->{
                            if (usuario.ganadaMedio)
                                return "Dungeon ya superada"
                            else{
                                for (i in 1 .. usuario.personList.size){
                                    prob = Random.nextInt(1, 2)
                                    if(prob == 1){
                                        usuarioRepository.findAll().forEach { usuario ->
                                            if (usuario.token == token){
                                                usuario.personList.forEach {
                                                    characterList.docs.forEach { personaje ->
                                                        if (it == personaje.id)
                                                            personaje.seleccion = false
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (usuario.personList.size < 1)
                                    return "Equipo muerto"
                                else{
                                    usuario.ganadaMedio = true

                                    usuarioRepository.findAll().forEach { usuario ->
                                        if(usuario.token == token){
                                            usuario.personList.forEach {
                                                characterList.docs.forEach { personaje ->
                                                    if (it == personaje.id)
                                                        lista.add(personaje)
                                                }
                                            }
                                        }
                                    }
                                    if (usuario.ganadaFacil && usuario.ganadaDificil){
                                        usuario.sizeTeam = usuario.sizeTeam + 1
                                        asignarEquipo(token)
                                    }
                                    return "Has ganado la dungeon de nivel Medio $lista"
                                }
                            }
                        }
                        Usuario.DifDungeon.Dificil ->{
                            if (usuario.ganadaDificil)
                                return "Dungeon superada"
                            else{
                                for(i in 1 .. usuario.personList.size){
                                    prob = Random.nextInt(1, 4)
                                    if (prob == 1 || prob == 2 || prob == 3){
                                        usuarioRepository.findAll().forEach { usuario ->
                                            if (usuario.token == token){
                                                usuario.personList.forEach {
                                                    characterList.docs.forEach { personaje ->
                                                        if (it == personaje.id)
                                                            personaje.seleccion = false
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (usuario.personList.size < 1)
                                    return "Equipo muerto"
                                else{
                                    usuario.ganadaDificil = true

                                    usuarioRepository.findAll().forEach { usuario ->
                                        if (usuario.token == token){
                                            usuario.personList.forEach {
                                                characterList.docs.forEach { personaje ->
                                                    if(it == personaje.id)
                                                        lista.add(personaje)
                                                }
                                            }
                                        }
                                    }
                                    if (usuario.ganadaFacil && usuario.ganadaMedio){
                                        usuario.sizeTeam = usuario.sizeTeam + 1
                                        asignarEquipo(token)
                                    }
                                    return "Has ganado la dungeon de nivel Dificil $lista"
                                }
                            }
                        }
                    }
                }else{
                    return "Equipo muerto"
                }
            }
        }
        return "Token no valido"
    }
}