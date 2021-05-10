package com.marcsello.matterless.ui.home

interface HomeScreen {
    fun teamsLoaded(teams:ArrayList<TeamData>, current_team_id:String);
    fun channelsLoaded(channels:ArrayList<ChannelData>, team_id:String);
    fun personalDataLoaded(username:String, serverName:String);
    fun loggedOut();
}