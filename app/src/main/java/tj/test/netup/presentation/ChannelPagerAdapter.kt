package tj.test.netup.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import tj.test.netup.databinding.ChannelItemBinding
import java.util.*

class ChannelPagerAdapter(private val context: Context,
                          private val mediaList: List<MediaUI>) : PagerAdapter() {

    override fun getCount(): Int = mediaList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = ChannelItemBinding
            .inflate(LayoutInflater.from(context), container, false)
        binding.tvName.text = mediaList[position].name
        Objects.requireNonNull(container).addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

    fun getItem() = mediaList
}