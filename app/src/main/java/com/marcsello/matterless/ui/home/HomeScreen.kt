package com.marcsello.matterless.ui.home

import java.io.File

interface HomeScreen {
    fun teamsLoaded(teams:ArrayList<TeamData>, current_team_id:String?);
    fun channelsLoaded(channels:ArrayList<ChannelData>, team_id:String);
    fun personalDataLoaded(username:String, serverName:String);
    fun profilePictureLoaded(userId:String, f: File);
    fun loggedOut();
}