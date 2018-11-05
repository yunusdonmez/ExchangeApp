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
import com.example.yom.exchangeapp.dto.SmallVideoDTO
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.layout_video.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class VideoFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    var exoPlayer: SimpleExoPlayer? = null
    var currentVideo: String? = null
    var currentVideoTitle: String? = null
    private lateinit var playerView: PlayerView
    lateinit var mediaDataSourceFactory: DataSource.Factory
    private lateinit var bandwidthMeter: DefaultBandwidthMeter

    // autoplay = false
    private var autoPlay = false

    // used to remember the playback position
    private var currentWindow: Int = 0
    private var playbackPosition: Long = 0

    // constant fields for saving and restoring bundle
    val AUTOPLAY = "autoplay"
    val CURRENT_WINDOW_INDEX = "current_window_index"
    val PLAYBACK_POSITION = "playback_position"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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

        /* val videoList = ArrayList<YoutubeDTO>()
         videoList.add(YoutubeDTO("Cs992IBPIcA", "2019 Taslak Bütçesi\nVergiler"))
         videoList.add(YoutubeDTO("hesOSQ9tQeI", "Dünya Piyasalarında\nSon Durum"))
         videoList.add(YoutubeDTO("5eA8Sa6Q-k0", "Enflasyon&Faizler"))
         videoList.add(YoutubeDTO("F_CnCvSyzT4", "Tüketim Çöktü(Grafikli)"))*/
        currentVideo = param1
        currentVideoTitle = param2
        //initializePlayer()
        return fragmentVideoFragment
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtTitle.text = currentVideoTitle
        val list = ArrayList<SmallVideoDTO>()
        list.add(SmallVideoDTO("", "https://i.ytimg.com/vi/Cs992IBPIcA/hqdefault.jpg?sqp=-oaymwEZCPYBEIoBSFXyq4qpAwsIARUAAIhCGAFwAQ==&rs=AOn4CLA-Z9_1VsKR5nS8fui2ZhrUFRkW1A"))
        list.add(SmallVideoDTO("", "https://i.ytimg.com/vi/hesOSQ9tQeI/hqdefault.jpg?sqp=-oaymwEZCPYBEIoBSFXyq4qpAwsIARUAAIhCGAFwAQ==&rs=AOn4CLDf2Qu4kEqeEGTZMc4-Yvcz7K_vEQ"))
        list.add(SmallVideoDTO("", "https://i.ytimg.com/vi/5eA8Sa6Q-k0/hqdefault.jpg?sqp=-oaymwEZCPYBEIoBSFXyq4qpAwsIARUAAIhCGAFwAQ==&rs=AOn4CLDVG-f-3IkbSsSKxnfqL3aGYzSppA"))
        list.add(SmallVideoDTO("", "https://i.ytimg.com/vi/F_CnCvSyzT4/hqdefault.jpg?sqp=-oaymwEZCPYBEIoBSFXyq4qpAwsIARUAAIhCGAFwAQ==&rs=AOn4CLCocWFcoVBkFWXBaVkI80EAKNd2FA"))

        val smallVideoAdapter = SmallVideoAdapter(list, VideoFragment@ this)
        rvOtherVideos.apply {
            adapter = smallVideoAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        initializePlayer()
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
        if (Util.SDK_INT > 23) {
            initializePlayer()
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

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                VideoFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
