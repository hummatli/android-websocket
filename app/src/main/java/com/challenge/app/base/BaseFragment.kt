package com.challenge.app.base

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.challenge.app.R
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

abstract class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
    }

    /**
     * This function is called at the appropriate time to initialise the views
     * i.e. set listeners...
     *
     * @param rootView – The fragment's root view
     */
    protected abstract fun setupViews(rootView: View)

    protected fun showError(throwable: Throwable, rootView: View, action: (() -> Any)? = null) {
        val snackBar = Snackbar.make(
            rootView,
            throwable.toString(),
            Snackbar.LENGTH_SHORT
        )

        val tv = snackBar.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

        if (action != null) {
            snackBar.setAction(R.string.action_close) {
                action.invoke()
            }
            snackBar.duration = Snackbar.LENGTH_INDEFINITE
        }
        snackBar.show()

        Timber.e(throwable)
    }
}
