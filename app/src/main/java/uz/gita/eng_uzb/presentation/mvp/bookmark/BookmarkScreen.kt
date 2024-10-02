package uz.gita.eng_uzb.presentation.mvp.bookmark

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.eng_uzb.R
import uz.gita.eng_uzb.databinding.ScreenBookmarkBinding
import uz.gita.eng_uzb.presentation.mvp.adapters.ListAdapter
import uz.gita.eng_uzb.presentation.mvp.dialogs.InfoDialog
import java.lang.Math.abs

class BookmarkScreen : Fragment(R.layout.screen_bookmark), BookmarkContract.View {
    private val binding by viewBinding(ScreenBookmarkBinding::bind)
    private lateinit var presenter: BookmarkContract.Presenter
    private val adapter by lazy { ListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rv.adapter = adapter
        presenter = BookmarkPresenter(this)

        adapter.setOnItemClickListener { word, position ->
            presenter.dialogOpened(word)
            InfoDialog(requireContext(), word) {
                if (it) presenter.clickFavourite(word.copy(isFavourite = 1))
                else presenter.clickFavourite(word.copy(isFavourite = 0))
            }
        }

        adapter.setOnFavouriteBtnClickListener { word, position ->
            presenter.clickFavourite(word.copy(isFavourite = abs(word.isFavourite!! - 1)))
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun updateList(cursor: Cursor) {
        adapter.submitCursor(cursor)
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        println(" Bookmark resume")
        adapter.submitCursor(presenter.updateList())
        adapter.notifyDataSetChanged()
    }
}