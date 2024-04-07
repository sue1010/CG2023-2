package com.example.openglesbook

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.openglesbook.databinding.ActivityListBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class ListActivity : Activity() {

    private lateinit var  binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        try {
            // get list view
            val listView = binding.exampleList
            // get json object from asset
            val jsonObject = readJSONFromAsset("list") ?: return
            // get json array example list
            val examples = jsonObject.getJSONArray("examples")
            // set adapter of list view
            listView.adapter = CustomAdapter(applicationContext, examples)
            // set listener of list view
            listView.onItemClickListener =
                OnItemClickListener { adapterView, view, i, l -> // create intent with extra example ID
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.putExtra("ExampleID", i)

                    // start main activity
                    applicationContext.startActivity(intent)
                }

            //listView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            //    val intent = Intent(applicationContext, MainActivity::class.java)
            //    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            //    intent.putExtra("ExampleID", position)

                // start main activity
            //    applicationContext.startActivity(intent)
            //}
        } catch (e: JSONException) {
            // print error log
            Log.e("OpenGL ES 3.0", "Fail to get examples from JSON object")
            e.printStackTrace()
        }
    }

    private fun readJSONFromAsset(filename: String): JSONObject? {
        var json: String?
        try {
            val inputStream: InputStream = assets.open("$filename.json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charsets.UTF_8)

            return JSONObject(json)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } catch (e: JSONException) {
            e.printStackTrace()
            return null
        }
    }


    private inner class CustomAdapter(context: Context, examples: JSONArray) : BaseAdapter() {
        private var examples: JSONArray
        private var inflater: LayoutInflater

        init {
            this.examples = examples
            this.inflater = LayoutInflater.from(context)
        }
        override fun getCount(): Int {
            return examples.length()
        }

        override fun getItem(position: Int): Any? {
            return null
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            // get views
            var convertView = inflater.inflate(R.layout.list_item, null)
            var title: TextView = convertView.findViewById(R.id.title)
            var desc: TextView = convertView.findViewById(R.id.desc)

            try {
                // get example json object
                val jsonObject: JSONObject = examples.getJSONObject(position)

                // set title and description
                title.text = jsonObject.getString("title")
                desc.text = jsonObject.getString("desc")
            } catch (e: JSONException) {
                // print error log
                Log.e("OpenGL ES 3.0", "Fail to get JSON object from examples")
                e.printStackTrace()
            }
            // return convertView
            return convertView
        }
    }
}