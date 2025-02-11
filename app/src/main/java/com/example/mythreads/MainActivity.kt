package com.example.mythreads

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withCreated
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.logging.Handler

class MainActivity : AppCompatActivity() {
    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart = findViewById<Button>(R.id.btn_start)
        val tvStatus = findViewById<TextView>(R.id.tv_status)

        val executor = Executors.newSingleThreadExecutor()
        val handler = android.os.Handler(Looper.getMainLooper())

        btnStart.setOnClickListener{
            //menggunakan thread, handler, dan executor
            /*executor.execute{
                try{
                    //simulasi proses compress
                    for(i in 0..10){
                        Thread.sleep(500)
                        val percentage = i * 10

                        handler.post{
                            if(percentage == 100){
                                tvStatus.setText(R.string.tesk_completed)
                            }else{
                                tvStatus.text = String.format(getString(R.string.compressing), percentage)
                            }
                        }
                    }
                }catch (e: InterruptedException){
                    e.printStackTrace()
                }
                }
                */
            //menggunakan coroutine
            lifecycleScope.launch(Dispatchers.Default) {
                for (i in 0..10) {
                    delay(500)
                    val percentage = i * 10
                    withContext(Dispatchers.Main) {
                        if (percentage == 100) {
                            tvStatus.setText(R.string.tesk_completed)
                        } else {
                            tvStatus.text =
                                String.format(getString(R.string.compressing), percentage)
                        }
                    }
                }
            }
        }
    }
}