package android.bignerdranch.criminalintent

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_crime_list.view.*

class CrimeListFragment: Fragment() {
    lateinit var rootView: View
    companion object{
        val EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_crime_list, container,false)
        val itemClick: (Crime) -> Unit = { crime ->
            val intent = Intent(activity, CrimePagerActivity::class.java)
            intent.apply {
                putExtra(EXTRA_CRIME_ID,crime.id)
            }
            startActivity(intent)
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
    }
}