package uz.gita.eng_uzb.utils

import android.content.Context
import android.content.res.Resources
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import uz.gita.eng_uzb.R

//fun AppCompatActivity.setScreen(fragment: Fragment){
//    supportFragmentManager.beginTransaction()
//        .replace(R.id.container,fragment)
//        .commit()
//}
//
//fun Fragment.replaceScreen(fragment: Fragment){
//    parentFragmentManager.beginTransaction()
//        .replace(R.id.container,fragment)
//        .addToBackStack(fragment::class.java.name)
//        .commit()
//}
//
//fun Fragment.finishScreen(){
//    requireActivity().onBackPressedDispatcher.onBackPressed()
//}

fun Fragment.showToast(message:String){
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}
//
//val Int.toPx: Int
//    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.toPX(context: Context):Int {
    return (this * context.resources.displayMetrics.density).toInt()
}
fun Int.toDp(context: Context): Int {
    return (this / context.resources.displayMetrics.density).toInt()
}