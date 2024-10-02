package uz.gita.eng_uzb.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import eightbitlab.com.blurview.RenderScriptBlur
import uz.gita.eng_uzb.R
import uz.gita.eng_uzb.databinding.ActivityMainBinding
import uz.gita.eng_uzb.presentation.mvp.adapters.ViewPagerAdapter
import uz.gita.eng_uzb.presentation.mvp.bookmark.BookmarkScreen
import uz.gita.eng_uzb.presentation.mvp.history.HistoryScreen
import uz.gita.eng_uzb.presentation.mvp.home.MainScreen
import uz.gita.eng_uzb.presentation.mvp.info.InfoScreen
import uz.gita.eng_uzb.utils.NetworkStatusValidator


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

//        setScreen(MainScreen())

        attachPagerAndBottomNav()
        setViewPagerAdapter()
        makeBottomNavBlurry()
        runNetworkStatusValidator()

    }

    private fun runNetworkStatusValidator() {
        NetworkStatusValidator.getInstance().listenNetworkStatus({},{})
    }

    private fun makeBottomNavBlurry() {
        val  decorView = window.decorView;
        val rootView = decorView.findViewById<View>(android.R.id.content) as ViewGroup
        val windowBackground = decorView.background
        binding.blur.setupWith(rootView, RenderScriptBlur(this))
            .setFrameClearDrawable(windowBackground)
            .setBlurRadius(1F)

        binding.blur.outlineProvider = ViewOutlineProvider.BACKGROUND;
        binding.blur.clipToOutline = true;
    }

    private fun setViewPagerAdapter() {
        val fragmentList = arrayListOf(MainScreen(),BookmarkScreen(),HistoryScreen(),InfoScreen())
        binding.viewPager.adapter = ViewPagerAdapter(this,fragmentList)
    }


    private fun attachPagerAndBottomNav() {
        binding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                binding.bottomNav.selectedItemId = when(position){
                    0-> R.id.home
                    1-> R.id.bookmark
                    2-> R.id.history
                    else-> R.id.info
                }
            }
        })

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> binding.viewPager.currentItem = 0
                R.id.bookmark -> binding.viewPager.currentItem = 1
                R.id.history -> binding.viewPager.currentItem = 2
                else-> binding.viewPager.currentItem = 3
            }
            return@setOnItemSelectedListener true
        }
    }
}