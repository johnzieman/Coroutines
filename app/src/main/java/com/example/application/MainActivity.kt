package com.example.application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.application.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.button.setOnClickListener {
            CoroutineScope(IO).launch {
                fakeApiRequest()


            }
        }
    }

    private fun setNewText(input: String){
        val newText = binding.textView.text.toString() + "\n$input"
        binding.textView.text = newText
    }

    private suspend fun setTextOnMainThread(input: String){
        withContext(Main){
            setNewText(input)
        }
    }

    private suspend fun fakeApiRequest(){
        val result1 = getResult1FromApi()
        setTextOnMainThread("result 1")

        val result2 = getResult1FromApi()
        setTextOnMainThread("result 2")
    }

    private suspend fun getResult1FromApi(): String{
        logName("getResult1FromName")
        delay(1000)
        return "result 1"
    }

    private suspend fun getResult2FromApi(): String{
        logName("getResult2FromApi")
        delay(1000)
        return "result 2"
    }

    private suspend fun logName(methodName: String){
        println("debug: ${methodName}: ${Thread.currentThread().name}")
    }
}