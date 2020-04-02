package sad.ru.animalskins

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.*
import com.google.android.material.appbar.AppBarLayout
import com.mindorks.editdrawabletext.DrawablePosition
import com.mindorks.editdrawabletext.OnDrawableClickListener
import kotlinx.android.synthetic.main.activity_main.*
import sad.ru.animalskins.adapter.AnimalsAdapter


class MainActivity : AppCompatActivity() {

    private lateinit var mInterstitialAd: InterstitialAd
    private lateinit var customHandler: Handler

    private var images = ArrayList<Int>()
    private var names = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initRecyclerView()
        initAdMob()
        initHandler()
        initBtnSearch()
        initItems()
    }

    private fun initItems() {
        val images = ArrayList<Int>()
        val names = ArrayList<String>()

        for (i in 1 until 61) {
            val id = resources
                .getIdentifier("animal_$i", "drawable", packageName)
            images.add(id)

            val name = resources.getIdentifier("animal_name_$i", "string", packageName)
            names.add(getString(name))
        }

        this.images = images
        this.names = names
        adapter.swapItems(images, names)
    }

    private fun initRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
    }

    private val adapter: AnimalsAdapter by lazy {
        AnimalsAdapter(this)
    }

    private fun initBtnSearch() {
        btn_search.setOnClickListener {
            edit_find.visibility = View.VISIBLE
            name_tv.visibility = View.GONE
            edit_find.requestFocus()
            val imm =
                edit_find.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(edit_find, InputMethodManager.SHOW_IMPLICIT)
        }

        edit_find.setDrawableClickListener(object : OnDrawableClickListener {
            override fun onClick(target: DrawablePosition) {
                edit_find.visibility = View.GONE
                name_tv.visibility = View.VISIBLE
                edit_find.clearFocus()
                val imm =
                    edit_find.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(edit_find.windowToken, 0)
            }

        })

        edit_find.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val namesList = ArrayList<String>()
                namesList.addAll(names)
                val imagesList = ArrayList<Int>()
                imagesList.addAll(images)
                var i = 0
                val iter = namesList.iterator()
                while (iter.hasNext()) {
                    if (!iter.next().toLowerCase().contains(p0!!.toString().toLowerCase())) {
                        imagesList.removeAt(i)
                        iter.remove()
                        i--
                    }
                    i++
                }

                adapter.swapItems(imagesList, namesList)
            }
        })
    }

    private fun initView() {
        setContentView(R.layout.activity_main)
    }

    private fun initHandler() {
        customHandler = Handler()
        customHandler.postDelayed(updateTimerThread, 0)
    }

    private fun initTimerAdvise() {
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
        mInterstitialAd.loadAd(AdRequest.Builder().addTestDevice("91CF873F530C958EBBC647EB3C5679F1").build())
    }

    private val updateTimerThread: Runnable = object : Runnable {
        override fun run() { //write here whaterver you want to repeat
            runOnUiThread {
                initTimerAdvise()
                customHandler.postDelayed(this, 60000)
            }
        }
    }

    private fun initAdMob() {
        MobileAds.initialize(this, "ca-app-pub-8154277548860310~7921530233")

        RequestConfiguration.Builder().setTestDeviceIds(
            listOf("91CF873F530C958EBBC647EB3C5679F1")
        ).build()

        val adRequest: AdRequest =
            AdRequest.Builder().addTestDevice("91CF873F530C958EBBC647EB3C5679F1").build()
        adView.loadAd(adRequest)

        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                val params = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.MATCH_PARENT
                )


                Log.d("xui", AdSize.SMART_BANNER.getHeightInPixels(adView.context).toString())
                params.setMargins(
                    0,
                    0,
                    0,
                    AdSize.SMART_BANNER.getHeightInPixels(adView.context)
                )

                //params.behavior = AppBarLayout.ScrollingViewBehavior()
                recycler_view.layoutParams = params
            }

            override fun onAdOpened() {
                super.onAdOpened()
                Log.d("xui", adView.height.toString())
            }

            override fun onAdImpression() {
                super.onAdImpression()
                Log.d("xui", adView.height.toString())
            }
        }

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-8154277548860310/5319417628"
        mInterstitialAd.loadAd(AdRequest.Builder().addTestDevice("91CF873F530C958EBBC647EB3C5679F1").build())
    }
}
