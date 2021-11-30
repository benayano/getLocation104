package com.example.getlocation104

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient :FusedLocationProviderClient

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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager


        button.setOnClickListener {
            setLocation(tvLongitude, tvLatitude)
            getLastLocation()
        }

    }
    @SuppressLint("MissingPermission")
    private fun getLastLocation(){
        if (permissionsGranted()) {
            fusedLocationClient.lastLocation.addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    tvLatitude.text = task.result.longitude.toString()
                    tvLongitude.text = task.result.latitude.toString()
                }

            }
        }
    }


    @SuppressLint("MissingPermission")
    private fun setLocation(tvLongitude: TextView, tvLatitude: TextView) {
        if (permissionsGranted()) {
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let {

                Toast.makeText(
                    this,
                    "${it.longitude.toString()} \n${it.latitude.toString()}",
                    Toast.LENGTH_LONG
                ).show()

                tvLatitude.text = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)!!.latitude.toString()
            }

            tvLongitude.text = locationManager.allProviders.toString()

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

















