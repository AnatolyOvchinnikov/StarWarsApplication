package com.app.starwars.ui.global

import android.widget.Toast
import com.app.starwars.StarWarsApplication
import com.app.starwars.presentation.global.BaseView
import com.arellomobile.mvp.MvpAppCompatActivity
import timber.log.Timber
import java.lang.ref.WeakReference


abstract class BaseActivity : MvpAppCompatActivity(), BaseView {
    private var previousToastWeakReference: WeakReference<Toast>? = null
    override fun showError(error: Throwable) {
        Timber.e(error)
        if (error != null) {
            showError(error.localizedMessage)
        }
    }

    fun showError(errorMessage: String) {
        showMessage(errorMessage)
    }

    fun showMessage(message: String) {
        if (message.isBlank()) {
            return
        }

        val previous = if (previousToastWeakReference != null) previousToastWeakReference?.get() else null
        if (previous != null) {
            previous.cancel()
        }

        val toastText = message
        val toast = Toast.makeText(StarWarsApplication.applicationContext(), toastText, Toast.LENGTH_SHORT)
        toast.show()

        previousToastWeakReference = WeakReference<Toast>(toast)
    }
}