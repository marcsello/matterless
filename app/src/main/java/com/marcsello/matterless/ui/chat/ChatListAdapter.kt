package com.marcsello.matterless.ui.chat

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marcsello.matterless.R
import com.marcsello.matterless.ui.profile.ProfileActivity
import java.lang.ref.WeakReference

class ChatListAdapter : RecyclerView.Adapter<ChatListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val textSenderName: TextView = view.findViewById(R.id.textSenderName)
        private val textSentDate: TextView = view.findViewById(R.id.textSentDate)
        private val textMessageContent: TextView = view.findViewById(R.id.textMessageContent)

        private lateinit var referencedchatMessageDataPtr: WeakReference<ChatMessageData>

        init {
            view.setOnClickListener(this)
        }

        fun loadFromChatMessageData(chatMessageData: ChatMessageData) {
            referencedchatMessageDataPtr = WeakReference(chatMessageData)

            textSenderName.text = chatMessageData.senderFriendlyName
            textSentDate.text = chatMessageData.timestamp
            textMessageContent.text = chatMessageData.content

        }

        override fun onClick(view: View?) {
            val chatMessageData = referencedchatMessageDataPtr.get() ?: return

            Log.println(
                Log.VERBOSE,
                "ChatListAdapter",
                "Opening profile of ${chatMessageData.senderId}"
            )

            if (view != null) {
                val intent = Intent(view.context, ProfileActivity::class.java)
                intent.putExtra(
                    ProfileActivity.KEY_USER_FRIENDLY_NAME,
                    chatMessageData.senderFriendlyName
                )
                intent.putExtra(ProfileActivity.KEY_USER_ID, chatMessageData.senderId)
                view.context.startActivity(intent)
            }
        }
    }

    private var chatMessages = ArrayList<ChatMessageData>()

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.chat_list_item_layout, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.loadFromChatMessageData(chatMessages[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = chatMessages.size

    // Wrapped helper stuff
    fun setContents(chatMessages: ArrayList<ChatMessageData>) {
        this.chatMessages = chatMessages
        notifyDataSetChanged()
    }

    fun addNewItem(message: ChatMessageData) {
        chatMessages.add(message)
        notifyItemInserted(chatMessages.size - 1)
    }

}