package com.learning.stepconunter

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), SensorEventListener {

    private var sensorManager : SensorManager? = null
    private var counting : Boolean = false
    private var steps : Int = 0

    lateinit var tvHeader : TextView
    lateinit var tvSteps : TextView
    lateinit var tvCounter : TextView
    lateinit var btnStart : Button
    lateinit var btnReset : Button
    lateinit var btnStop : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvCounter = findViewById(R.id.tvCounter)
        btnStart = findViewById(R.id.btnStart)
        btnReset = findViewById(R.id.btnReset)
        btnStop = findViewById(R.id.btnStop)
        tvSteps = findViewById(R.id.tvSteps)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        btnStart.setOnClickListener{
            counting = true
            Snackbar.make(
                findViewById(R.id.coorLayout),
                "Start",
                Snackbar.LENGTH_LONG
            ).show()
        }
        btnReset.setOnClickListener{
            steps = 0
            counting = false
            tvCounter.text = steps.toString()
            Snackbar.make(
                findViewById(R.id.coorLayout),
                "Reset",
                Snackbar.LENGTH_LONG
            ).show()
        }
        btnStop.setOnClickListener{
            counting = false
            Snackbar.make(
                findViewById(R.id.coorLayout),
                "Stopped",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    override fun onResume() {
        super.onResume()
        //counting = true
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor == null) {
            Snackbar.make(
                findViewById(R.id.coorLayout),
                "No Sensors",
                Snackbar.LENGTH_LONG
            ).show(

            )
        } else {
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (counting) {
            if (event != null) {
                steps = event?.values[0].toInt()
                tvCounter.text = steps.toString()
            } else {
                print("event")
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        println(accuracy)
    }
}