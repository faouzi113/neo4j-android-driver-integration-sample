package com.faouzibidi.neo4j.driver.sample

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import org.neo4j.driver.v1.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun addPerson(fname: String, lname: String) {
        lateinit var driver: Driver
        try {
            driver = GraphDatabase.driver("bolt://192.168.1.87:7687", AuthTokens.basic("neo4j", "password"))
            val st = Statement("CREATE (p:Person{firstname:\$fname,lastname:\$lname})",
                    Values.parameters("fname", fname, "lname", lname))
            driver.session().run(st)
        } catch (e: Exception) {
            Log.d("NEO4J", "addPerson: ", e)
        } finally {
            driver.close()
        }
    }

    fun register(view: View?) {
        val firstname = (findViewById<View>(R.id.firstName) as EditText).editableText.toString()
        val lastname = (findViewById<View>(R.id.lastName) as EditText).editableText.toString()
        Thread(Runnable { addPerson(firstname, lastname) }).start()
    }
}