package android.bignerdranch.criminalintent

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import java.util.*

class CrimeActivity : SingleFragmentActivity() {
    companion object{
        val EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id"
    }
    override fun createFragment(): Fragment {
        var crimeId: UUID = intent.getSerializableExtra(EXTRA_CRIME_ID) as UUID
        var arguments = bundleOf(
            EXTRA_CRIME_ID to crimeId)
        val fragment = CrimeFragment()
        fragment.arguments = arguments
        return fragment
    }
}