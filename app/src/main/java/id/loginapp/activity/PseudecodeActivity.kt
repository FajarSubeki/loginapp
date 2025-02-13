package id.loginapp.activity

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import id.loginapp.R
import id.loginapp.data.NumberLabel
import java.security.MessageDigest

class PseudecodeActivity : AppCompatActivity() {

    private lateinit var containerOne: LinearLayout
    private lateinit var containerTwo: LinearLayout
    private lateinit var containerThree: LinearLayout
    private lateinit var textInputX: TextInputEditText
    private lateinit var buttonShow: MaterialButton
    private lateinit var textInputX4: TextInputEditText
    private lateinit var buttonShow4: MaterialButton
    private lateinit var textTerbilang: TextView
    private lateinit var textArrayShort: TextView
    private lateinit var textHashString: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pseudecode)

        initView()
    }

    private fun initView() {

        containerOne = findViewById(R.id.viewContainerOne)
        containerTwo = findViewById(R.id.viewContainerTwo)
        containerThree = findViewById(R.id.viewContainerThree)
        textTerbilang = findViewById(R.id.tvTerbilang)
        textInputX = findViewById(R.id.etInputX)
        buttonShow = findViewById(R.id.buttonShow)
        textInputX4 = findViewById(R.id.etInputX4)
        buttonShow4 = findViewById(R.id.buttonShow4)
        textArrayShort = findViewById(R.id.tvArraySort)
        textHashString = findViewById(R.id.tvHashString)

        numberOne()
        numberTwo()

        buttonShow.setOnClickListener {
            numberThree()
        }

        buttonShow4.setOnClickListener {
            numberFour()
        }

        numberFive()
        numberSix()
    }

    private fun numberOne() {
        val numberList = generateNumberList()

        // Tambahkan TextView ke dalam LinearLayout
        numberList.forEach { item ->
            val textView = TextView(this)
            textView.text = "${item.number} - ${item.label}"
            textView.textSize = 18f
            textView.setPadding(10, 10, 10, 10)
            containerOne.addView(textView)
        }
    }

    private fun numberTwo() {
        // Generate deret Fibonacci sebanyak 20 angka
        val fibonacciList = generateFibonacci()

        // Tambahkan TextView ke dalam LinearLayout
        fibonacciList.forEachIndexed { index, number ->
            val textView = TextView(this)
            textView.text = "${index + 1}. $number"
            textView.textSize = 18f
            textView.setPadding(10, 10, 10, 10)
            containerTwo.addView(textView)
        }
    }

    private fun numberThree() {

        val inputX = textInputX.text.toString()
        if (inputX.isNotEmpty()) {
            val x = inputX.toInt() // Ubah nilai x sesuai keinginan

            // Tambahkan TextView untuk setiap baris
            for (i in 1..x) {
                val textView = TextView(this)
                textView.text = "* ".repeat(i) // Membuat pola bintang
                textView.textSize = 18f
                textView.setPadding(10, 5, 10, 5)
                containerThree.addView(textView)
            }
        } else {
            Toast.makeText(applicationContext, "Harap isi dulu...", Toast.LENGTH_SHORT).show()
        }

    }

    private fun numberFour() {

        val inputX = textInputX4.text.toString()
        if (inputX.isNotEmpty()) {
            val x = inputX.toInt() // Ubah nilai x sesuai keinginan
            if (x < 2000) {
                Toast.makeText(applicationContext, "Kurang dari 2000...", Toast.LENGTH_SHORT).show()
            } else {
                textTerbilang.text = "$x = ${convertToWords(x)}"
            }

        } else {
            Toast.makeText(applicationContext, "Harap isi dulu...", Toast.LENGTH_SHORT).show()
        }

    }

    private fun numberFive() {

        val fruits = arrayOf("Mango", "Apple", "Banana", "Orange", "Grapes")
        fruits.sort() // Mengurutkan secara alfabetis
        textArrayShort.text = "Hasil Sorting:\n" + fruits.joinToString(", ")

    }

    private fun numberSix() {

        val inputString = "13022025fajarpriaifabula"
        val hashedValue = sha256(inputString)
        textHashString.text = "SHA-256 Hash: $hashedValue\n"

    }

    private fun generateNumberList(): List<NumberLabel> {
        return (50..100 step 5).map { number ->
            val label = when {
                number <= 60 -> "KURANG"
                number in 61..70 -> "CUKUP"
                number in 71..80 -> "BAIK"
                else -> "LUAR BIASA"
            }
            NumberLabel(number, label)
        }
    }

    // Fungsi untuk menghasilkan deret Fibonacci
    private fun generateFibonacci(): List<Int> {
        val list = mutableListOf(0, 1)
        for (i in 2 until 20) {
            list.add(list[i - 1] + list[i - 2])
        }
        return list
    }

    private fun convertToWords(number: Int): String {
        val units = arrayOf(
            "", "Satu", "Dua", "Tiga", "Empat", "Lima", "Enam", "Tujuh", "Delapan", "Sembilan"
        )

        val tens = arrayOf(
            "", "Sepuluh", "Dua Puluh", "Tiga Puluh", "Empat Puluh", "Lima Puluh",
            "Enam Puluh", "Tujuh Puluh", "Delapan Puluh", "Sembilan Puluh"
        )

        val teens = arrayOf(
            "Sepuluh", "Sebelas", "Dua Belas", "Tiga Belas", "Empat Belas",
            "Lima Belas", "Enam Belas", "Tujuh Belas", "Delapan Belas", "Sembilan Belas"
        )

        if (number < 2000 || number > 9999) return "Angka harus di antara 2000 - 9999"

        val ribuan = number / 1000
        val ratusan = (number % 1000) / 100
        val puluhan = (number % 100) / 10
        val satuan = number % 10

        val sb = StringBuilder()

        if (ribuan > 0) sb.append("${units[ribuan]} Ribu ")

        if (ratusan > 0) {
            if (ratusan == 1) sb.append("Seratus ")
            else sb.append("${units[ratusan]} Ratus ")
        }

        if (puluhan == 1) {
            sb.append("${teens[satuan]} ")
        } else {
            if (puluhan > 0) sb.append("${tens[puluhan]} ")
            if (satuan > 0) sb.append("${units[satuan]} ")
        }

        return sb.toString().trim()
    }

    fun sha256(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) } // Convert byte array to hex string
    }
}