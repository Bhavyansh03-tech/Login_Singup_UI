package com.bhavya.shopping.customerOnbording

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.bhavya.shopping.R
import com.google.android.material.button.MaterialButton

class CustomerOnbording2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_customer_onbording2, container, false)

        val nxt : MaterialButton = v.findViewById(R.id.nxt1)
        val back : MaterialButton = v.findViewById(R.id.back)
        val lottie : LottieAnimationView = v.findViewById(R.id.lottie_2)
        val heading : TextView = v.findViewById(R.id.onboard_heading_2)
        val bio : TextView = v.findViewById(R.id.onboard_bio_2)
        val view = activity?.findViewById<ViewPager2>(R.id.view_pager)

        // Going to nxt page :->
        nxt.setOnClickListener {
            view?.currentItem = 2
        }

        // Going to previous page :->
        back.setOnClickListener {
            view?.currentItem = -1
        }


        // Animation :->
        nxt.translationX = 300f
        back.translationX = -300f
        lottie.translationX = 300f
        heading.translationX = 300f
        bio.translationX = 300f

        nxt.alpha = 0f
        back.alpha = 0f
        lottie.alpha = 0f
        heading.alpha = 0f
        bio.alpha = 0f

        lottie.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(400).start()
        heading.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(500).start()
        bio.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(600).start()
        nxt.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(700).start()
        back.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(700).start()

        return v
    }
}