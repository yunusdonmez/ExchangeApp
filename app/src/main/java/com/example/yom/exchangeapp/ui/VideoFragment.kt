package com.example.yom.exchangeapp.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.example.yom.exchangeapp.R
import com.example.yom.exchangeapp.adapter.SmallVideoAdapter
import com.example.yom.exchangeapp.dto.VideosDTO
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.util.Util
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.layout_video.*

class VideoFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    private var exoPlayer: SimpleExoPlayer? = null
    var currentVideo: String? = null
    var currentVideoTitle: String? = null
    private lateinit var playerView: PlayerView
    lateinit var mediaDataSourceFactory: DataSource.Factory
    private lateinit var bandwidthMeter: DefaultBandwidthMeter

    val videoList = ArrayList<VideosDTO>()
    // autoplay = false
    private var autoPlay = false

    // used to remember the playback position
    private var currentWindow: Int = 0
    private var playbackPosition: Long = 0

    // constant fields for saving and restoring bundle
    private val AUTOPLAY = "autoplay"
    private val CURRENT_WINDOW_INDEX = "current_window_index"
    private val PLAYBACK_POSITION = "playback_position"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("value")
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(data: DataSnapshot) {
                for (dataItem in data.children) {
                    //Log.e("Videolist", dataItem.child("baslik").value.toString())
                    //Log.i("dataSnapshot", "")
                    videoList.add(VideosDTO(dataItem.child("baslik").value.toString(), dataItem.child("link").value.toString(), dataItem.child("resim").value.toString()))
                }
                currentVideo = videoList[0].videoLink
                currentVideoTitle = videoList[0].viodeTitle
            }
        })
        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION, 0)
            currentWindow = savedInstanceState.getInt(CURRENT_WINDOW_INDEX, 0)
            autoPlay = savedInstanceState.getBoolean(AUTOPLAY, false)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val fragmentVideoFragment = inflater.inflate(R.layout.fragment_video, container, false)
        playerView = fragmentVideoFragment.findViewById(R.id.simpleExoPlayerView)
        return fragmentVideoFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val smallVideoAdapter = SmallVideoAdapter(videoList, VideoFragment@ this)
        rvOtherVideos.apply {
            adapter = smallVideoAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    fun initializePlayer() {
        txtTitle.text = currentVideoTitle
        bandwidthMeter = DefaultBandwidthMeter()
        mediaDataSourceFactory = buildDataSourceFactory(true)
        if (exoPlayer == null)
            exoPlayer = ExoPlayerFactory.newSimpleInstance(DefaultRenderersFactory(context), DefaultTrackSelector(), DefaultLoadControl())

        playerView.apply {
            player = exoPlayer
            useController = true
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
        }
        object : YouTubeExtractor(context!!) {
            override fun onExtractionComplete(ytfiles: SparseArray<YtFile>?, vmeta: VideoMeta) {
                val itag = 18// 135:480p,18:360p,17:144p
                if (ytfiles?.get(itag) != null && ytfiles.get(itag).url != null) {
                    val downloadUrl = ytfiles.get(itag).url
                    val extractorFactory = DefaultExtractorsFactory()
                    val mediaSource = ExtractorMediaSource.Factory(mediaDataSourceFactory)
                            .setExtractorsFactory(extractorFactory)
                            .createMediaSource(Uri.parse(downloadUrl))
                    exoPlayer?.prepare(mediaSource, true, true)
                    exoPlayer?.seekTo(playbackPosition)
                } else {
                    Toast.makeText(context, "Video Başlatılamadı", Toast.LENGTH_LONG).show()
                }
            }
        }.extract("http://youtube.com/watch?v=$currentVideo", true, true)
        exoPlayer?.playWhenReady = autoPlay
        exoPlayer?.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) startPlayer()
        else releasePlayer()
    }

    override fun onResume() {
        super.onResume()
        //hideSystemUi()
        if (userVisibleHint) startPlayer()
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) releasePlayer()
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    //exoplayer çıkış
    fun releasePlayer() {
        if (exoPlayer != null) {
            playbackPosition = exoPlayer!!.currentPosition
            currentWindow = exoPlayer!!.currentWindowIndex
            autoPlay = exoPlayer!!.playWhenReady
            exoPlayer!!.release()
            autoPlay = false
            exoPlayer = null
        }
    }

    private fun startPlayer() {
        if (currentVideo != null) {
            if (Util.SDK_INT > 23) {
                initializePlayer()
            }
        }
    }

    fun resetProperties() {
        currentWindow = 0
        playbackPosition = 0
    }

    private fun buildDataSourceFactory(useBandwidthMeter: Boolean): DataSource.Factory = buildDataSourceFactory(if (useBandwidthMeter) bandwidthMeter else null)

    private fun buildDataSourceFactory(bandwidthMeter: DefaultBandwidthMeter?): DataSource.Factory = DefaultDataSourceFactory(context, bandwidthMeter, buildHttpDataSourceFactory(bandwidthMeter))

    private fun buildHttpDataSourceFactory(bandwidthMeter: DefaultBandwidthMeter?): HttpDataSource.Factory? = DefaultHttpDataSourceFactory("exoplayer-codelab", bandwidthMeter)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is VideoFragment.OnFragmentInteractionListener)
            listener = context

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        /*
       * A simple configuration change such as screen rotation will destroy this activity
       * so we'll save the player state here in a bundle (that we can later access in onCreate) before everything is lost
       * NOTE: we cannot save player state in onDestroy like we did in onPause and onStop
       * the reason being our activity will be recreated from scratch and we would have lost all members (e.g. variables, objects) of this activity
       */
        if (exoPlayer == null) {
            outState.putLong(PLAYBACK_POSITION, playbackPosition)
            outState.putInt(CURRENT_WINDOW_INDEX, currentWindow)
            outState.putBoolean(AUTOPLAY, autoPlay)
        }
    }

    // This is just an implementation detail to have a pure full screen experience. Nothing fancy here
    /* @SuppressLint("InlinedApi")
     private fun hideSystemUi() {
         playerView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                 or View.SYSTEM_UI_FLAG_FULLSCREEN
                 or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                 or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                 or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                 or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
     }*/
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }
}
