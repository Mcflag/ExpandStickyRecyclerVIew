package com.ccooy.expandablerecyclerview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ccooy.expandablerecyclerview.decoration.SampleActivity
import com.ccooy.expandablerecyclerview.stockdemo.StockActivity
import com.ccooy.expandablerecyclerview.stockdemo.StockSecondActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_expandable.setOnClickListener { SampleActivity.openActivity(this) }
//        btn_expandable.setOnClickListener {
//            startActivity(Intent(this@MainActivity, StockActivity::class.java))
//        }
    }
}
