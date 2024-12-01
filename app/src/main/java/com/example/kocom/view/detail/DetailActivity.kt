package com.example.kocom.view.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.kocom.R
import com.example.kocom.databinding.ActivityUserDetailBinding
import com.example.kocom.ext.viewBinding
import com.example.kocom.utils.BaseActivity
import com.example.kocom.view.home.MainActivity.Companion.KEY_INDEX
import com.example.kocom.viewmodels.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : BaseActivity(R.layout.activity_user_detail) {

    private val viewModel: DetailViewModel by viewModel()
    private val binding by viewBinding(ActivityUserDetailBinding::bind)

    override fun bindView() = with(binding) {
        setSupportActionBar(materialToolbar)
        materialToolbar.title = "User Details"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        btnDelete.setOnClickListener {
            viewModel.deleteItem()
        }
    }

    override fun observer() {
        viewModel.apply {
            onItem.observe(this@DetailActivity) { item ->
                with(binding) {
                    tvTitel.text = item.title
                    tvDescription.text = item.description
                    tvDate.text = item.date
                }
            }
            onDeleteSuccess.observe(this@DetailActivity) {
                Toast.makeText(this@DetailActivity, "Delete success", Toast.LENGTH_SHORT).show()
                finish()
            }

            onErrorResponse.observe(this@DetailActivity) {
                Toast.makeText(this@DetailActivity, it, Toast.LENGTH_SHORT).show()
            }
            onShowLoading.observe(this@DetailActivity) {
                binding.progressCircular.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val index: Int = intent.getIntExtra(KEY_INDEX, 0)
        if (index > 0) viewModel.getItemByIndex(index)
    }
}