package com.example.coroutinesexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.EditText
import com.example.coroutinesexample.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var stringBuilder= StringBuilder()
    private lateinit var task1:MyTask
    private lateinit var job1:Job
    lateinit var numero: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)
        numero = findViewById(R.id.editTextTextNumero)

        binding.statusText?.movementMethod= ScrollingMovementMethod()
        stringBuilder = StringBuilder("Empezando actividad\n")
        stringBuilder.append("Esperando click.\n")
        binding.statusText.text = "${stringBuilder.toString()}"



        task1 = MyTask(this,  1.0)


        binding.btnAsync.setOnClickListener {
            binding.progressBar.max = numero.text.toString().toInt()
            startTasks(numero.text.toString().toInt())
        }
    }

    fun startTasks(num: Int){
        job1 = MainScope().launch {
            task1.execute(num)
        }
    }

    suspend fun actualizacion(valor:Int) = withContext(Dispatchers.Main){
        stringBuilder.append("Tarea: Factorial. Resultado actual: ${valor}\n")
        binding.statusText.text = "${stringBuilder.toString()}"
        binding.progressBar.progress = binding.progressBar.progress + 1
    }

    suspend fun finTarea(mensaje:String) = withContext(Dispatchers.Main){
        stringBuilder.append("Tarea: Factorial.  ${mensaje}\n")
        binding.statusText.text = "${stringBuilder.toString()}"
        binding.progressBar.progress = 0
    }
}