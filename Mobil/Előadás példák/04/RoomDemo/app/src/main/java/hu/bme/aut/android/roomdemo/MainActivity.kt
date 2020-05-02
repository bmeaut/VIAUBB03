package hu.bme.aut.android.roomdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.roomdemo.data.AppDatabase
import hu.bme.aut.android.roomdemo.data.Grade
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSubmit.setOnClickListener {
            val grade = Grade(null, etStudentId.text.toString(),
                    etGrade.text.toString())

            val dbThread = Thread {
                AppDatabase.getInstance(this@MainActivity).gradeDao().insertGrades(grade)
            }
            dbThread.start()
        }

        btnSearch.setOnClickListener {
            val dbThread = Thread {
                val grades = AppDatabase.getInstance(this@MainActivity).gradeDao()
                        .getSpecificGrades("A+")
                runOnUiThread {
                    tvResult.text = ""
                    grades.forEach {
                        tvResult.append("${it.studentId} ${it.grade}\n")
                    }
                }
            }
            dbThread.start()
        }
    }
}
