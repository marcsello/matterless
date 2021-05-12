package com.marcsello.matterless

import com.marcsello.matterless.model.LoginCredentials
import com.marcsello.matterless.model.NewPost
import com.marcsello.matterless.network.ChannelsApi
import com.marcsello.matterless.network.PostsApi
import com.marcsello.matterless.network.TeamsApi
import com.marcsello.matterless.network.UsersApi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Retrofit2ModelTest {


    lateinit var mockWebServer: MockWebServer
    lateinit var retrofit: Retrofit


    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown();
    }

    @Test
    fun testLoginResponse() {
        mockWebServer.enqueue(
            MockResponse().setBody(
                """
            {
  "id": "almaID",
  "create_at": 0,
  "update_at": 0,
  "delete_at": 0,
  "username": "almaUSERNAME",
  "first_name": "almaFIRSTNAME",
  "last_name": "almaLASTNAME",
  "nickname": "almaNICKNAME",
  "email": "string",
  "email_verified": true,
  "auth_service": "string",
  "roles": "almaROLES",
  "locale": "string",
  "notify_props": {
    "email": true,
    "push": "string",
    "desktop": "string",
    "desktop_sound": true,
    "mention_keys": "string",
    "channel": true,
    "first_name": true
  },
  "props": {},
  "last_password_update": 0,
  "last_picture_update": 0,
  "failed_attempts": 0,
  "mfa_active": true,
  "timezone": {
    "useAutomaticTimezone": true,
    "manualTimezone": "string",
    "automaticTimezone": "string"
  },
  "terms_of_service_id": "string",
  "terms_of_service_create_at": 0
}
        """.trimIndent()
            )
        )

        val usersApi = retrofit.create(UsersApi::class.java)

        val call = usersApi.doLogin(LoginCredentials("alma", "barack"))

        val body = call.execute().body()!!

        assert(body.id == "almaID")
        assert(body.username == "almaUSERNAME")
        assert(body.firstName == "almaFIRSTNAME")
        assert(body.lastName == "almaLASTNAME")
        assert(body.nickname == "almaNICKNAME")
        assert(body.roles == "almaROLES")
    }


    @Test
    fun testGetUserInfo() {
        mockWebServer.enqueue(
            MockResponse().setBody(
                """
            {
  "id": "almaID",
  "create_at": 0,
  "update_at": 0,
  "delete_at": 0,
  "username": "almaUSERNAME",
  "first_name": "almaFIRSTNAME",
  "last_name": "almaLASTNAME",
  "nickname": "almaNICKNAME",
  "email": "string",
  "email_verified": true,
  "auth_service": "string",
  "roles": "almaROLES",
  "locale": "string",
  "notify_props": {
    "email": true,
    "push": "string",
    "desktop": "string",
    "desktop_sound": true,
    "mention_keys": "string",
    "channel": true,
    "first_name": true
  },
  "props": {},
  "last_password_update": 0,
  "last_picture_update": 0,
  "failed_attempts": 0,
  "mfa_active": true,
  "timezone": {
    "useAutomaticTimezone": true,
    "manualTimezone": "string",
    "automaticTimezone": "string"
  },
  "terms_of_service_id": "string",
  "terms_of_service_create_at": 0
}
        """.trimIndent()
            )
        )

        val usersApi = retrofit.create(UsersApi::class.java)

        val call = usersApi.getUserInfo("token", "almaID")

        val body = call.execute().body()!!

        assert(body.id == "almaID")
        assert(body.username == "almaUSERNAME")
        assert(body.firstName == "almaFIRSTNAME")
        assert(body.lastName == "almaLASTNAME")
        assert(body.nickname == "almaNICKNAME")
        assert(body.roles == "almaROLES")
    }

    @Test
    fun testGetTeamMembers() {
        mockWebServer.enqueue(
            MockResponse().setBody(
                """[
            {
  "id": "almaID",
  "create_at": 0,
  "update_at": 0,
  "delete_at": 0,
  "username": "almaUSERNAME",
  "first_name": "almaFIRSTNAME",
  "last_name": "almaLASTNAME",
  "nickname": "almaNICKNAME",
  "email": "string",
  "email_verified": true,
  "auth_service": "string",
  "roles": "almaROLES",
  "locale": "string",
  "notify_props": {
    "email": true,
    "push": "string",
    "desktop": "string",
    "desktop_sound": true,
    "mention_keys": "string",
    "channel": true,
    "first_name": true
  },
  "props": {},
  "last_password_update": 0,
  "last_picture_update": 0,
  "failed_attempts": 0,
  "mfa_active": true,
  "timezone": {
    "useAutomaticTimezone": true,
    "manualTimezone": "string",
    "automaticTimezone": "string"
  },
  "terms_of_service_id": "string",
  "terms_of_service_create_at": 0
},
{
  "id": "barackID",
  "create_at": 0,
  "update_at": 0,
  "delete_at": 0,
  "username": "barackUSERNAME",
  "first_name": "barackFIRSTNAME",
  "last_name": "barackLASTNAME",
  "nickname": "barackNICKNAME",
  "email": "string",
  "email_verified": true,
  "auth_service": "string",
  "roles": "barackROLES",
  "locale": "string",
  "notify_props": {
    "email": true,
    "push": "string",
    "desktop": "string",
    "desktop_sound": true,
    "mention_keys": "string",
    "channel": true,
    "first_name": true
  },
  "props": {},
  "last_password_update": 0,
  "last_picture_update": 0,
  "failed_attempts": 0,
  "mfa_active": true,
  "timezone": {
    "useAutomaticTimezone": true,
    "manualTimezone": "string",
    "automaticTimezone": "string"
  },
  "terms_of_service_id": "string",
  "terms_of_service_create_at": 0
}]
        """.trimIndent()
            )
        )

        val usersApi = retrofit.create(UsersApi::class.java)

        val call = usersApi.getTeamMembers("token", "team")

        val body = call.execute().body()!!

        assert(body[0].id == "almaID")
        assert(body[0].username == "almaUSERNAME")
        assert(body[0].firstName == "almaFIRSTNAME")
        assert(body[0].lastName == "almaLASTNAME")
        assert(body[0].nickname == "almaNICKNAME")
        assert(body[0].roles == "almaROLES")

        assert(body[1].id == "barackID")
        assert(body[1].username == "barackUSERNAME")
        assert(body[1].firstName == "barackFIRSTNAME")
        assert(body[1].lastName == "barackLASTNAME")
        assert(body[1].nickname == "barackNICKNAME")
        assert(body[1].roles == "barackROLES")

    }


    @Test
    fun testGetTeamsForUser() {
        mockWebServer.enqueue(
            MockResponse().setBody(
                """[

  {
    "id": "almaID",
    "create_at": 0,
    "update_at": 0,
    "delete_at": 0,
    "display_name": "almaDISPLAYNAME",
    "name": "almaNAME",
    "description": "string",
    "email": "string",
    "type": "almaTYPE",
    "allowed_domains": "string",
    "invite_id": "string",
    "allow_open_invite": true,
    "policy_id": "string"
  },
  {
    "id": "barackID",
    "create_at": 0,
    "update_at": 0,
    "delete_at": 0,
    "display_name": "barackDISPLAYNAME",
    "name": "barackNAME",
    "description": "string",
    "email": "string",
    "type": "barackTYPE",
    "allowed_domains": "string",
    "invite_id": "string",
    "allow_open_invite": true,
    "policy_id": "string"
  }


          ]
        """.trimIndent()
            )
        )

        val teamsApi = retrofit.create(TeamsApi::class.java)

        val call = teamsApi.getTeamsForUser("token", "user", 0, 0)

        val body = call.execute().body()!!

        assert(body[0].id == "almaID")
        assert(body[0].displayName == "almaDISPLAYNAME")
        assert(body[0].name == "almaNAME")
        assert(body[0].type == "almaTYPE")

        assert(body[1].id == "barackID")
        assert(body[1].displayName == "barackDISPLAYNAME")
        assert(body[1].name == "barackNAME")
        assert(body[1].type == "barackTYPE")


    }


    @Test
    fun testCreatePost() {
        mockWebServer.enqueue(
            MockResponse().setBody(
                """
  {
  "id": "almaID",
  "create_at": 0,
  "update_at": 0,
  "delete_at": 0,
  "edit_at": 0,
  "user_id": "almaUSERID",
  "channel_id": "almaCHANNEL",
  "root_id": "almaROOTID",
  "parent_id": "string",
  "original_id": "string",
  "message": "almaMESSAGE",
  "type": "almaTYPE",
  "props": { },
  "hashtag": "string",
  "filenames": [
  ],
  "file_ids": [
  ],
  "pending_post_id": "string",
  "metadata": {
  "embeds": [],
  "emojis": [],
  "files": [],
  "images": { },
  "reactions": []
  }
  }
        """.trimIndent()
            )
        )

        val postsApi = retrofit.create(PostsApi::class.java)

        val call = postsApi.createPost("token", NewPost("almaCHANNEL", "message", null))

        val body = call.execute().body()!!

        assert(body.id == "almaID")
        assert(body.channelId == "almaCHANNEL")
        assert(body.createAt!! == 0L)
        assert(body.message == "almaMESSAGE")
        assert(body.rootId == "almaROOTID")
        assert(body.type == "almaTYPE")
        assert(body.userId == "almaUSERID")

    }


    @Test
    fun testGetPosts() {
        mockWebServer.enqueue(
            MockResponse().setBody(
                """
{
  "order": [
    "almaPOSTID",
    "barackPOSTID"
  ],
  "posts": {
    "almaPOSTID": {
      "id": "almaPOSTID",
      "create_at": 0,
      "update_at": 0,
      "delete_at": 0,
      "edit_at": 0,
      "user_id": "almaUSERID",
      "channel_id": "almaCHANNELID",
      "root_id": "almaROOTID",
      "parent_id": "almaPARENTID",
      "original_id": "string",
      "message": "almaMESSAGE",
      "type": "almaTYPE",
      "props": {},
      "hashtag": "string",
      "filenames": [
        "string"
      ],
      "file_ids": [
        "string"
      ],
      "pending_post_id": "string",
      "metadata": {
        "embeds": [
          {
            "type": "image",
            "url": "string",
            "data": {}
          }
        ],
        "emojis": [
          {
            "id": "string",
            "creator_id": "string",
            "name": "string",
            "create_at": 0,
            "update_at": 0,
            "delete_at": 0
          }
        ],
        "files": [
          {
            "id": "string",
            "user_id": "string",
            "post_id": "string",
            "create_at": 0,
            "update_at": 0,
            "delete_at": 0,
            "name": "string",
            "extension": "string",
            "size": 0,
            "mime_type": "string",
            "width": 0,
            "height": 0,
            "has_preview_image": true
          }
        ],
        "images": {},
        "reactions": [
          {
            "user_id": "string",
            "post_id": "string",
            "emoji_name": "string",
            "create_at": 0
          }
        ]
      }
    },
    "barackPOSTID": {
      "id": "barackPOSTID",
      "create_at": 1,
      "update_at": 0,
      "delete_at": 0,
      "edit_at": 0,
      "user_id": "barackUSERID",
      "channel_id": "barackCHANNELID",
      "root_id": "barackROOTID",
      "parent_id": "string",
      "original_id": "string",
      "message": "barackMESSAGE",
      "type": "barackTYPE",
      "props": {},
      "hashtag": "string",
      "filenames": [
        "string"
      ],
      "file_ids": [
        "string"
      ],
      "pending_post_id": "string",
      "metadata": {
        "embeds": [
          {
            "type": "image",
            "url": "string",
            "data": {}
          }
        ],
        "emojis": [
          {
            "id": "string",
            "creator_id": "string",
            "name": "string",
            "create_at": 0,
            "update_at": 0,
            "delete_at": 0
          }
        ],
        "files": [
          {
            "id": "string",
            "user_id": "string",
            "post_id": "string",
            "create_at": 0,
            "update_at": 0,
            "delete_at": 0,
            "name": "string",
            "extension": "string",
            "size": 0,
            "mime_type": "string",
            "width": 0,
            "height": 0,
            "has_preview_image": true
          }
        ],
        "images": {},
        "reactions": [
          {
            "user_id": "string",
            "post_id": "string",
            "emoji_name": "string",
            "create_at": 0
          }
        ]
      }
    }
  },
  "next_post_id": "almaNEXTPOSTID",
  "prev_post_id": "almaPREVPOSTID"
}
        """.trimIndent()
            )
        )

        val postsApi = retrofit.create(PostsApi::class.java)

        val call = postsApi.getPostsInChannel("token", "almaCHANNEL", null, null, null, null)

        val body = call.execute().body()!!

        assert(body.nextPostId == "almaNEXTPOSTID")
        assert(body.prevPostId == "almaPREVPOSTID")
        assert(body.order!![0] == "almaPOSTID")
        assert(body.order!![1] == "barackPOSTID")


        assert(body.posts!!["almaPOSTID"]!!.id == "almaPOSTID")
        assert(body.posts!!["almaPOSTID"]!!.userId == "almaUSERID")
        assert(body.posts!!["almaPOSTID"]!!.type == "almaTYPE")
        assert(body.posts!!["almaPOSTID"]!!.userId == "almaUSERID")
        assert(body.posts!!["almaPOSTID"]!!.rootId == "almaROOTID")
        assert(body.posts!!["almaPOSTID"]!!.message == "almaMESSAGE")
        assert(body.posts!!["almaPOSTID"]!!.createAt == 0L)
        assert(body.posts!!["almaPOSTID"]!!.channelId == "almaCHANNELID")

        assert(body.posts!!["barackPOSTID"]!!.id == "barackPOSTID")
        assert(body.posts!!["barackPOSTID"]!!.userId == "barackUSERID")
        assert(body.posts!!["barackPOSTID"]!!.type == "barackTYPE")
        assert(body.posts!!["barackPOSTID"]!!.userId == "barackUSERID")
        assert(body.posts!!["barackPOSTID"]!!.rootId == "barackROOTID")
        assert(body.posts!!["barackPOSTID"]!!.message == "barackMESSAGE")
        assert(body.posts!!["barackPOSTID"]!!.createAt == 1L)
        assert(body.posts!!["barackPOSTID"]!!.channelId == "barackCHANNELID")


    }


    @Test
    fun testGetThreadByPost() {
        mockWebServer.enqueue(
            MockResponse().setBody(
                """
{
  "order": [
    "almaPOSTID",
    "barackPOSTID"
  ],
  "posts": {
    "almaPOSTID": {
      "id": "almaPOSTID",
      "create_at": 0,
      "update_at": 0,
      "delete_at": 0,
      "edit_at": 0,
      "user_id": "almaUSERID",
      "channel_id": "almaCHANNELID",
      "root_id": "almaROOTID",
      "parent_id": "almaPARENTID",
      "original_id": "string",
      "message": "almaMESSAGE",
      "type": "almaTYPE",
      "props": {},
      "hashtag": "string",
      "filenames": [
        "string"
      ],
      "file_ids": [
        "string"
      ],
      "pending_post_id": "string",
      "metadata": {
        "embeds": [
          {
            "type": "image",
            "url": "string",
            "data": {}
          }
        ],
        "emojis": [
          {
            "id": "string",
            "creator_id": "string",
            "name": "string",
            "create_at": 0,
            "update_at": 0,
            "delete_at": 0
          }
        ],
        "files": [
          {
            "id": "string",
            "user_id": "string",
            "post_id": "string",
            "create_at": 0,
            "update_at": 0,
            "delete_at": 0,
            "name": "string",
            "extension": "string",
            "size": 0,
            "mime_type": "string",
            "width": 0,
            "height": 0,
            "has_preview_image": true
          }
        ],
        "images": {},
        "reactions": [
          {
            "user_id": "string",
            "post_id": "string",
            "emoji_name": "string",
            "create_at": 0
          }
        ]
      }
    },
    "barackPOSTID": {
      "id": "barackPOSTID",
      "create_at": 1,
      "update_at": 0,
      "delete_at": 0,
      "edit_at": 0,
      "user_id": "barackUSERID",
      "channel_id": "barackCHANNELID",
      "root_id": "barackROOTID",
      "parent_id": "string",
      "original_id": "string",
      "message": "barackMESSAGE",
      "type": "barackTYPE",
      "props": {},
      "hashtag": "string",
      "filenames": [
        "string"
      ],
      "file_ids": [
        "string"
      ],
      "pending_post_id": "string",
      "metadata": {
        "embeds": [
          {
            "type": "image",
            "url": "string",
            "data": {}
          }
        ],
        "emojis": [
          {
            "id": "string",
            "creator_id": "string",
            "name": "string",
            "create_at": 0,
            "update_at": 0,
            "delete_at": 0
          }
        ],
        "files": [
          {
            "id": "string",
            "user_id": "string",
            "post_id": "string",
            "create_at": 0,
            "update_at": 0,
            "delete_at": 0,
            "name": "string",
            "extension": "string",
            "size": 0,
            "mime_type": "string",
            "width": 0,
            "height": 0,
            "has_preview_image": true
          }
        ],
        "images": {},
        "reactions": [
          {
            "user_id": "string",
            "post_id": "string",
            "emoji_name": "string",
            "create_at": 0
          }
        ]
      }
    }
  },
  "next_post_id": "almaNEXTPOSTID",
  "prev_post_id": "almaPREVPOSTID"
}
        """.trimIndent()
            )
        )

        val postsApi = retrofit.create(PostsApi::class.java)

        val call = postsApi.getThreadByPost("token", "almaPOSTID")

        val body = call.execute().body()!!

        assert(body.nextPostId == "almaNEXTPOSTID")
        assert(body.prevPostId == "almaPREVPOSTID")
        assert(body.order!![0] == "almaPOSTID")
        assert(body.order!![1] == "barackPOSTID")


        assert(body.posts!!["almaPOSTID"]!!.id == "almaPOSTID")
        assert(body.posts!!["almaPOSTID"]!!.userId == "almaUSERID")
        assert(body.posts!!["almaPOSTID"]!!.type == "almaTYPE")
        assert(body.posts!!["almaPOSTID"]!!.userId == "almaUSERID")
        assert(body.posts!!["almaPOSTID"]!!.rootId == "almaROOTID")
        assert(body.posts!!["almaPOSTID"]!!.message == "almaMESSAGE")
        assert(body.posts!!["almaPOSTID"]!!.createAt == 0L)
        assert(body.posts!!["almaPOSTID"]!!.channelId == "almaCHANNELID")

        assert(body.posts!!["barackPOSTID"]!!.id == "barackPOSTID")
        assert(body.posts!!["barackPOSTID"]!!.userId == "barackUSERID")
        assert(body.posts!!["barackPOSTID"]!!.type == "barackTYPE")
        assert(body.posts!!["barackPOSTID"]!!.userId == "barackUSERID")
        assert(body.posts!!["barackPOSTID"]!!.rootId == "barackROOTID")
        assert(body.posts!!["barackPOSTID"]!!.message == "barackMESSAGE")
        assert(body.posts!!["barackPOSTID"]!!.createAt == 1L)
        assert(body.posts!!["barackPOSTID"]!!.channelId == "barackCHANNELID")

    }

    @Test
    fun testGetAllChannels() {
        mockWebServer.enqueue(
            MockResponse().setBody(
                """
 
 [
   {
     "id": "almaID",
     "create_at": 0,
     "update_at": 0,
     "delete_at": 0,
     "team_id": "almaTEAMID",
     "type": "almaTYPE",
     "display_name": "almaDISPLAYNAME",
     "name": "almaNAME",
     "header": "string",
     "purpose": "string",
     "last_post_at": 0,
     "total_msg_count": 0,
     "extra_update_at": 0,
     "creator_id": "string",
     "team_display_name": "string",
     "team_name": "string",
     "team_update_at": 0,
     "policy_id": "string"
   }, {
        "id": "barackID",
        "create_at": 0,
        "update_at": 0,
        "delete_at": 0,
        "team_id": "barackTEAMID",
        "type": "barackTYPE",
        "display_name": "barackDISPLAYNAME",
        "name": "barackNAME",
        "header": "string",
        "purpose": "string",
        "last_post_at": 1,
        "total_msg_count": 0,
        "extra_update_at": 0,
        "creator_id": "string",
        "team_display_name": "string",
        "team_name": "string",
        "team_update_at": 0,
        "policy_id": "string"
      }
 ]
 
        """.trimIndent()
            )
        )

        val channelsApi = retrofit.create(ChannelsApi::class.java)

        val call = channelsApi.getAllChannels("token", 0, 0)

        val body = call.execute().body()!!

        assert(body[0].id == "almaID")
        assert(body[0].teamId == "almaTEAMID")
        assert(body[0].type == "almaTYPE")
        assert(body[0].displayName == "almaDISPLAYNAME")
        assert(body[0].name == "almaNAME")
        assert(body[0].lastPostat == 0L)

        assert(body[1].id == "barackID")
        assert(body[1].teamId == "barackTEAMID")
        assert(body[1].type == "barackTYPE")
        assert(body[1].displayName == "barackDISPLAYNAME")
        assert(body[1].name == "barackNAME")
        assert(body[1].lastPostat == 1L)


    }

    @Test
    fun testGetChannelsForTeam() {
        mockWebServer.enqueue(
            MockResponse().setBody(
                """
 
 [
   {
     "id": "almaID",
     "create_at": 0,
     "update_at": 0,
     "delete_at": 0,
     "team_id": "almaTEAMID",
     "type": "almaTYPE",
     "display_name": "almaDISPLAYNAME",
     "name": "almaNAME",
     "header": "string",
     "purpose": "string",
     "last_post_at": 0,
     "total_msg_count": 0,
     "extra_update_at": 0,
     "creator_id": "string",
     "team_display_name": "string",
     "team_name": "string",
     "team_update_at": 0,
     "policy_id": "string"
   }, {
        "id": "barackID",
        "create_at": 0,
        "update_at": 0,
        "delete_at": 0,
        "team_id": "almaTEAMID",
        "type": "barackTYPE",
        "display_name": "barackDISPLAYNAME",
        "name": "barackNAME",
        "header": "string",
        "purpose": "string",
        "last_post_at": 1,
        "total_msg_count": 0,
        "extra_update_at": 0,
        "creator_id": "string",
        "team_display_name": "string",
        "team_name": "string",
        "team_update_at": 0,
        "policy_id": "string"
      }
 ]
 
        """.trimIndent()
            )
        )

        val channelsApi = retrofit.create(ChannelsApi::class.java)

        val call = channelsApi.getChannelsForTeam("token", "almaTEAMID", 0, 0)

        val body = call.execute().body()!!

        assert(body[0].id == "almaID")
        assert(body[0].teamId == "almaTEAMID")
        assert(body[0].type == "almaTYPE")
        assert(body[0].displayName == "almaDISPLAYNAME")
        assert(body[0].name == "almaNAME")
        assert(body[0].lastPostat == 0L)

        assert(body[1].id == "barackID")
        assert(body[1].teamId == "almaTEAMID")
        assert(body[1].type == "barackTYPE")
        assert(body[1].displayName == "barackDISPLAYNAME")
        assert(body[1].name == "barackNAME")
        assert(body[1].lastPostat == 1L)


    }

    @Test
    fun testGetChannelsForUser() {
        mockWebServer.enqueue(
            MockResponse().setBody(
                """
 
 [
   {
     "id": "almaID",
     "create_at": 0,
     "update_at": 0,
     "delete_at": 0,
     "team_id": "almaTEAMID",
     "type": "almaTYPE",
     "display_name": "almaDISPLAYNAME",
     "name": "almaNAME",
     "header": "string",
     "purpose": "string",
     "last_post_at": 0,
     "total_msg_count": 0,
     "extra_update_at": 0,
     "creator_id": "string",
     "team_display_name": "string",
     "team_name": "string",
     "team_update_at": 0,
     "policy_id": "string"
   }, {
        "id": "barackID",
        "create_at": 0,
        "update_at": 0,
        "delete_at": 0,
        "team_id": "barackTYPE",
        "type": "barackTYPE",
        "display_name": "barackDISPLAYNAME",
        "name": "barackNAME",
        "header": "string",
        "purpose": "string",
        "last_post_at": 1,
        "total_msg_count": 0,
        "extra_update_at": 0,
        "creator_id": "string",
        "team_display_name": "string",
        "team_name": "string",
        "team_update_at": 0,
        "policy_id": "string"
      }
 ]
 
        """.trimIndent()
            )
        )

        val channelsApi = retrofit.create(ChannelsApi::class.java)

        val call = channelsApi.getChannelsForUser("token", "almaUSER", "almaTEAMID")

        val body = call.execute().body()!!

        assert(body[0].id == "almaID")
        assert(body[0].teamId == "almaTEAMID")
        assert(body[0].type == "almaTYPE")
        assert(body[0].displayName == "almaDISPLAYNAME")
        assert(body[0].name == "almaNAME")
        assert(body[0].lastPostat == 0L)

        assert(body[1].id == "barackID")
        assert(body[1].teamId == "barackTYPE")
        assert(body[1].type == "barackTYPE")
        assert(body[1].displayName == "barackDISPLAYNAME")
        assert(body[1].name == "barackNAME")
        assert(body[1].lastPostat == 1L)


    }

}