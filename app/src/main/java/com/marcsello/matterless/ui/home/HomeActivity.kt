package com.marcsello.matterless.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.marcsello.matterless.R
import com.marcsello.matterless.injector
import com.marcsello.matterless.ui.login.LoginActivity
import javax.inject.Inject


class HomeActivity : AppCompatActivity(), HomeScreen,
    NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var homePresenter: HomePresenter

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navigationView: NavigationView
    private lateinit var header: View

    private var teams: ArrayList<TeamData>? = null
    private var displayed_team_id: Int? = null

    private val channelListAdapter = ChannelListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        injector.inject(this)

        title = "Loading..."

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)


        // Setup dynamic team selector
        navigationView = findViewById(R.id.nav_view)
        header = navigationView.getHeaderView(0)

        navigationView.setNavigationItemSelectedListener(this)
        navigationView.menu.clear()
        channelListAdapter.clear()

        val buttonLogout: Button = header.findViewById(R.id.buttonLogout)
        buttonLogout.setOnClickListener {
            homePresenter.performLogout()
        }


        val recycleViewChannelList = findViewById<View>(R.id.recycleViewChannelList) as RecyclerView
        recycleViewChannelList.adapter = channelListAdapter
        recycleViewChannelList.layoutManager = LinearLayoutManager(this)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    private fun performTeamChange(index: Int) {

        if (teams == null) {
            return;
        }

        if ((displayed_team_id == null) or (displayed_team_id != index)) {
            channelListAdapter.clear()
            this.title = teams!![index].name // Sets the activity title
            displayed_team_id = index; // this may required in the channelsLoaded call so we have to set it before

            homePresenter.changeTeam(teams!![index].id);
        }
    }

    override fun teamsLoaded(teams: ArrayList<TeamData>, current_team_id: String?) {
        this.teams = teams
        navigationView.menu.clear()

        var target_id: Int? = null
        teams.forEachIndexed { i, v ->
            navigationView.menu.add(Menu.NONE, i, Menu.NONE, v.name);
            Log.println(Log.VERBOSE, "HomeActivity", "Adding team ${v.name} to list at $i")

            if (v.id == current_team_id) {
                target_id = i
            }
        }

        if (target_id != null) {
            performTeamChange(target_id!!)
        } else {
            performTeamChange(0)
        }
    }

    override fun channelsLoaded(channels: ArrayList<ChannelData>, team_id: String) {

        var displayed_team_api_id:String? = null
        if (displayed_team_id != null) {
            try {
                displayed_team_api_id = teams!![displayed_team_id!!].id
            } catch (e:KotlinNullPointerException) {
                // Just keep it null then
            }
        }

        if ((displayed_team_api_id == null) or (displayed_team_api_id == team_id)) {
            channelListAdapter.setContents(channels)
        }

    }

    override fun personalDataLoaded(username: String, serverName: String) {
        val textUsername: TextView = header.findViewById(R.id.textUsername)
        textUsername.text = username;

        val textServerName: TextView = header.findViewById(R.id.textServerName)
        textServerName.text = serverName;
    }

    override fun loggedOut() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        homePresenter.attachScreen(this)

        // Start loading data
        homePresenter.loadDataOnStart()
    }

    override fun onStop() {
        super.onStop()
        homePresenter.detachScreen()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        performTeamChange(item.itemId)
        return true
    }

}