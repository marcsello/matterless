package com.marcsello.matterless.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.navigation.NavigationView
import com.marcsello.matterless.R
import com.marcsello.matterless.injector
import com.marcsello.matterless.ui.login.LoginActivity
import javax.inject.Inject


class HomeActivity : AppCompatActivity(), HomeScreen, NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var homePresenter: HomePresenter

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navigationView: NavigationView
    private lateinit var header: View

    private var teamIdMap = HashMap<Int, String>()

    private var displayed_team_id:String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        injector.inject(this)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)


        // Setup dynamic team selector
        navigationView = findViewById(R.id.nav_view)
        header = navigationView.getHeaderView(0)

        navigationView.setNavigationItemSelectedListener(this)
        navigationView.menu.clear()
        teamIdMap.clear()

        val buttonLogout: Button = header.findViewById(R.id.buttonLogout)
        buttonLogout.setOnClickListener {
            homePresenter.performLogout()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    private fun performTeamChange(to_id:String) {
        if ((displayed_team_id == null) or (displayed_team_id != to_id)) {
            homePresenter.changeTeam(to_id);
            displayed_team_id = to_id;
        }
    }

    override fun teamsLoaded(teams: List<TeamData>, current_team_id: String) {
        navigationView.menu.clear()
        teamIdMap.clear()

        var id:Int = 0;
        teams.forEach {
            val item:MenuItem = navigationView.menu.add(Menu.NONE, id, Menu.NONE, it.name);
            teamIdMap[id] = it.id;
            Log.println(Log.VERBOSE, "HomeActivity", "Adding team ${it.name} to list at ${id}")
            id++;
        }

        performTeamChange(current_team_id)
    }

    override fun channelsLoaded(channels: List<ChannelData>) {
        TODO("Not yet implemented")
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
        homePresenter.loadDataOnForeground()
    }

    override fun onStop() {
        super.onStop()
        homePresenter.detachScreen()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val new_id = teamIdMap[item.itemId]

        Toast.makeText(this, new_id, Toast.LENGTH_SHORT).show()
        if (new_id != null) {
            performTeamChange(new_id)
        }
        return true
    }

}