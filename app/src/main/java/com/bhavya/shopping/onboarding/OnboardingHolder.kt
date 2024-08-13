package com.bhavya.shopping.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bhavya.shopping.R
import com.bhavya.shopping.adminOnboarding.AdminOnboardingHolder
import com.bhavya.shopping.customerOnbording.CostumerOnbordingHolder
import com.google.android.material.button.MaterialButton
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class OnboardingHolder : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_holder)

        // Id's Initializing :->
        val view = findViewById<ViewPager2>(R.id.view_pager)
        val dot = findViewById<DotsIndicator>(R.id.dots_indicator)
        val shopBtn = findViewById<MaterialButton>(R.id.shop_btn)
        val customerBtn = findViewById<MaterialButton>(R.id.customer_btn)

        // Page Slider :->
        view.adapter = SlidePageAdapter(this.supportFragmentManager, lifecycle)
        dot.attachTo(view)

        // Animation :->
        shopBtn.translationX = 300f
        customerBtn.translationX = 300f
        dot.translationX = -300f

        shopBtn.alpha = 0f
        customerBtn.alpha = 0f
        dot.alpha = 0f

        shopBtn.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(400).start()
        customerBtn.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(600).start()
        dot.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(500).start()

        // Btn Initializing :->
        shopBtn.setOnClickListener {
            startActivity(Intent(this@OnboardingHolder, AdminOnboardingHolder::class.java))
            finish()
        }
        customerBtn.setOnClickListener {
            startActivity(Intent(this@OnboardingHolder, CostumerOnbordingHolder::class.java))
            finish()
        }
    }

    private class SlidePageAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle){

        private val fragmentManager = listOf(
            Onboarding1(),
            Onboarding2(),
            Onboarding3()
        )

        override fun getItemCount(): Int {
            return fragmentManager.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentManager[position]
        }
    }
}