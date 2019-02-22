package com.app.starwars.ui.lists


import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.SearchView
import android.view.*
import com.app.starwars.R
import com.app.starwars.entity.UserWithMetadata
import com.app.starwars.presentation.main.StarWarsPresenter
import com.app.starwars.presentation.main.StarWarsView
import com.app.starwars.model.system.ConnectivityReceiver
import com.app.starwars.ui.MainActivity
import com.app.starwars.ui.global.BaseFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_persons_list.*

class PersonsListFragment : BaseFragment(), StarWarsView, SwipeRefreshLayout.OnRefreshListener {

    private val adapter = UsersAdapter()

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "StarWarsPresenter")
    lateinit var presenter: StarWarsPresenter

    @ProvidePresenter(type = PresenterType.GLOBAL, tag = "StarWarsPresenter")
    fun provideStarWarsPresenter() = StarWarsPresenter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.app.starwars.R.layout.fragment_persons_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()
        setHasOptionsMenu(true)
        swipeView.setOnRefreshListener(this)
        (activity as MainActivity).supportActionBar?.setTitle(getString(R.string.persons))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear();
        inflater.inflate(com.app.starwars.R.menu.app_menu, menu);


        val mSearch = menu.findItem(R.id.search)

        val mSearchView = mSearch.actionView as SearchView
        mSearchView.setQueryHint("Search")

        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                presenter.loadList(newText)
                return true
            }
        })
    }

    private fun initAdapter() {
        list.adapter = adapter
        presenter.loadList()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun loadItems(list: PagedList<UserWithMetadata>) {
        adapter.submitList(list)
    }

    override fun onRefresh() {
        presenter.loadList()
        (activity as MainActivity).errorWidget.showContainer(!ConnectivityReceiver.isConnected())
    }

    override fun showProgress(progress: Boolean) {
        swipeView.isRefreshing = progress
    }

    override fun showError(error: Throwable) {
        super.showError(error)
    }
}
