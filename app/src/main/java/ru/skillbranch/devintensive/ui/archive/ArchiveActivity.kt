package ru.skillbranch.devintensive.ui.archive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_archive.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.extensions.setDecoration
import ru.skillbranch.devintensive.ui.adapters.ChatAdapter
import ru.skillbranch.devintensive.ui.adapters.ChatItemTouchHelperCallback
import ru.skillbranch.devintensive.viewmodels.ArchiveViewModel
import ru.skillbranch.devintensive.viewmodels.MainViewModel

class ArchiveActivity : AppCompatActivity() {
    private lateinit var archiveAdapter: ChatAdapter
    private lateinit var viewModel: ArchiveViewModel

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Введите имя пользователя"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.handleSearchQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.handleSearchQuery(newText)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if(item?.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archive)
        initToolbar()
        initViews()
        initViewModel()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initViews() {
        archiveAdapter = ChatAdapter {
            Snackbar.make(rv_archive_list, "Click on ${it.title}", Snackbar.LENGTH_LONG).show()
        }

        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        val touchCallback = ChatItemTouchHelperCallback(archiveAdapter) {
            val itemId = it.id
            viewModel.restoreFromArchive(itemId)
            archiveAdapter.notifyItemChanged(0)
            Snackbar.make(rv_archive_list, "Вы точно хотите восстановить ${it.title} из архива?", Snackbar.LENGTH_LONG)
                .setAction("NO") {
                    viewModel.addToArchive(itemId)
                    archiveAdapter.notifyItemChanged(0)
                }.setDecoration().show()
        }

        val itemTouchHelper = ItemTouchHelper(touchCallback)
        itemTouchHelper.attachToRecyclerView(rv_archive_list)

        with(rv_archive_list) {
            adapter = archiveAdapter
            layoutManager = LinearLayoutManager(this@ArchiveActivity)
            addItemDecoration(divider)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ArchiveViewModel::class.java)
        viewModel.getChatData().observe(this, Observer { archiveAdapter.updateData(it) })
    }
}
