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

class MainActivity : AppCompatActivity(){
    var mRecyclerView: RecyclerView? = null
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var mSwipeRefreshLayout: SwipeRefreshLayout? = null

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

        // use a linear layout manager
        this.mLayoutManager = LinearLayoutManager(this);
        this.mRecyclerView?.setLayoutManager(this.mLayoutManager)

        // Listenerをセット
        if (mRecyclerView != null){
            mSwipeRefreshLayout?.setOnRefreshListener(
                    SwipeRefreshLayout.OnRefreshListener{
                        Threads(this.mRecyclerView!!, mSwipeRefreshLayout).execute(Threads.url().string())
                    }
            )
        }

        if (mRecyclerView != null) {
            Threads(this.mRecyclerView!!, mSwipeRefreshLayout).execute(Threads.url().string())
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
