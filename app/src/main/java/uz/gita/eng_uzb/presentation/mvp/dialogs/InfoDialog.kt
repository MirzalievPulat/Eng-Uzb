package uz.gita.eng_uzb.presentation.mvp.dialogs

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import uz.gita.eng_uzb.R
import uz.gita.eng_uzb.data.room.entity.DictionaryEntity
import uz.gita.eng_uzb.databinding.DialogInfoBinding
import java.util.Locale

class InfoDialog(
    val context: Context,
    val dictionaryEntity: DictionaryEntity,
    bookmarkClick: (isFavourite: Boolean) -> Unit
) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private lateinit var dialogBinding: DialogInfoBinding
    private var lastTime = 0L

    init {
        tts = TextToSpeech(context, this)
        val dialog = AlertDialog.Builder(context).create()
        dialogBinding = DialogInfoBinding.inflate(LayoutInflater.from(context), null, false)

        dialogBinding.engWord.text = dictionaryEntity.english
        dialogBinding.uzbWord.text = dictionaryEntity.uzbek
        dialogBinding.engTranscript.text = dictionaryEntity.transcript
        dialogBinding.engType.text = dictionaryEntity.type
        dialogBinding.engCountable.text = dictionaryEntity.countable
        var isFavourite = dictionaryEntity.isFavourite == 1
        if (isFavourite) {
            dialogBinding.bookmark.setImageResource(R.drawable.bookmark_fill)
        } else dialogBinding.bookmark.setImageResource(R.drawable.bookmark)

        dialogBinding.bookmark.setOnClickListener {
            isFavourite = !isFavourite
            bookmarkClick.invoke(isFavourite)
            if (isFavourite) {
                dialogBinding.bookmark.setImageResource(R.drawable.bookmark_fill)
            } else dialogBinding.bookmark.setImageResource(R.drawable.bookmark)
        }

        dialogBinding.volume.setOnClickListener {
            var currentTime = System.currentTimeMillis()
            if (currentTime - lastTime < 1000) return@setOnClickListener

            tts!!.speak(dictionaryEntity.english, TextToSpeech.QUEUE_FLUSH, null, "")

            lastTime = currentTime
        }

        dialogBinding.copy.setOnClickListener {
            var currentTime = System.currentTimeMillis()
            if (currentTime - lastTime < 1000) return@setOnClickListener

            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("label", dictionaryEntity.english)
            clipboard.setPrimaryClip(clipData)

            Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()
            lastTime = currentTime
        }


//        makeDialogBlurry()
//        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
        dialog.setView(dialogBinding.root)
        dialog.show()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(context, "The Language not supported!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

//    private fun makeDialogBlurry() {
//        val  decorView = fragmentActivity.window.decorView;
//        val rootView = decorView.findViewById<View>(android.R.id.content) as ViewGroup
//        val windowBackground = decorView.background
//        dialogBinding.blur.setupWith(rootView, RenderScriptBlur(context))
//            .setFrameClearDrawable(windowBackground)
//            .setBlurRadius(10F)
//
//        dialogBinding.blur.outlineProvider = ViewOutlineProvider.BACKGROUND;
//        dialogBinding.blur.clipToOutline = true;
//    }
}