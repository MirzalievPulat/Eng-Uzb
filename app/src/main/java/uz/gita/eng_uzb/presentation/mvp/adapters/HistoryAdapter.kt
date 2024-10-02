package uz.gita.eng_uzb.presentation.mvp.adapters

import android.annotation.SuppressLint
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import uz.gita.eng_uzb.R
import uz.gita.eng_uzb.data.room.entity.DictionaryEntity
import uz.gita.eng_uzb.databinding.ItemBinding
import uz.gita.eng_uzb.databinding.ItemHistoryBinding
import uz.gita.eng_uzb.utils.getWordData
import uz.gita.eng_uzb.utils.toPX
import uz.gita.eng_uzb.utils.toPattern

class HistoryAdapter() : RecyclerView.Adapter<HistoryAdapter.DictionaryViewHolder>() {
    private var onClickItem: ((word: DictionaryEntity) -> Unit)? = null
    private var favouriteBtnOnClick: ((word: DictionaryEntity) -> Unit)? = null
    private var deleteBtnClick: ((word: DictionaryEntity) -> Unit)? = null
    private lateinit var cursor: Cursor

    inner class DictionaryViewHolder(val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imgBookmark.setOnClickListener {
                cursor.moveToPosition(adapterPosition)
                val word = cursor.getWordData()
                favouriteBtnOnClick?.invoke(word)
            }

            itemView.setOnClickListener {
                cursor.moveToPosition(adapterPosition)
                val word = cursor.getWordData()
                onClickItem?.invoke(word)
            }

            binding.imgBookmarkDelete.setOnClickListener {
                cursor.moveToPosition(adapterPosition)
                val word = cursor.getWordData()
                deleteBtnClick?.invoke(word)
            }
        }

        fun bind(dictionaryEntity: DictionaryEntity) {
            binding.tvItemMain.text = dictionaryEntity.english!!.trim().also { println("word: $it") }
            binding.tvItemType.text = "[${dictionaryEntity.type!!.trim()}]".also { println("type: $it") }


            if (dictionaryEntity.isFavourite == 1) {
                binding.imgBookmark.setImageResource(R.drawable.bookmark_fill)
            } else binding.imgBookmark.setImageResource(R.drawable.bookmark)


            binding.tvItemTime.text = dictionaryEntity.time.toPattern()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return DictionaryViewHolder(ItemHistoryBinding.bind(view))
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
            params.bottomMargin = margin.toPX(holder.itemView.context)  // Set the desired bottom margin
            holder.itemView.layoutParams = params
        }

        cursor.let {
            cursor.moveToPosition(position)
            holder.bind(cursor.getWordData())
        }
    }

//    override fun onViewAttachedToWindow(holder: DictionaryViewHolder) {
//        val animation = AnimationUtils.loadAnimation(holder.itemView.context,R.anim.alpha)
//        holder.itemView.startAnimation(animation)
//    }

    fun submitCursor(cursor: Cursor) {
        this.cursor = cursor
    }

    fun setOnItemClickListener(listener: (word: DictionaryEntity) -> Unit) {
        this.onClickItem = listener
    }

    fun setOnDeleteClickListener(listener: (word: DictionaryEntity) -> Unit) {
        this.deleteBtnClick = listener
    }

    fun setOnFavouriteBtnClickListener(listener: (word: DictionaryEntity) -> Unit) {
        this.favouriteBtnOnClick = listener
    }
}