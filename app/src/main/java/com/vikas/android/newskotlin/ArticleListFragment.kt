package com.vikas.android.newskotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.vikas.android.newskotlin.databinding.FragmentArticleListBinding
import kotlinx.coroutines.launch


class ArticleListFragment : Fragment() {

    private var _binding: FragmentArticleListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val articleListViewModel: ArticleListViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentArticleListBinding.inflate(inflater, container, false)

        binding.articleRecyclerView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                articleListViewModel.articles.collect { articles ->
                    binding.articleRecyclerView.adapter = ArticleListAdapter(articles)
                }
            }
        }

        setupMenu()
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_article_list, menu)
                val searchItem = menu.findItem(R.id.menu_item_search)
                val searchView = searchItem.actionView as? SearchView

                searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        articleListViewModel.setQuery(query ?: "")
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean = false
                })

                searchView?.setOnCloseListener{
                    articleListViewModel.setQuery("")
                    false
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return menuItem.itemId == R.id.menu_item_search
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}