package com.example.cheesefinderapp.ui.cheeselist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cheesefinderapp.R
import com.example.cheesefinderapp.model.Cheese
import com.example.cheesefinderapp.model.pojo.UpdateCheeseRequest
import com.example.cheesefinderapp.ui.InfoCheeseActivity
import com.example.cheesefinderapp.ui.MainActivity
import com.google.android.material.transition.MaterialFadeThrough
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_CHEESE_COLLECTION = "param_cheese_collection"

/**
 * A simple [Fragment] subclass.
 * Use the [CheeseListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CheeseListFragment : Fragment() {

    private var cheeseCollection: ArrayList<Cheese>? = null
    private var filteredCheeseCollection: ArrayList<Cheese>? = null
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
        val rootView = inflater.inflate(R.layout.fragment_cheese_list, container, false)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.fgt_cheese_list_view)
        adapter = CheeseListAdapter(cheeseCollection!!,
            { cheese ->
                val intent = Intent(requireContext(), InfoCheeseActivity::class.java)
                intent.putExtra("cheeseID", cheese.id)
                startActivity(intent)
            },
            { cheeseID ->
                makeRetrofitCall(cheeseID)
            })
        layoutManager = LinearLayoutManager(this.context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

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

    private fun makeRetrofitCall(cheeseId: String) {

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
}