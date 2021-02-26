package com.example.coroutinesexample

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext

class MyTask(val actividad: MainActivity, val tiempo: Double) {

    suspend fun execute(count : Int) = withContext(Dispatchers.IO){
        try {
            var index = 1
            var numAux = 1
            while (index <= count) {
                Thread.sleep((2000 * tiempo).toLong())
                numAux *= index
                actividad.actualizacion(numAux)
                index++
            }
        }
        catch (e:CancellationException){
            actividad.finTarea(e.message!!)
        }
        finally {
            if (isActive){
                actividad.finTarea("Tarea finalizada")
            }
        }
    }


}