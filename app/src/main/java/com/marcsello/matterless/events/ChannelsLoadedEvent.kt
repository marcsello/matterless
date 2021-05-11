package com.marcsello.matterless.events

import com.marcsello.matterless.ui.home.ChannelData

data class ChannelsLoadedEvent(
    val teamId:String,
    val channels:ArrayList<ChannelData>
)
