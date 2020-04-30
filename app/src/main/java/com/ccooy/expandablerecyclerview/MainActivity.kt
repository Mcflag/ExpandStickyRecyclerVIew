package com.ccooy.expandablerecyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ccooy.expandablerecyclerview.sample.SampleActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_expandable.setOnClickListener { SampleActivity.openActivity(this) }
    }
}
