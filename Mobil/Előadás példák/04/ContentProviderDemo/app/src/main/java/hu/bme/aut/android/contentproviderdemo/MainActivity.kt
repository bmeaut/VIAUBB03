package hu.bme.aut.android.contentproviderdemo

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import hu.bme.aut.android.contentproviderdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding

    companion object {
        val KEY_LOG = "LOG_PROVIDER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        requestNeededPermission()
    }

    private fun initUI() {
        binding.btnGet.setOnClickListener {
            val cursorContacts = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER),
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " LIKE '%Tamás%'",
                    //null,
                    null,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " DESC")

            //Toast.makeText(MainActivity.this, ""+c.getCount(), Toast.LENGTH_LONG).show();

            if (cursorContacts != null) {
                while (cursorContacts.moveToNext()) {
                    val name = cursorContacts.getString(cursorContacts.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    Log.d(KEY_LOG, name)
                    Toast.makeText(this@MainActivity, name, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    101)
        } else {
            // we are ok
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            101 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this@MainActivity, "Permissions granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity,
                            "Permissions are NOT granted", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }
}
