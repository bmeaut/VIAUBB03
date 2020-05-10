package hu.aut.android.broadcastreceiverdemo

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    val airplaneReceiver = AirPlaneReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestNeededPermission()
    }

    override fun onStart() {
        super.onStart()

        registerReceiver(
            airplaneReceiver,
            IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        )

    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(airplaneReceiver)
    }

    private fun requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECEIVE_SMS
            ) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.PROCESS_OUTGOING_CALLS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.RECEIVE_SMS
                )
            ) {
                AlertDialog.Builder(this)
                    .setTitle("Engedély")
                    .setMessage("I need it")
                    .setPositiveButton("OK") { dialogInterface: DialogInterface, i: Int ->
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(
                                Manifest.permission.RECEIVE_SMS,
                                Manifest.permission.PROCESS_OUTGOING_CALLS
                            ),
                            101
                        )
                    }
                    .setNegativeButton("mégse") { dialogInterface: DialogInterface, i: Int ->
                        Toast.makeText(this, "Cancel", Toast.LENGTH_LONG).show()
                        finish()
                    }
                    .show()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.PROCESS_OUTGOING_CALLS
                    ),
                    101
                )
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            101 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(
                        this@MainActivity,
                        "RECEIVE_SMS and call perm granted",
                        Toast.LENGTH_SHORT
                    ).show()


                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "RECEIVE_SMS and call perm NOT granted", Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
                return
            }
        }
    }
}
