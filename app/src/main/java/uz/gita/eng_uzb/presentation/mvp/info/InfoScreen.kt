package uz.gita.eng_uzb.presentation.mvp.info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.eng_uzb.R
import uz.gita.eng_uzb.databinding.ScreenInfoBinding
import uz.gita.eng_uzb.utils.NetworkStatusValidator
import uz.gita.eng_uzb.utils.showToast


class InfoScreen:Fragment(R.layout.screen_info) {
    private val binding by viewBinding(ScreenInfoBinding::bind)
    private val networkStatusValidator = NetworkStatusValidator.getInstance()
    private var lastTime = 0L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.telegram.setOnClickListener {
            openProfile("https://t.me/mirzaliyev2002")
        }

        binding.twitter.setOnClickListener {
            openProfile("https://x.com/MirzaliyevPulat?t=dZQXRstq1eoS9N0QFN2TKg&s=09")
        }

        binding.linkedin.setOnClickListener{
            openProfile("https://www.linkedin.com/in/po-lat-mirzaliyev-1628762b6/")
        }


    }

    private fun openProfile(url:String){
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastTime < 1000) return

        if (networkStatusValidator.isNetworkEnabled){
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }else{
            showToast("No Internet")
        }

        lastTime = currentTime
    }
}