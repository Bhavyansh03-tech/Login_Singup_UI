package com.bhavya.shopping.customerOnbording

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bhavya.shopping.R
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class CostumerOnbordingHolder : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_onbording_holder)

        val view = findViewById<ViewPager2>(R.id.view_pager)
        val dot = findViewById<DotsIndicator>(R.id.dots_indicator)

        view.adapter = SlidePageAdapter(this.supportFragmentManager, lifecycle)
        dot.attachTo(view)

        // Animation :->
        dot.translationX = 300f
        dot.alpha = 0f
        dot.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(500).start()
    }

    private class SlidePageAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle){

        private val fragmentManager = listOf(
            CustomerOnbording1(),
            CustomerOnbording2(),
            CustomerOnbording3()
        )

        override fun getItemCount(): Int {
            return fragmentManager.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentManager[position]
        }
    }
}