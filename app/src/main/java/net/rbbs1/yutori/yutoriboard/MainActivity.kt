package net.rbbs1.yutori.yutoriboard

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.support.v7.widget.GridLayoutManager



class MainActivity : AppCompatActivity(){
    var mRecyclerView: RecyclerView? = null
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    var page = 0
    var threads: MutableList<Thread> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }


        this.mRecyclerView = findViewById(R.id.threadList) as RecyclerView
        this.mSwipeRefreshLayout = findViewById(R.id.swipe_refresh) as SwipeRefreshLayout

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        this.mRecyclerView?.setHasFixedSize(true);


        // Listenerをセット
        if (mRecyclerView != null && mSwipeRefreshLayout != null){
            // use a linear layout manager
            this.mLayoutManager = LinearLayoutManager(this);
            this.mRecyclerView!!.setLayoutManager(this.mLayoutManager)

            mSwipeRefreshLayout?.setOnRefreshListener {
                Threads(this.mRecyclerView!!, mSwipeRefreshLayout!!, threads).execute(Threads.url().string())
            }


            mRecyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val totalCount = recyclerView!!.adapter.itemCount
                    val childCount = recyclerView.childCount
                    val layoutManager = recyclerView.layoutManager

                    if (layoutManager is LinearLayoutManager) { // LinearLayoutManager
                        val firstPosition = layoutManager.findFirstVisibleItemPosition() // RecyclerViewの一番上に表示されているアイテムのポジション
                        if (totalCount == childCount + firstPosition) {
                            // ページング処理
                            // LinearLayoutManagerを指定している時のページング処理
                            if (mSwipeRefreshLayout != null && !mSwipeRefreshLayout!!.isRefreshing) {
                                Threads(mRecyclerView!!, mSwipeRefreshLayout!!, threads).execute(Threads.url(page = page + 1).string())
                                page++
                            }
                        }
                    }
                }
            })
            Threads(this.mRecyclerView!!, mSwipeRefreshLayout!!, threads).execute(Threads.url().string())
            page = 1
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
