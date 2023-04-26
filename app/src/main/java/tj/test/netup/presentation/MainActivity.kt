package tj.test.netup.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.viewpager.widget.ViewPager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import dagger.hilt.android.AndroidEntryPoint
import tj.test.netup.databinding.ActivityMainBinding
import tj.test.netup.extensions.MediaUIList
import tj.test.netup.extensions.showToast

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()
    private lateinit var pagerAdapter: ChannelPagerAdapter

    private var player: ExoPlayer? = null
    private var position: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPlayer()
        initObservers()
    }

    private fun initPlayer() {
        player = ExoPlayer.Builder(this) // <- context
            .build()
    }

    private fun initObservers() = with(viewModel) {
        media.observe(this@MainActivity) {
            when (it.status) {
                Status.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) {
                        setupMedia(it.data)
                    }
                }
                Status.ERROR -> {
                    showToast(it.message)
                }
            }
        }

        isLoading.observe(this@MainActivity) { isLoading ->
            binding.playerView.isVisible = !isLoading
            binding.channelViewpagerContainer.isVisible = !isLoading
            binding.pbLoading.isVisible = isLoading
        }
    }

    private fun setupMedia(data: MediaUIList) {
        pagerAdapter = ChannelPagerAdapter(this, data)
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.currentItem = 0
        binding.viewPager.addOnPageChangeListener(pageChangeListener)
        pageChangeListener.onPageSelected(0)

        binding.ivPrev.setOnClickListener {
            var tab = binding.viewPager.currentItem
            if (tab > 0) {
                tab--
                binding.viewPager.currentItem = tab
            } else {
                binding.viewPager.currentItem = tab
            }
        }

        binding.ivNext.setOnClickListener {
            var tab = binding.viewPager.currentItem
            tab++
            binding.viewPager.currentItem = tab
        }
    }

    private val pageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageScrollStateChanged(state: Int) {}
        override fun onPageSelected(position: Int) {
            binding.ivPrev.isVisible = position != 0
            binding.ivNext.isVisible = position != pagerAdapter.count - 1
            setupPlayer(pagerAdapter.getItem()[position].url)
        }
    }


    private fun setupPlayer(url: String) {
        // create a media item.
        val mediaItem = MediaItem.Builder()
            .setUri(url)
            .build()

        // Create a media source and pass the media item
        val mediaSource = HlsMediaSource.Factory(
            DefaultDataSource.Factory(this) // <- context
        ).createMediaSource(mediaItem)

        // Finally assign this media source to the player
        player?.let {
            it.setMediaSource(mediaSource)
            it.playWhenReady = true
            it.seekTo(0, 0L)
            it.prepare()
            binding.playerView.player = it
        }
    }

    private fun releasePlayer() {
        player?.let {
            position = it.currentPosition
            it.playWhenReady = it.playWhenReady
            it.stop()
            it.release()
            player = null
        }
    }

    override fun onPause() {
        super.onPause()
        player?.let {
            if (it.playWhenReady) {
                position = it.currentPosition
                it.playWhenReady = false
                it.pause()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        player?.let {
            it.seekTo(position)
            it.playWhenReady = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }
}