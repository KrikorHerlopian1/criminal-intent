package android.bignerdranch.criminalintent

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_crime_list.view.*

class CrimeListFragment: Fragment() {
    lateinit var rootView: View
    var mSubtitleVisible: Boolean = false
    companion object{
        const val SAVED_SUBTITLE_VISIBLE = "subtitle"
        val EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_crime_list, container,false)
        if(savedInstanceState != null)
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE)
        val itemClick: (Crime) -> Unit = { crime ->
            val intent = Intent(activity, CrimePagerActivity::class.java)
            intent.apply {
                putExtra(EXTRA_CRIME_ID,crime.id)
                startActivity(this)
            }

        }
        rootView.recycler_view.apply{
            CrimeLab.get(activity)
            layoutManager = LinearLayoutManager(activity)
            adapter = CrimeAdapter(CrimeLab.crimes, itemClick)
        }

        return rootView
    }

    override fun onResume() {
        super.onResume()
        rootView.recycler_view.apply{
            adapter?.notifyDataSetChanged()
        }
        updateSubtitle()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_crime_list,menu)
        var subtitleItem = menu.findItem(R.id.show_subtitle)
        if(mSubtitleVisible)
            subtitleItem.setTitle(R.string.hide_subtitle)
        else
            subtitleItem.setTitle(R.string.show_subtitle)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.new_crime -> {
                var crime = Crime()
                CrimeLab.get(context)?.addCrime(crime)
                val intent = Intent(activity, CrimePagerActivity::class.java)
                intent.apply {
                    putExtra(EXTRA_CRIME_ID,crime.id)
                    startActivity(this)
                }
                return true
            }
            R.id.show_subtitle -> {
                mSubtitleVisible = !mSubtitleVisible
                activity?.invalidateOptionsMenu()
                updateSubtitle()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun updateSubtitle(){
        var crimeLab = CrimeLab.get(activity)
        var crimeCount = crimeLab?.crimes?.size
        var subtitle: String? = getString(R.string.subtitle_format, crimeCount)
        if(!mSubtitleVisible)
            subtitle = null
        var act = activity as AppCompatActivity
        act.supportActionBar?.subtitle = subtitle
    }
}