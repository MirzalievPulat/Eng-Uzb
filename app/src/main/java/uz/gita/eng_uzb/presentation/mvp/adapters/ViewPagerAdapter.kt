package uz.gita.eng_uzb.presentation.mvp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fr:FragmentActivity, private val fragmentList:ArrayList<Fragment>):FragmentStateAdapter(fr) {
    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}