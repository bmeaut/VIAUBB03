package hu.bme.aut.android.broadcastreceiverdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast


class AirPlaneReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val newState = intent.getBooleanExtra("state",false);
        if (newState) {
        Toast.makeText(context, "AIRPLANE MODE ENABLED!!!!",
                Toast.LENGTH_LONG).show()
        }
        else {
            Toast.makeText(context, "AIRPLANE MODE DISABLED!!!!",
                Toast.LENGTH_LONG).show()
        }
    }

}