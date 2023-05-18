package com.example.compass

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private var currentDegree: Float = 0f
    private lateinit var sensorManager: SensorManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        imageView = findViewById(R.id.degrees)
        textView = findViewById(R.id.textView)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        val degree: Int = p0?.values?.get(0)?.toInt()!!
        textView.text = degree.toString()
        val rotationAnim = RotateAnimation(
            currentDegree,
            (-degree).toFloat(),
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotationAnim.duration = 210
        rotationAnim.fillAfter = true
        currentDegree = (-degree).toFloat()
        imageView.startAnimation(rotationAnim)

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}