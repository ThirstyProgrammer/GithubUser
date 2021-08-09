package id.bachtiar.harits.githubuser.util

import android.util.Log
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

abstract class AppBarStateChangeListener : AppBarLayout.OnOffsetChangedListener {

    private var mCurrentState = State.IDLE

    enum class State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        Log.d("VALUE", verticalOffset.toString())
        Log.d("TOTALRANGE", appBarLayout!!.totalScrollRange.toString())
        if (verticalOffset == 0) {
            if (mCurrentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED)
            }
            mCurrentState = State.EXPANDED
            Log.d("STATUS", "EXPANDED")
        } else if (abs(verticalOffset) >= (appBarLayout.totalScrollRange - 48)) {
            if (mCurrentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED)
            }
            mCurrentState = State.COLLAPSED
            Log.d("STATUS", "COLLAPSED")
        }
    }

    abstract fun onStateChanged(appBarLayout: AppBarLayout, state: State)
}