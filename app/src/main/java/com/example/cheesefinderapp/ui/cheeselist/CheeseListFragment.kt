package com.example.cheesefinderapp.ui.cheeselist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.cheesefinderapp.R
import com.example.cheesefinderapp.model.Cheese
import com.example.cheesefinderapp.model.pojo.UpdateCheeseRequest
import com.example.cheesefinderapp.ui.InfoCheeseActivity
import com.example.cheesefinderapp.ui.MainActivity
import com.google.android.material.transition.MaterialFadeThrough
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_CHEESE_COLLECTION = "param_cheese_collection"

/**
 * A simple [Fragment] subclass.
 * Use the [CheeseListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CheeseListFragment : Fragment() {

    private var cheeseCollection: ArrayList<Cheese>? = null
    private var filteredCheeseCollection: ArrayList<Cheese>? = null
    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CheeseListAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            cheeseCollection =
                arguments?.getSerializable(ARG_CHEESE_COLLECTION) as ArrayList<Cheese>
        }
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_cheese_list, container, false)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.fgt_cheese_list_view)
        adapter = CheeseListAdapter(cheeseCollection!!,
            { cheese ->
                val intent = Intent(requireContext(), InfoCheeseActivity::class.java)
                intent.putExtra("cheeseID", cheese.id)
                startActivity(intent)
            },
            { cheeseID ->
                makeToggleFavoriteRequest(cheeseID)
            })
        layoutManager = LinearLayoutManager(this.context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        //
        val refreshLayout =
            rootView.findViewById<SwipeRefreshLayout>(R.id.fgt_cheese_list_swipe_refresh)
        refreshLayout.setOnRefreshListener {
            makeGetAllCheesesRequest()
        }
        exitTransition = MaterialFadeThrough()
        return rootView
    }

    fun filterDataLocally(query: String) {
        if (query.isEmpty()) {
            adapter.restoreData()
        } else {
            filteredCheeseCollection = cheeseCollection?.filter {
                it.fromage.contains(query, ignoreCase = true) ||
                        it.departement.contains(query, ignoreCase = true)
            } as ArrayList<Cheese>

            adapter.updateData(filteredCheeseCollection!!)
        }
    }

    fun filterDataWithAPIResponse(query: List<Cheese>) {
        adapter.updateData(query)
    }

    companion object {
        @JvmStatic
        fun newInstance(cheeseCollection: ArrayList<Cheese>) =
            CheeseListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CHEESE_COLLECTION, cheeseCollection)
                }
            }
    }

    private fun makeToggleFavoriteRequest(cheeseId: String) {

        val cheeseService = (requireActivity() as MainActivity).cheeseService

        cheeseService.toggleFavorite(updateCheeseRequest = UpdateCheeseRequest(cheeseId))
            .enqueue(object : Callback<Cheese> {
                override fun onResponse(
                    call: Call<Cheese>,
                    response: Response<Cheese>
                ) {
                    response.body()?.let { updatedCheese ->
                        val updatedList = ArrayList(cheeseCollection)
                        val position = updatedList.indexOfFirst { it.id == updatedCheese.id }
                        if (position != -1) {
                            updatedList[position] = updatedCheese
                            adapter.updateData(updatedList)
                        }
                    }
                }

                override fun onFailure(call: Call<Cheese>, t: Throwable) {
                    t.printStackTrace()
                }
            })

    }

    private fun makeGetAllCheesesRequest() {
        val cheeseService = (requireActivity() as MainActivity).cheeseService
        val swipeRefreshLayout =
            rootView.findViewById<SwipeRefreshLayout>(R.id.fgt_cheese_list_swipe_refresh)

        cheeseService.getAllCheese()
            .enqueue(object : Callback<List<Cheese>> {
                override fun onResponse(
                    call: Call<List<Cheese>>,
                    response: Response<List<Cheese>>
                ) {

                    cheeseCollection?.clear()
                    response.body()?.forEach {
                        val cheese =
                            Cheese(
                                it.id,
                                it.departement,
                                it.fromage,
                                it.lait,
                                it.geo_shape,
                                it.geo_point_2d,
                                it.favorite
                            )
                        cheeseCollection?.add(cheese)
                    }
                    requireActivity().runOnUiThread {
                        adapter.updateData(ArrayList(cheeseCollection))
                        swipeRefreshLayout.isRefreshing = false
                    }

                }

                override fun onFailure(call: Call<List<Cheese>>, t: Throwable) {
                    t.printStackTrace()
                }
            })

    }
}