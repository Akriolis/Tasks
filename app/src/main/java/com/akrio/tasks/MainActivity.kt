package com.akrio.tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.akrio.tasks.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar
import com.akrio.tasks.data.db.TaskDatabase
import com.akrio.tasks.ui.tasks.TasksViewModel
import com.akrio.tasks.ui.tasks.TasksViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: MaterialToolbar

    private lateinit var viewModel: TasksViewModel
    private lateinit var viewModelFactory: TasksViewModelFactory

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        toolbar = binding.toolbar

        val application = requireNotNull(this).application
        val dao = TaskDatabase.getInstance(application).taskDao

        viewModelFactory = TasksViewModelFactory(dao)
        viewModel = ViewModelProvider(this,viewModelFactory)[TasksViewModel::class.java]

        setContentView(binding.root)
        setSupportActionBar(toolbar)

        binding.floatingActionButton.setOnClickListener {
            showCreateTaskDialog()
        }

        //toolbar with navigation Up button
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController,appBarConfiguration)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        showCreateTaskDialog()
        return super.onOptionsItemSelected(item)
    }

    private fun showCreateTaskDialog() {
        val builder = AlertDialog.Builder(this)
        val testImageView = ImageView(this)
        testImageView.setImageDrawable(AppCompatResources
            .getDrawable(this, R.drawable.delete_forever_48px))

        builder.apply {
            setTitle(getString(R.string.clear_all_tasks))
            setView(testImageView)
            setMessage(getString(R.string.destroy))

            setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                viewModel.deleteAll()
                dialog.dismiss()
            }

            setNegativeButton(getString(R.string.no)) { dialog, _->
                dialog.dismiss()
            }
            create().show()
        }
    }
}