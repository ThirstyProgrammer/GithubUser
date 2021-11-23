package id.bachtiar.harits.githubuser

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.bachtiar.harits.githubuser.databinding.ActivitySplashBinding
import kotlinx.serialization.ExperimentalSerializationApi
import java.util.*
import kotlin.concurrent.schedule

@ExperimentalSerializationApi
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timer().schedule(2000) {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}