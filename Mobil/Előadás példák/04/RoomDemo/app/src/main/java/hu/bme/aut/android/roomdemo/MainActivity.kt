package hu.bme.aut.android.roomdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.roomdemo.data.AppDatabase
import hu.bme.aut.android.roomdemo.data.Grade
import hu.bme.aut.android.roomdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSubmit.setOnClickListener {
            val grade = Grade(null, binding.etStudentId.text.toString(),
                binding.etGrade.text.toString())

            val dbThread = Thread {
                AppDatabase.getInstance(this@MainActivity).gradeDao().insertGrades(grade)
            }
            dbThread.start()
        }

        binding.btnSearch.setOnClickListener {
            val dbThread = Thread {
                val grades = AppDatabase.getInstance(this@MainActivity).gradeDao()
                        .getSpecificGrades("A+")
                runOnUiThread {
                    binding.tvResult.text = ""
                    grades.forEach {
                        binding.tvResult.append("${it.studentId} ${it.grade}\n")
                    }
                }
            }
            dbThread.start()
        }
    }
}
