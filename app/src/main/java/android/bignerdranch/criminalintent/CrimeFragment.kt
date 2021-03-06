package android.bignerdranch.criminalintent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_crime.view.*
import java.util.*

class CrimeFragment: Fragment() {
    companion object{
        const val ARG_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id"
        const val DIALOG_DATE = "DialogDate"
        const val REQUEST_DATE = 0
        fun newInstance(crimeID: UUID): CrimeFragment {
            val args = Bundle()
            args.putSerializable(ARG_CRIME_ID, crimeID)

            val fragment = CrimeFragment()
            fragment.arguments = args
            return fragment
        }
    }
    var mCrime: Crime? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var crimeId: UUID? = arguments?.getSerializable(ARG_CRIME_ID) as UUID
        crimeId?.let {
            mCrime = CrimeLab.get(activity)?.getCrime(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view =  inflater.inflate(R.layout.fragment_crime, container,false)
        view.crime_title.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(sequence: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {
                mCrime?.title = sequence.toString()
            }
            override fun afterTextChanged(sequence: Editable?) {
            }
        })

        /*view.crime_solved.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                mCrime.isSolved = isChecked
            }
        })*/
        view.crime_solved.setOnCheckedChangeListener { buttonView, isChecked ->
            mCrime?.isSolved = isChecked
        }

        view.crime_date.text = mCrime?.date.toString()
        view.crime_title.setText(mCrime?.title)
        //view.crime_date.setEnabled(mCrime?.isSolved ?: false)
        view.crime_date.setOnClickListener {
            fragmentManager?.let {
                var dialog = mCrime?.date?.let {
                        it1 -> DatePickerFragment.newInstance(it1)
                }
                dialog?.setTargetFragment(this@CrimeFragment, REQUEST_DATE)
                dialog?.show(fragmentManager!!, DIALOG_DATE)
            }
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(Activity.RESULT_OK == resultCode){
            if(requestCode == REQUEST_DATE){
                var date = data?.getSerializableExtra(DatePickerFragment.EXTRA_DATE) as Date?
                date?.let {
                    mCrime?.date = it
                    updateDate(it)
                }
            }
        }
    }
    private fun updateDate(date: Date){
        view?.crime_date?.text = date.toString()
    }
}