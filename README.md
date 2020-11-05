# EndlessListScrolling_KotlinExample
Endless List Scrolling With RecylerView to use Kotlin Example

You can find the complete implemention step by step at my [Medium Link](https://innovatingideas.medium.com/endless-list-scrolling-with-recyclerview-or-listview-in-kotlin-in-4-steps-with-the-easiest-way-ad8ab1e204e7) here.

Also see ![Demo application](https://github.com/abhi10jul/EndlessListScrolling_KotlinExample/blob/master/demo.gif)

                                             ***************************
Today’s, Every mostly applications have Endless list scrolling features there i.e INSTAGRAM, FACEBOOK, FLIPKART, and so on…

So that this common feature in the application is to load automatically more items as the user scrolls through the items endless list.

### There are so many prons like
<br>1: You don’t want to make a huge API request call to the server, making your user wait infinitely or much time.</br>
<br>2: You don’t want to make your UI or App look like the 90's generation with Next and previous button.</br>

In such a scenario implementing an endless scroll might be the ideal choice. This choice is working like to provide the appearance of endless scrolling,
it’s important to fetch data before the user gets to the end of the list. That the time you can add more value to call the next RESTAPI request to the server
and get data and therefore need to append more data and show the user without block user UI and get the best user experience.If We have to implement an Endless
list in our code that a big task and more difficult, especially for beginners. So here the solution with a simple and easy way to implement Endless list 
scrolling in our code within few steps.

                                         *****************************
<H3>Sample of API URL:</H3>

### {{baseURL}}/api/{{endpoint}}??page={{currentPageNumber}}

<H3>Sample of API response:</H3>

        {
          page: 1, //This is current page
          pages: 108, //This is total pages
          otherObjectKey: [otherResponseObject]
        }
        
<H2>Page and Pages is mandatory in API response to use Endless list Scrolling.</H2>

<H1>STEP 1:</H1>
First, You have implemented Retrofit APICLIENT and API SERVICE to establish a connection and get data from the server. Here, We need the below classes and dependencies.
### i)Dependencies
### ii) ApiClient
### iii)ApiService

<H3>Add Dependencies in your build.gradle</H3>

        //TODO Retrofit
        implementation 'com.squareup.retrofit2:retrofit:2.9.0'
        implementation 'com.google.code.gson:gson:2.8.6'
        implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
        
        //TODO GLIDE implementation for fetch images
        implementation 'com.github.bumptech.glide:glide:4.11.0'
        annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'
        implementation 'androidx.cardview:cardview:1.0.0'
        
        //TODO For dimension
        implementation 'com.intuit.sdp:sdp-android:1.0.6'
        
<H3>ApiClient.kt</H3>

        package com.github.abhi10jul.endlesslistscrolling_kotlinexample

        import retrofit2.Callback
        import retrofit2.Retrofit
        import retrofit2.converter.gson.GsonConverterFactory

        /**
         *  @author abhishek srivastava.
         */
                class ApiClient {
            private val BASE_URL = "{{ADD YOUR BASE URL}}"
            private var apiService: ApiService = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)

            fun callTvShowListRequest(page: Int, callback: Callback<ShowModel>) {
                apiService.getTvShowList(page).enqueue(callback)
            }
         }
         
<H3>ApiService.kt</H3>

        package com.github.abhi10jul.endlesslistscrolling_kotlinexample

        import retrofit2.Call
        import retrofit2.http.GET
        import retrofit2.http.Query

        /**
         *  @author abhishek srivastava.
         */
        interface ApiService {

            @GET("{{ADD YOUR END POINT}}")
            fun getTvShowList(@Query("page") page: Int): Call<ShowModel>

        }
        
<H1>STEP 2:</H1>
Add Your model class base on response and make sure ‘PAGE’ & ‘PAGES’ is there in response.
### i) ShowModel.kt
### ii) TvShow.kt

<H3>ShowModel.kt</H3>

        package com.github.abhi10jul.endlesslistscrolling_kotlinexample

        import com.google.gson.annotations.Expose
        import com.google.gson.annotations.SerializedName

        /**
         *  @author abhishek srivastava.
         */
        data class ShowModel(
            @SerializedName("total")
            @Expose val total: String,
            @SerializedName("page")
            @Expose
            val page: Int,
            @SerializedName("pages")
            @Expose
            val pages: Int,
            @SerializedName("tv_shows")
            @Expose
            val tvShows: ArrayList<TvShow>
        )
        
<H3>TVShow.kt</H3>

        data class TvShow(
            @SerializedName("id")
            @Expose
            val id: Int,
            @SerializedName("name")
            @Expose
            val name: String,
            @SerializedName("status")
            @Expose
            val status: String,
            @SerializedName("image_thumbnail_path")
            @Expose val imageThumbnailPath: String
        )
        
<H1>STEP 3:<H1>
Now We can design the UI part for “main view” and “child view”.
        
### i)activity_main.xml
### ii)show_list_raw.xml

<H3>activity_main.xml</H3>

        <?xml version="1.0" encoding="utf-8"?>
        <layout xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:tools="http://schemas.android.com/tools"
            tools:context=".MainActivity">

            <RelativeLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent">

                <TextView
                    android:id="@+id/tvTitle"
                    android:layout_width="match_parent"
                    android:layout_height="@dimen/_40sdp"
                    android:layout_alignParentTop="true"
                    android:background="@color/purple_200"
                    android:gravity="center_vertical|center_horizontal"
                    android:text="Endless Scrolling RecyclerView"
                    android:textAppearance="@style/TextAppearance.AppCompat.Large"
                    android:textColor="@color/white"
                    android:textStyle="bold" />

                <ProgressBar
                    android:id="@+id/defaultProgress"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_below="@+id/tvTitle"
                    android:layout_centerHorizontal="true"
                    android:indeterminate="true"
                    android:visibility="gone" />

                <androidx.recyclerview.widget.RecyclerView
                    android:id="@+id/recyclerview"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:layout_above="@id/loadMoreProgress"
                    android:layout_below="@+id/defaultProgress" />

                <ProgressBar
                    android:id="@+id/loadMoreProgress"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_alignParentBottom="true"
                    android:layout_centerHorizontal="true"
                    android:indeterminate="true"
                    android:visibility="gone" />
            </RelativeLayout>
        </layout>
        
<H3>show_list_raw.xml</H3>

        <?xml version="1.0" encoding="utf-8"?>
        <layout xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto">

            <androidx.cardview.widget.CardView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginStart="@dimen/_5sdp"
                android:layout_marginTop="@dimen/_2sdp"
                android:layout_marginEnd="@dimen/_5sdp"
                android:layout_marginBottom="@dimen/_2sdp"
                app:cardCornerRadius="@dimen/_20sdp">

                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:gravity="center_vertical|center_horizontal"
                    android:orientation="horizontal">

                    <ImageView
                        android:id="@+id/thumbnail"
                        android:layout_width="match_parent"
                        android:layout_height="@dimen/_70sdp"
                        android:layout_gravity="center_vertical|center_horizontal"
                        android:layout_weight="1.7"
                        android:contentDescription="@string/app_name"
                        android:scaleType="centerCrop" />


                    <TextView
                        android:id="@+id/tvTitle"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:layout_gravity="center_vertical|center_horizontal"
                        android:layout_weight="1"
                        android:background="@null"
                        android:fontFamily="sans-serif"
                        android:gravity="center_horizontal|center_vertical"
                        android:padding="@dimen/_2sdp"
                        android:singleLine="true"
                        android:textAppearance="@style/TextAppearance.AppCompat.Medium"
                        android:textColor="@color/black" />
                </LinearLayout>
            </androidx.cardview.widget.CardView>
        </layout>
        
<H1>STEP 4:</H1>
Now We can set up the adapter to bind data in RecylerView. Here I have created a custom way to the adapter for updating list because sometimes in KOTLIN notify method i.e ‘notifyDataSetChanged(), notifyItemRangeInserted() and so on…’ is not worked. So I can use a custom method to update the list.
So I have created “fun updateList(tvShowList: ArrayList<TvShow>, oldCount: Int, tvShowListSize: Int)” in MainActivityAdapter class.
And also implement logic in “MainActivity.kt” to handle all “Endless list scrolling ”.
        
### i)MainActivityAdapter.kt
### ii)MainActivity.kt

<H3>MainActivityAdapter.kt</H3>

        package com.github.abhi10jul.endlesslistscrolling_kotlinexample

        import android.view.LayoutInflater
        import android.view.View
        import android.view.ViewGroup
        import androidx.recyclerview.widget.RecyclerView
        import com.bumptech.glide.Glide
        import com.github.abhi10jul.endlesslistscrolling_kotlinexample.databinding.ShowListRawBinding

        /**
         *  @author abhishek srivastava.
         */
        class MainActivityAdapter() :
            RecyclerView.Adapter<MainActivityAdapter.MainActivityAdapterHolder>() {
            private var tvShowList = ArrayList<TvShow>()

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityAdapterHolder {
                return MainActivityAdapterHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.show_list_raw, parent, false)
                )
            }

            override fun onBindViewHolder(holder: MainActivityAdapterHolder, position: Int) {
                holder.binding.tvTitle.text = "${tvShowList[position].name}"
                Glide
                    .with(holder.itemView)
                    .load(tvShowList[position].imageThumbnailPath)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(holder.binding.thumbnail)
            }

            override fun getItemCount(): Int {
                return tvShowList.size
            }

            class MainActivityAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                val binding = ShowListRawBinding.bind(itemView)
            }

            fun updateList(tvShowList: ArrayList<TvShow>, oldCount: Int, tvShowListSize: Int) {
                this.tvShowList = tvShowList
                notifyDataSetChanged()
                notifyItemRangeInserted(oldCount, tvShowListSize)
            }
        }
        
<H3>MainActivity.kt</H3>

        package com.github.abhi10jul.endlesslistscrolling_kotlinexample

        import android.os.Bundle
        import android.util.Log
        import android.view.View
        import androidx.appcompat.app.AppCompatActivity
        import androidx.databinding.DataBindingUtil
        import androidx.recyclerview.widget.LinearLayoutManager
        import androidx.recyclerview.widget.RecyclerView
        import com.github.abhi10jul.endlesslistscrolling_kotlinexample.databinding.ActivityMainBinding
        import retrofit2.Call
        import retrofit2.Callback
        import retrofit2.Response

        class MainActivity : AppCompatActivity() {
            private val TAG = MainActivity::class.java.simpleName
            private lateinit var binding: ActivityMainBinding
            private lateinit var mainActivityAdapter: MainActivityAdapter
            private var apiClient = ApiClient()
            private var currentPage = 1
            private var totalAvailablePages = 1
            private lateinit var tvShowList: ArrayList<TvShow>

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
                initViews()
            }


            private fun initViews() {
                binding.recyclerview.setHasFixedSize(true)
                binding.recyclerview.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                tvShowList = ArrayList()
                mainActivityAdapter = MainActivityAdapter()
                binding.recyclerview.adapter = mainActivityAdapter
                binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (!binding.recyclerview.canScrollVertically(1)) {
                            if (currentPage <= totalAvailablePages) {
                                currentPage += 1
                                loadPageList()
                            }
                        }
                    }
                })
                loadPageList()
            }

            private fun loadPageList() {
                toogleLoading()
                apiClient.callTvShowListRequest(currentPage, object : Callback<ShowModel> {
                    override fun onResponse(call: Call<ShowModel>, response: Response<ShowModel>) {
                        if (response.isSuccessful) {
                            val showModel = response.body()
                            if (showModel != null) {
                                val oldCount = tvShowList.size
                                totalAvailablePages = showModel.pages
                                tvShowList.addAll(showModel.tvShows)
                                mainActivityAdapter.updateList(tvShowList, oldCount, tvShowList.size)
                                Log.e(
                                    TAG,
                                    "oldCount $oldCount totalAvailablePages $totalAvailablePages tvShowList ${tvShowList.size}"
                                )
                            }
                        }
                        toogleLoading()
                    }

                    override fun onFailure(call: Call<ShowModel>, t: Throwable) {
                        t.printStackTrace()
                        Log.e(TAG, "exception", t)
                        toogleLoading()
                    }
                })
            }

            private fun toogleLoading() {
                if (currentPage == 1) {
                    if (binding.defaultProgress.isShown) {
                        binding.defaultProgress.visibility = View.GONE
                    } else {
                        binding.defaultProgress.visibility = View.VISIBLE
                    }
                } else {
                    if (binding.loadMoreProgress.isShown) {
                        binding.loadMoreProgress.visibility = View.GONE
                    } else {
                        binding.loadMoreProgress.visibility = View.VISIBLE
                    }
                }
            }
        }
        
<H2><br>Hope you learn</br>
<br>Thanks for reading!</br>
<br>Special Thanks to ALL ❤️ </br></H2>
