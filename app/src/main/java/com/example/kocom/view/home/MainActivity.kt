package com.example.kocom.view.home

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.bundle.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kocom.R
import com.example.kocom.databinding.ActivityMainBinding
import com.example.kocom.ext.viewBinding
import com.example.kocom.models.SortType
import com.example.kocom.utils.BaseActivity
import com.example.kocom.view.detail.DetailActivity
import com.example.kocom.view.home.adapter.UserAdapter
import com.example.kocom.viewmodels.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity(R.layout.activity_main) {

    private val viewModel: HomeViewModel by viewModel()
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private lateinit var adapter: UserAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun bindView() = with(binding) {
        //init action bar
        setSupportActionBar(materialToolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //init recyclerview
        adapter = UserAdapter { item ->
            val bundle = bundleOf(KEY_INDEX to item.index)
            val myIntent: Intent = Intent(
                this@MainActivity,
                DetailActivity::class.java
            ).putExtras(bundle)
            this@MainActivity.startActivity(myIntent)
        }
        layoutManager = LinearLayoutManager(this@MainActivity)
        rvUser.layoutManager = layoutManager
        rvUser.adapter = adapter
        rvUser.setHasFixedSize(true)
    }

    override fun observer() {
        viewModel.apply {
            onUser.observe(this@MainActivity) {
                adapter.submitList(it)
            }
            onSortValue.observe(this@MainActivity) {
                binding.tvSortValue.text = "Sort by ${it.name}"
            }
            onErrorResponse.observe(this@MainActivity) {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
            }
            onShowLoading.observe(this@MainActivity) {
                binding.progressCircular.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel
        viewModel.getItems()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.sort_title -> {
                viewModel.sortItem(SortType.SortTitle())
                return true
            }

            R.id.sort_index -> {
                viewModel.sortItem(SortType.SortIndex())
                return true
            }

            R.id.sort_date -> {
                viewModel.sortItem(SortType.SortDate())
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object {
        const val KEY_INDEX = "id"
    }
}
