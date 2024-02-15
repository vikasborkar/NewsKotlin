package com.vikas.android.newskotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.vikas.android.newskotlin.data.Article
import com.vikas.android.newskotlin.databinding.ListItemArticleBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ArticleHolder(private val binding: ListItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(article: Article) {
        binding.articleTitle.text = article.title
        binding.articleImage.load(article.urlToImage){
            placeholder(androidx.appcompat.R.color.material_grey_100)
            error(R.drawable.ic_image_placeholder)
        }
        article.publishedAt?.let {
            binding.articleDate.text = SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.ENGLISH).format(article.publishedAt)
        }
    }
}


class ArticleListAdapter(private val articles: List<Article>) : RecyclerView.Adapter<ArticleHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemArticleBinding.inflate(inflater, parent, false)
        return ArticleHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) = holder.bind(articles[position])

    override fun getItemCount(): Int = articles.size

}