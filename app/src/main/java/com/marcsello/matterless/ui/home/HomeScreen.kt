package com.marcsello.matterless.ui.home

interface HomeScreen {
    fun teamsLoaded(teams:List<TeamData>, current_team_id:String);
    fun channelsLoaded(channels:List<ChannelData>);
    fun personalDataLoaded(username:String, serverName:String);
    fun loggedOut();
}