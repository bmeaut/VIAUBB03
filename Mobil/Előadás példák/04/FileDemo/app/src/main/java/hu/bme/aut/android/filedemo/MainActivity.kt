package hu.bme.aut.android.filedemo

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import hu.bme.aut.android.filedemo.databinding.ActivityMainBinding
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnWriteFile.setOnClickListener{
            writeFile("Hello! ${Date(System.currentTimeMillis()).toString()}");
        }

        binding.btnReadFile.setOnClickListener{
            binding.tvData.text = readFile()
        }

        binding.btnReadFile.isEnabled = false
        binding.btnWriteFile.isEnabled = false

        requestNeededPermission()
    }

    fun requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this,
                        "I need it for file", Toast.LENGTH_SHORT).show()
            }

            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    101)
        } else {
            // már van engedély
            binding.btnReadFile.isEnabled = true
            binding.btnWriteFile.isEnabled = true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            101 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "WRITE_EXTERNAL_STORAGE perm granted", Toast.LENGTH_SHORT).show()

                    binding.btnReadFile.isEnabled = true
                    binding.btnWriteFile.isEnabled = true

                } else {
                    Toast.makeText(this,
                            "WRITE_EXTERNAL_STORAGE perm NOT granted", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    private fun writeFile(data: String) {
        val file = "${Environment.getExternalStorageDirectory()}/test.txt"

        Toast.makeText(this, file, Toast.LENGTH_LONG).show()

        lateinit var outputStream: FileOutputStream
        try {
            outputStream = FileOutputStream(file)
            outputStream.write(data.toByteArray())
            outputStream.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                outputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun readFile(): String {
        var result = ""

        val file = "${Environment.getExternalStorageDirectory()}/test.txt"
        lateinit var inputStream: FileInputStream
        try {
            inputStream = FileInputStream(file)
            result = inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return result
    }
}
