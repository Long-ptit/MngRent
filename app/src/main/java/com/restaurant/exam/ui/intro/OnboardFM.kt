package com.restaurant.exam.ui.intro
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.layout_about.view.*
import restaurant.exam.R

class OnboardFM : Fragment() {

    private lateinit var title: String
    private lateinit var description: String
    private lateinit var more: String
    private var imageResource = 0

    private lateinit var tvTitle: AppCompatTextView
    private lateinit var tvDescription: AppCompatTextView
    private lateinit var tvMore: AppCompatTextView
    private lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            title =
                requireArguments().getString(ARG_PARAM1)!!
            description =
                requireArguments().getString(ARG_PARAM2)!!
            more = ""
            imageResource =
                requireArguments().getInt(ARG_PARAM4)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootLayout: View =
            inflater.inflate(R.layout.layout_about, container, false)
        tvTitle = rootLayout.text_onboarding_title
        tvDescription = rootLayout.text_onboarding_description
        tvMore = rootLayout.text_onboarding_more
        image = rootLayout.image_onboarding
        tvTitle.text = title
        tvMore.text = more
        tvDescription.text = description
        image.setImageResource(imageResource)
        return rootLayout
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        private const val ARG_PARAM3 = "param3"
        private const val ARG_PARAM4 = "param4"
        fun newInstance(
            title: String,
            description: String,
            more: String,
            imageResource: Int
        ): OnboardFM {
            val fragment =
                OnboardFM()
            val args = Bundle()
            args.putString(
                ARG_PARAM1,
                title
            )
            args.putString(
                ARG_PARAM2,
                description
            )
            args.putString(
                ARG_PARAM3,
                more
            )
            args.putInt(
                ARG_PARAM4,
                imageResource
            )
            fragment.arguments = args
            return fragment
        }
    }
}