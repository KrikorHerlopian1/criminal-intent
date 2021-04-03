package android.bignerdranch.criminalintent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_crime_pager.*
import java.util.*

class CrimePagerActivity: AppCompatActivity() {
    companion object{
        val EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id"
    }
    var mCrimes: MutableList<Crime>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crime_pager)
        var crimeId: UUID = intent.getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID) as UUID

        mCrimes = CrimeLab.get(this)?.crimes
        crime_view_pager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getItem(position: Int): Fragment {
                val crime: Crime? = mCrimes?.get(position)
                return CrimeFragment.newInstance(crime?.id!!)
            }
            override fun getCount(): Int {
                return mCrimes?.size!!
            }
        }


        mCrimes?.indexOfFirst { it.id == crimeId }!!


        mCrimes?.let {
            var value = 0
            for(crime in mCrimes!!){
                if(crime.id == crimeId)
                {
                    crime_view_pager.currentItem = value
                }
                value += 1
            }
        }
    }
}