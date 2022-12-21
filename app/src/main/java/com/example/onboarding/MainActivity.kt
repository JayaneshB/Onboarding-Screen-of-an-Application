package com.example.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TextClock
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.viewpager.widget.ViewPager
import com.example.onboarding.adapter.ViewPagerAdapter
import com.example.onboarding.model.OnboardingData
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {


    var ViewPagerAdapter:ViewPagerAdapter?=null
    var tabLayout: TabLayout?=null
    var onBoardingViewPagr: ViewPager?=null
    var next:TextView?=null
    private lateinit var skip:AppCompatButton
    var position=0
    var sharedPreferences:SharedPreferences?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(restorePrefData()) {
            val intent= Intent(applicationContext,FirstActivity::class.java)
            startActivity(intent)
            finish( )
        }

        setContentView(R.layout.activity_main)

        tabLayout=findViewById(R.id.tab_indicator)
        next=findViewById(R.id.btn_next)
        skip=findViewById(R.id.skip_btn)


        //click event on skip
        skip.setOnClickListener{
            val intent=Intent(applicationContext,FirstActivity::class.java)
            startActivity(intent)
        }

        // Adding data models to the class

        val Data:MutableList<OnboardingData> = ArrayList()
        Data.add(OnboardingData("Dream Big","No dream is too big. No challenge is too great. Nothing we want for our future is beyond our reach.",R.drawable.image01))
        Data.add(OnboardingData("Be Fearless","If you know the enemy and know yourself you need not fear the results of hundred battles.",R.drawable.image02))
        Data.add(OnboardingData("Find Success","Don't aim for success if you want it. Just do what you love and believe in, and it will come naturally ",R.drawable.image03))

        setOnBoardingViewPagerAdapter(Data)

        position=onBoardingViewPagr!!.currentItem

        next?.setOnClickListener{
            if(position < Data.size) {
                 position++
                onBoardingViewPagr!!.currentItem=position
            }

            if(position==Data.size){
                savePrefData()
                val intent=Intent(applicationContext,FirstActivity::class.java)
                startActivity(intent)
            }
        }

        tabLayout!!.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                position=tab!!.position
                if(tab.position==Data.size-1) {
                    next!!.text="Start"
                } else {
                    next!!.text="Next"
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun setOnBoardingViewPagerAdapter(onBoardingList:List<OnboardingData>) {

        onBoardingViewPagr=findViewById(R.id.screenPager);
        ViewPagerAdapter= ViewPagerAdapter(this,onBoardingList)
        onBoardingViewPagr!!.adapter=ViewPagerAdapter
        tabLayout?.setupWithViewPager(onBoardingViewPagr)

    }

    private fun savePrefData() {
        sharedPreferences=applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putBoolean("isFirstTimeRun",true)
        editor.apply()
    }

    private fun restorePrefData(): Boolean  {
        sharedPreferences=applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        return sharedPreferences!!.getBoolean("isFirstTimeRun",false)

    }

}