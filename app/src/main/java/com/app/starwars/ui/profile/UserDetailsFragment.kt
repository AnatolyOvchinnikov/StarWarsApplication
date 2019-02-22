package com.app.starwars.ui.profile


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.starwars.R
import com.app.starwars.databinding.FragmentPersonDetailsBinding
import com.app.starwars.entity.User
import com.app.starwars.presentation.profile.UserDetailsPresenter
import com.app.starwars.presentation.profile.UserDetailsView
import com.app.starwars.model.system.ConnectivityReceiver
import com.app.starwars.ui.MainActivity
import com.app.starwars.ui.global.BaseFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_person_details.*

class UserDetailsFragment : BaseFragment(), UserDetailsView, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var user: User

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "UserDetailsPresenter")
    lateinit var presenter: UserDetailsPresenter

    @ProvidePresenter(type = PresenterType.GLOBAL, tag = "UserDetailsPresenter")
    fun provideUserDetailsPresenter() = UserDetailsPresenter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPersonDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_person_details, container, false)
        binding.presenter = presenter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val person = arguments?.let { UserDetailsFragmentArgs.fromBundle(it).person }
        person?.let {
            bindData(it)
        }
        (activity as MainActivity).supportActionBar?.setTitle(getString(R.string.person_details))
        swipeView.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        this.user.userId?.let { presenter.loadList(it) }
        (activity as MainActivity).errorWidget.showContainer(!ConnectivityReceiver.isConnected())
    }

    private fun bindData(user: User) {
        this.user = user
        presenter.userObservable.set(user)
        presenter.setVisited()
    }

    override fun showProgress(progress: Boolean) {
        swipeView.isRefreshing = progress
    }

    override fun showError(error: Throwable) {
        super.showError(error)
    }
}
