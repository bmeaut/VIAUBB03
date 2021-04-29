package hu.bme.aut.android.preferenceframeworkdemo



import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.preferenceframeworkdemo.databinding.ActivityMainBinding
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        Toast.makeText(this, getLastStartTime(), Toast.LENGTH_LONG).show()

        if (isFirstStart()) {
            MaterialTapTargetPrompt.Builder(this)
                .setTarget(R.id.fab)
                .setPrimaryText("TUTORIAL")
                .setSecondaryText("Kattints ide")
                .show()
        }

        saveLastStartTime()
    }

    fun saveLastStartTime() {
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        val editor: SharedPreferences.Editor = sp.edit()
        editor.putString("KEY_LAST_START", Date(System.currentTimeMillis()).toString())
        editor.putBoolean("KEY_STARTED", true)
        editor.apply()
    }

    fun getLastStartTime() : String {
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        return sp.getString("KEY_LAST_START", "This is the first time")!!
    }

    fun isFirstStart() : Boolean {
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        return !sp.getBoolean("KEY_STARTED", false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item?.getItemId()
        if (id == R.id.action_settings) {
            // itt fogjuk meghívni a saját SettingsActivity-nket
            val i = Intent(this, SettingsActivity::class.java)
            i.putExtra(":android:no_headers", true)
            i.putExtra(":android:show_fragment", SettingsActivity.FragmentSettingsBasic::class.java.getName())
            startActivity(i)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
