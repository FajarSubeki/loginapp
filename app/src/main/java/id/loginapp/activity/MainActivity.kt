package id.loginapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import id.loginapp.R
import id.loginapp.adapter.PostAdapter
import id.loginapp.data.Post
import id.loginapp.utils.PreferenceManager
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvWelcome: TextView
    private lateinit var buttonLogout: MaterialButton
    private lateinit var buttonPseudecode: MaterialButton
    private lateinit var prefs: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        fetchData()
    }

    private fun initView() {
        prefs = PreferenceManager(this)

        recyclerView = findViewById(R.id.recyclerData)
        tvWelcome = findViewById(R.id.tvWelcome)
        buttonLogout = findViewById(R.id.buttonLogout)
        buttonPseudecode = findViewById(R.id.buttonPseudoode)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun fetchData() {
        recyclerView.layoutManager = LinearLayoutManager(this)

        if (prefs.isLoggedIn()) {
            tvWelcome.text = "Selamat Datang, ${prefs.getUsername()}"
            buttonLogout.visibility = View.VISIBLE

            buttonLogout.setOnClickListener {
                prefs.logout()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            buttonPseudecode.setOnClickListener {
                val intent = Intent(this, PseudecodeActivity::class.java)
                startActivity(intent)
            }
        }

        // Fetch posts and update UI
        kotlinx.coroutines.GlobalScope.launch {
            val posts = fetchPosts()
            runOnUiThread {
                recyclerView.adapter = PostAdapter(posts)
            }
        }
    }

    private suspend fun fetchPosts(): MutableList<Post> {
        val client = HttpClient()
        return withContext(Dispatchers.IO) {
            val response: HttpResponse = client.get("http://jsonplaceholder.typicode.com/posts") {
                headers { append(HttpHeaders.Accept, ContentType.Application.Json.toString()) }
            }
            val responseBody: String = response.body()
            Json.decodeFromString<List<Post>>(responseBody).take(10).toMutableList()
        }
    }
}