package com.example.getlocation104

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    private val tvLongitude: TextView by lazy { findViewById(R.id.tvLongitude) }
    private val tvLatitude: TextView by lazy { findViewById(R.id.tvLatitude) }
    private val button: Button by lazy { findViewById(R.id.button) }

    private lateinit var locationManager: LocationManager

    private val permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    companion object {
        const val PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager



        button.setOnClickListener {
            setLocation(tvLongitude, tvLatitude)
        }

    }

    @SuppressLint("MissingPermission")
    private fun setLocation(tvLongitude: TextView, tvLatitude: TextView) {
        if (permissionsGranted()) {
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let {

                tvLongitude.text = it.longitude.toString()
                tvLatitude.text = it.latitude.toString()
                Toast.makeText(this, "${it.longitude.toString()} \n${it.latitude.toString()}", Toast.LENGTH_LONG).show()
            }
            Toast.makeText(this, "יש הרשאה למיקום???????", Toast.LENGTH_LONG).show()

        } else {
            Toast.makeText(this, "אין גישה למיקום מצטער!!!!!!!", Toast.LENGTH_LONG).show()
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() =
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE)


    private fun permissionsGranted(): Boolean = (ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
            )

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            resultLocationPermission(grantResults)
        }

    }

    private fun resultLocationPermission(grantResults: IntArray) {
        if (grantResults.isEmpty() || grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

   
}

















