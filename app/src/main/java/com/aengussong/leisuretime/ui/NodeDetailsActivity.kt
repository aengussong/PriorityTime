package com.aengussong.leisuretime.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.aengussong.leisuretime.LeisureDataViewModel
import com.aengussong.leisuretime.R
import kotlinx.android.synthetic.main.activity_node_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val KEY_LEISURE_ID = "key_leisure"

class NodeDetailsActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context, leisureId: Long) =
            Intent(context, NodeDetailsActivity::class.java).apply {
                putExtra(KEY_LEISURE_ID, leisureId)
            }
    }

    private val viewModel: LeisureDataViewModel by viewModel()

    private val leisureId: Long by lazy { intent.getLongExtra(KEY_LEISURE_ID, -1L) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_node_details)

        viewModel.observeLeisure(leisureId).observe(this, Observer { leisure ->
            leisure?.let {
                node_name.text = it.name
                node_counter.text = it.counter.toString()
            }
        })

        delete.setOnClickListener {
            viewModel.removeEntity(leisureId)
            onBackPressed()
        }

        decrement.setOnClickListener { viewModel.decrementLeisure(leisureId) }
    }
}