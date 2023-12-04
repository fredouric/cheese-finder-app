package com.example.cheesefinderapp.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.cheesefinderapp.R
import com.google.android.material.transition.MaterialFadeThrough

/**
 * A simple [Fragment] subclass.
 * Use the [AboutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_about, container, false)

        setInfoApp(rootView)

        enterTransition = MaterialFadeThrough()
        return rootView
    }

    private fun setInfoApp(rootView: View) {
        val appNameTextView: TextView = rootView.findViewById(R.id.fa_app_name)
        val nameAuthorTextView: TextView = rootView.findViewById(R.id.fa_name_author)
        val urlTextView: TextView = rootView.findViewById(R.id.fa_url)
        val explanationTextView: TextView = rootView.findViewById(R.id.fa_explanation)
        val librariesTextView: TextView = rootView.findViewById(R.id.fa_librairies)
        val licenceTextView: TextView = rootView.findViewById(R.id.fa_license)

        appNameTextView.text = "Cheese Finder"
        nameAuthorTextView.text = " Auteurs : Eva Pinlong & Frédéric Thomas"
        explanationTextView.text = "Explanation"
        librariesTextView.text = "Librairies: Retrofit ..."
        licenceTextView.text = "Licence : MIT"

        hideURL(urlTextView)
    }

    private fun hideURL(textView: TextView) {
        val fullText = "Lien vers les données : Liste des Fromages Français"
        textView.text = fullText

        val clickableStart = fullText.indexOf("Liste des Fromages Français")
        val clickableEnd = clickableStart + "Liste des Fromages Français".length

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                // Handle the click event, in this case, open the specified URL
                val url =
                    "https://data.opendatasoft.com/explore/dataset/fromagescsv-fromagescsv%40public/table/?disjunctive.fromage&location=10,47.1547,4.72687&basemap=jawg.streets"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }

        val spannableString = SpannableString(textView.text)
        spannableString.setSpan(
            clickableSpan,
            clickableStart,
            clickableEnd,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment AboutFragment.
         */
        @JvmStatic
        fun newInstance() =
            AboutFragment().apply {
            }
    }
}