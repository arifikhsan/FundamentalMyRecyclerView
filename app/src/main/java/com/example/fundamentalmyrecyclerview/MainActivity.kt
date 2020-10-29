package com.example.fundamentalmyrecyclerview

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val list = ArrayList<Hero>()
    private var title = "Mode List"
    private var mode: Int = 0

    companion object {
        private const val STATE_TITLE = "state_string"
        private const val STATE_LIST = "state_list"
        private const val STATE_MODE = "state_mode"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_heroes.setHasFixedSize(true)

        if (savedInstanceState == null) {
            setActionBarTitle()
            list.addAll(getListHeroes())
            showRecyclerList()
            mode = R.id.action_list
        } else {
            title = savedInstanceState.getString(STATE_TITLE).toString()
            val stateList = savedInstanceState.getParcelableArrayList<Hero>(STATE_LIST)
            val stateMode = savedInstanceState.getInt(STATE_MODE)
            setActionBarTitle()
            if (stateList != null) {
                list.addAll(stateList)
            }
            setMode(stateMode)
        }

        list.addAll(getListHeroes())
        showRecyclerList()
        setActionBarTitle()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_TITLE, title)
        outState.putParcelableArrayList(STATE_LIST, list)
        outState.putInt(STATE_MODE, mode)
    }

    private fun setActionBarTitle() {
        supportActionBar?.title = title
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_layout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(selectedMode: Int) {
        when (selectedMode) {
            R.id.action_list -> showRecyclerList()
            R.id.action_cardview -> showRecyclerCardView()
            R.id.action_grid -> showRecyclerGrid()
        }
        mode = selectedMode
        setActionBarTitle()
    }

    private fun showRecyclerGrid() {
        rv_heroes.layoutManager = GridLayoutManager(this, 2)
        rv_heroes.adapter = GridHeroAdapter(list)
        title = "Mode Grid"
    }

    private fun showRecyclerCardView() {
        rv_heroes.layoutManager = LinearLayoutManager(this)
        rv_heroes.adapter = CardViewHeroAdapter(list)
        title = "Mode CardView"
    }

    private fun showRecyclerList() {
        rv_heroes.layoutManager = LinearLayoutManager(this)
        rv_heroes.adapter = ListHeroAdapter(list)
        title = "Mode List"
    }

    private fun getListHeroes(): ArrayList<Hero> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.getStringArray(R.array.data_photo)

        val listHero = ArrayList<Hero>()
        for (position in dataName.indices) {
            listHero.add(
                Hero(
                    dataName[position],
                    dataDescription[position],
                    dataPhoto[position]
                )
            )
        }
        return listHero
    }
}