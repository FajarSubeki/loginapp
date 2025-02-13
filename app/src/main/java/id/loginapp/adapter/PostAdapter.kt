package id.loginapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.loginapp.R
import id.loginapp.data.Post

class PostAdapter(private val posts: MutableList<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>(){

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvTitle)
        val body: TextView = view.findViewById(R.id.tvBody)
        val imgMenu: ImageView = view.findViewById(R.id.imgMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_data, parent, false)
        return PostViewHolder(view)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.title.text = post.title
        holder.body.text = post.body

        holder.imgMenu.setOnClickListener {
            posts.removeAt(position)
            notifyDataSetChanged()
        }
    }

}