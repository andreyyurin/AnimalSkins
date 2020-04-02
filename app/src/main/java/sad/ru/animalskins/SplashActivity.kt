package sad.ru.animalskins

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import android.view.Gravity
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.splash_activity.*


class SplashActivity : AppCompatActivity() {
    private lateinit var customHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.splash_activity)
        customHandler = Handler()
        customHandler.postDelayed(startNewActivity, 1000)
    }

    private val startNewActivity: Runnable = Runnable {
        val intent = Intent(this, MainActivity::class.java)
        //
        startActivity(intent)
        runFadeInAnimation()
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

    private fun runFadeInAnimation() {
        overridePendingTransition(R.anim.fadein, android.R.anim.fade_out);
//        val explode = Fade()
//        explode.mode = Fade.MODE_OUT
//        explode.duration = 1000
//        explode.interpolator = DecelerateInterpolator()
//        window.allowEnterTransitionOverlap = true
//        window.exitTransition = null
//        window.enterTransition = explode
    }
}