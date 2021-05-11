package com.marcsello.matterless.events

import com.marcsello.matterless.db.Team
import com.marcsello.matterless.ui.home.TeamData

data class TeamsLoadedEvent(
    val teams:ArrayList<TeamData>
)
