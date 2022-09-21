package com.example.vama

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.example.vama.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            replaceFragment(MainFragment.newInstance {
                replaceFragment(it, true)
            }, false)
        }

        window.statusBarColor = getColor(R.color.header)
        // Making status bar overlaps with the activity
        WindowCompat.setDecorFitsSystemWindows(window, false)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun replaceFragment(newInstance: Fragment, isBackStack: Boolean) {
        val replace = supportFragmentManager.beginTransaction()
            .replace(R.id.container, newInstance)

        if (isBackStack) {
            replace.addToBackStack(newInstance.tag)
        }

        replace.commit()
    }
}