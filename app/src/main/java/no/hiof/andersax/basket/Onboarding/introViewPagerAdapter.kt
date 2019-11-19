package no.hiof.andersax.basket.Onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import no.hiof.andersax.basket.R

class introViewPagerAdapter(val context : Context, val onbScreenItems : MutableList<ScreenItem> )  : PagerAdapter(){


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutscreen : View = inflater.inflate(R.layout.onboarding_layout_screen, null)

        val image : ImageView = layoutscreen.findViewById(R.id.onboardingimage)
        val title : TextView = layoutscreen.findViewById(R.id.introTitle)
        val description : TextView = layoutscreen.findViewById(R.id.introDescription)

        title.text = onbScreenItems.get(position).Title
        description.text = onbScreenItems.get(position).Description
        image.setImageResource(onbScreenItems.get(position).Image)

        container.addView(layoutscreen)

        return layoutscreen
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj : Any) {
        container.removeView(obj as View?)
    }

    override fun getCount(): Int {
        return onbScreenItems.size
    }
}