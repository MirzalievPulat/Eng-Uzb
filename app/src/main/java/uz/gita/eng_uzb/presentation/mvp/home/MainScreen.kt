package uz.gita.eng_uzb.presentation.mvp.home

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.speech.RecognizerIntent
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import me.zhanghai.android.fastscroll.FastScrollerBuilder
import uz.gita.eng_uzb.R
import uz.gita.eng_uzb.databinding.ScreenMainBinding
import uz.gita.eng_uzb.presentation.mvp.adapters.ListAdapter
import uz.gita.eng_uzb.presentation.mvp.dialogs.InfoDialog
import uz.gita.eng_uzb.utils.toPX
import java.util.Locale
import java.util.Objects
import kotlin.math.abs

class MainScreen : Fragment(R.layout.screen_main), MainContract.View {
    private val binding by viewBinding(ScreenMainBinding::bind)
    private lateinit var presenter: MainContract.Presenter
    private val adapter by lazy { ListAdapter() }
    private var searchQuery: String? = null

    private val handler = Handler()
    private var runnable = Runnable {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvMain.adapter = adapter
        presenter = MainPresenter(this)

        FastScrollerBuilder(binding.rvMain)
            .useMd2Style()
            .setPopupTextProvider(adapter)
            .setPadding(
                0,
                0,
                4,
                (60).toPX(requireContext()))
            .setThumbDrawable(ResourcesCompat.getDrawable(resources, R.drawable.track_back, null)!!)
            .setPopupStyle{
                it.setTextColor(Color.WHITE)
                it.setBackgroundResource(R.drawable.buble_back)
                it.gravity = Gravity.CENTER
                it.textSize = 24f
            }
            .build()

        binding.btnEngUz.setOnClickListener {
            presenter.clickLan()
        }

        binding.mic.setOnClickListener { openMicraphone() }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.searchByQuery(query = query!!)
                adapter.setSpannableText(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacks(runnable)
                runnable = Runnable {
                    searchQuery = newText
                    presenter.searchByQuery(query = newText!!)
                    adapter.setSpannableText(newText)
                }
                handler.postDelayed(runnable, 400)

                return false
            }
        })

        adapter.setOnItemClickListener { word, position ->
            presenter.dialogOpened(word)
            InfoDialog(requireContext(),word){
                if (it) presenter.clickFavourite(word.copy(isFavourite = 1))
                else presenter.clickFavourite(word.copy(isFavourite = 0))
            }
        }
        adapter.setOnFavouriteBtnClickListener { word, position ->
            presenter.clickFavourite(word.copy(isFavourite = abs( word.isFavourite!!-1)))
        }

        binding.rvMain.setHasFixedSize(true)


    }

    private fun openMicraphone() {

        // on below line we are calling speech recognizer intent.
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

        // on below line we are passing language model
        // and model free form in our intent
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )

        // on below line we are passing our
        // language as a default language.
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            Locale.getDefault()
        )

        // on below line we are specifying a prompt
        // message as speak to text on below line.
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak in English")

        // on below line we are specifying a try catch block.
        // in this block we are calling a start activity
        // for result method and passing our result code.
        try {
            micLauncher.launch(intent)
        } catch (e: Exception) {
            // on below line we are displaying error message in toast
            Toast
                .makeText(
                    requireContext(), " " + e.message,
                    Toast.LENGTH_SHORT
                )
                .show()
        }

    }

    val micLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK && it.data != null ){
            val res: ArrayList<String> =
                it.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

            setSearchViewText(binding.searchView, Objects.requireNonNull(res)[0])
        }
    }

    private fun setSearchViewText(searchView: SearchView, text: String) {
        // Access the SearchView's EditText component
        val searchEditText =
            searchView.findViewById<android.widget.EditText>(androidx.appcompat.R.id.search_src_text)
        searchEditText.setText(text)

        // Optionally, you can set the cursor to the end of the text
        searchEditText.setSelection(searchEditText.text.length)
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun updateList(cursor: Cursor) {
        adapter.submitCursor(cursor)
        adapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun updateLang(isEng: Boolean) {
        adapter.updateLang(isEng)
        adapter.notifyDataSetChanged()
        binding.btnEngUz.text = if (isEng) "ENG" else "UZB"
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        adapter.submitCursor(presenter.getWordsByLang())
        adapter.notifyDataSetChanged()
    }

}