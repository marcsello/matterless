package com.marcsello.matterless.ui.home

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marcsello.matterless.R
import com.marcsello.matterless.ui.chat.ChatActivity
import java.lang.ref.WeakReference

class ChannelListAdapter : RecyclerView.Adapter<ChannelListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val textChannelName: TextView = view.findViewById(R.id.textChannelName)
        private val textLastMessage: TextView = view.findViewById(R.id.textLastMessage)
        private val textMessageIndicator: TextView = view.findViewById(R.id.textMessageIndicator)
        private lateinit var referencedChannelDataPtr: WeakReference<ChannelData>

        init {
            view.setOnClickListener(this)
        }

        fun loadFromChannelData(channelData: ChannelData) {
            referencedChannelDataPtr = WeakReference(channelData)

            textChannelName.text = channelData.name
            textLastMessage.text = channelData.last_message

            if (channelData.have_unread) {
                textMessageIndicator.text = "*"
            } else {
                textMessageIndicator.text = ""
            }
        }

        override fun onClick(view: View?) {
            val channelData = referencedChannelDataPtr.get() ?: return

            Log.println(Log.VERBOSE, "ChannelListAdapter", "Opening channel ${channelData.id}")

            if (view != null) {
                val intent = Intent(view.context, ChatActivity::class.java)
                intent.putExtra(ChatActivity.KEY_CHANNEL_ID, channelData.id)
                intent.putExtra(ChatActivity.KEY_IS_THREAD, false)
                view.context.startActivity(intent)
            }
        }
    }

    private var channels = ArrayList<ChannelData>()

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.channel_list_item_layout, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.loadFromChannelData(channels[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = channels.size

    // Wrapped helper stuff
    fun setContents(new_channels: ArrayList<ChannelData>) {
        channels = new_channels
        notifyDataSetChanged()
    }

    fun clear() {
        channels.clear()
        notifyDataSetChanged()
    }

}