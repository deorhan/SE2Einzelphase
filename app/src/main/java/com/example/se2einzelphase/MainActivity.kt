package com.example.se2einzelphase

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.w3c.dom.Text
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val address: String = "se2-isys.aau.at"
    private val port: Int = 53212

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var textErweiterung = findViewById<TextView>(R.id.textViewErweiterung)
        val textMatrikelnummer = findViewById<EditText>(R.id.editMatrikelnummer)
        val textGGT = findViewById<TextView>(R.id.textViewGGT)
        findViewById<Button>(R.id.buttonSend).setOnClickListener{
            if(textMatrikelnummer.text.toString() != "12119264"){
                textErweiterung.text = "Falsche Matrikelnummer!"
                textErweiterung.setTextColor(Color.RED)
            } else {
                val ggtIndex = findGGT(textMatrikelnummer.text.toString(), textGGT)
                textErweiterung.text = ggtIndex
                textErweiterung.setTextColor(Color.BLACK)
            }
        }
    }

    private fun findGGT(matrikelnummer: String, textGGT: TextView): String {
        val intList = matrikelnummer.map { it.digitToInt() }
        val randomIndexA = Random.nextInt(intList.size)
        var randomIndexB = Random.nextInt(intList.size)
        while(randomIndexB == randomIndexA){
            randomIndexB = Random.nextInt(intList.size)
        }
        val a = intList[randomIndexA]
        val b = intList[randomIndexB]

        val value  = ggt(a, b)
        if(value != "1"){
            textGGT.text = "GGT: " + value
        } else {
            textGGT.text = "Keinen gemeinsamen Teiler gefunden"
        }
        return "Index 1: " + randomIndexA.toString() + " Value: " + a.toString() + "\n" + "Index 2: " + randomIndexB.toString() + " Value: " + b.toString()
    }

    private fun ggt(a: Int, b: Int): String{
        return if(b == 0){
            a.toString()
        } else {
            ggt(b, a%b)
        }
    }
}