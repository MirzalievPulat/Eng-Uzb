package uz.gita.eng_uzb.presentation.mvp.adapters

import android.annotation.SuppressLint
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import me.zhanghai.android.fastscroll.FastScrollScrollView
import me.zhanghai.android.fastscroll.FastScroller
import me.zhanghai.android.fastscroll.FastScrollerBuilder
import me.zhanghai.android.fastscroll.PopupTextProvider
import uz.gita.eng_uzb.R
import uz.gita.eng_uzb.data.model.AdapterType
import uz.gita.eng_uzb.data.room.entity.DictionaryEntity
import uz.gita.eng_uzb.databinding.ItemBinding
import uz.gita.eng_uzb.utils.getWordData
import uz.gita.eng_uzb.utils.createSpannable
import uz.gita.eng_uzb.utils.toDp
import uz.gita.eng_uzb.utils.toPX

class ListAdapter() : RecyclerView.Adapter<ListAdapter.DictionaryViewHolder>(),PopupTextProvider{
    private var onClickItem: ((word: DictionaryEntity, position: Int) -> Unit)? = null
    private var favouriteBtnOnClick: ((word: DictionaryEntity, position: Int) -> Unit)? = null
    private lateinit var cursor: Cursor
    private var isEng = true
    private var spannableText: String = ""

    inner class DictionaryViewHolder(val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imgBookmark.setOnClickListener {
                cursor.moveToPosition(adapterPosition)
                val word = cursor.getWordData()
                favouriteBtnOnClick?.invoke(word, adapterPosition)
            }

            itemView.setOnClickListener {
                cursor.moveToPosition(adapterPosition)
                val word = cursor.getWordData()
                onClickItem?.invoke(word, adapterPosition)
            }
        }

        fun bind(dictionaryEntity: DictionaryEntity) {
            if (isEng){
                binding.tvItemMain.text = dictionaryEntity.english?.trim()!!.createSpannable(spannableText)
                binding.tvItemType.text = "[${dictionaryEntity.type!!.trim()}]"
            }else{
                binding.tvItemMain.text = dictionaryEntity.uzbek!!.trim().createSpannable(spannableText)
                binding.tvItemType.text = ""
            }

            if (dictionaryEntity.isFavourite == 1){
                binding.imgBookmark.setImageResource(R.drawable.bookmark_fill)
            }else binding.imgBookmark.setImageResource(R.drawable.bookmark)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return DictionaryViewHolder(ItemBinding.bind(view))
    }

    override fun getItemCount(): Int = cursor.count

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {

        val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
        params.bottomMargin = 0 // Reset margin
        holder.itemView.layoutParams = params

        // Apply bottom margin to the last item
        if (position == cursor.count - 1) {
            val margin = 60
            params.bottomMargin = margin.toPX(holder.itemView.context) // Set the desired bottom margin
            holder.itemView.layoutParams = params
        }

        cursor.let {
            cursor.moveToPosition(position)
            holder.bind(cursor.getWordData())
        }

    }

//    override fun onViewAttachedToWindow(holder: DictionaryViewHolder) {
//        val animation = AnimationUtils.loadAnimation(holder.itemView.context,R.anim.scale_up)
//        holder.itemView.startAnimation(animation)
//    }

    fun submitCursor(cursor: Cursor) {
        this.cursor = cursor
    }

    fun updateLang(isEng: Boolean) {
        this.isEng = isEng
    }

    fun setSpannableText(text: String) {
        this.spannableText = text
    }

    fun setOnItemClickListener(listener: (word: DictionaryEntity, position: Int) -> Unit) {
        this.onClickItem = listener
    }


    fun setOnFavouriteBtnClickListener(listener: (word: DictionaryEntity, position: Int) -> Unit) {
        this.favouriteBtnOnClick = listener
    }

    override fun getPopupText(view: View, position: Int): CharSequence {
        cursor.moveToPosition(position)
        return  cursor.getWordData().english!!.first().uppercaseChar().toString()
    }
}