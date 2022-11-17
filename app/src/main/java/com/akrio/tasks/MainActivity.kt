package com.akrio.tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.akrio.tasks.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar
import models.TaskDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: MaterialToolbar

    private lateinit var viewModel: TasksViewModel
    private lateinit var viewModelFactory: TasksViewModelFactory

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

        //toolbar with navigation Up button
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val builder = AppBarConfiguration.Builder(navController.graph)
        val appBarConfiguration = builder.build()
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

        builder.apply {
            setTitle(getString(R.string.clear_all_tasks))
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