package uz.gita.eng_uzb.presentation.mvp.history

import android.annotation.SuppressLint
import android.app.Dialog
import android.database.Cursor
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.eng_uzb.R
import uz.gita.eng_uzb.databinding.ScreenHistoryBinding
import uz.gita.eng_uzb.presentation.mvp.adapters.HistoryAdapter
import uz.gita.eng_uzb.presentation.mvp.adapters.ListAdapter
import uz.gita.eng_uzb.presentation.mvp.bookmark.BookmarkContract
import uz.gita.eng_uzb.presentation.mvp.bookmark.BookmarkPresenter
import uz.gita.eng_uzb.presentation.mvp.dialogs.InfoDialog
import java.lang.Math.abs

class HistoryScreen:Fragment(R.layout.screen_history),HistoryContract.View {
    private val binding by viewBinding(ScreenHistoryBinding::bind)
    private lateinit var presenter: HistoryContract.Presenter
    private val adapter by lazy { HistoryAdapter() }
    private var showDeleteAll = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rv.adapter = adapter
        presenter = HistoryPresenter(this)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        addMenu(requireActivity())

        adapter.setOnItemClickListener { word ->
            presenter.dialogOpened(word)
            InfoDialog(requireContext(), word) {
                if (it) presenter.clickFavourite(word.copy(isFavourite = 1))
                else presenter.clickFavourite(word.copy(isFavourite = 0))
            }
        }

        adapter.setOnFavouriteBtnClickListener { word ->
            presenter.clickFavourite(word.copy(isFavourite = abs(word.isFavourite!! - 1)))
        }

        adapter.setOnDeleteClickListener { word ->
            presenter.deleteClick(word)
        }
    }

    private fun addMenu(requireActivity: FragmentActivity) {
        requireActivity.addMenuProvider(object :MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.history_menu,menu)
            }

            override fun onPrepareMenu(menu: Menu) {
                val menuItem = menu.findItem(R.id.deleteAll)
                menuItem.isVisible = showDeleteAll
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId){
                    R.id.deleteAll->{
                        val dialog = AlertDialog.Builder(requireContext()).create()
                        dialog.setMessage("Delete all from history?")
                        dialog.setButton(Dialog.BUTTON_POSITIVE,"No"){d,w->
                            dialog.dismiss()
                        }
                        dialog.setButton(Dialog.BUTTON_NEUTRAL,"Yes"){d,w->
                            presenter.deleteAll()
                        }

                        dialog.show()
                        dialog.getButton(Dialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.main))
                        dialog.getButton(Dialog.BUTTON_NEUTRAL).setTextColor(resources.getColor(R.color.main))

                        true
                    }
                    else->{false}
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun updateList(cursor: Cursor) {
        showDeleteAll = cursor.count >= 1
        requireActivity().invalidateOptionsMenu()

        adapter.submitCursor(cursor)
        adapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        println(" History resume")
        updateList(presenter.updateList())
    }
}