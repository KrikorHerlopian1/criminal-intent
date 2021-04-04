package android.bignerdranch.criminalintent

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_date.view.*
import java.util.*

class DatePickerFragment: DialogFragment() {
    companion object{
        const val ARG_DATE = "date"
        const val EXTRA_DATE = "com.bignerdranch.android.criminalintent.date"
        fun newInstance(date: Date): DatePickerFragment{
            var args = Bundle()
            args.putSerializable(ARG_DATE, date)
            var fragment = DatePickerFragment()
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var date = arguments?.getSerializable(ARG_DATE) as Date
        var calendar = Calendar.getInstance()
        calendar.time = date
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)

        var v: View = LayoutInflater.from(activity).inflate(R.layout.dialog_date,null);
        v.dialog_date_picker.init(year,month,day,null)

        return AlertDialog.Builder(requireActivity())
            .setView(v)
            .setTitle(R.string.date_picker_title)
            .setPositiveButton(android.R.string.ok, object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    var year = v.dialog_date_picker.year
                    var month = v.dialog_date_picker.month
                    var day = v.dialog_date_picker.dayOfMonth
                    var  date = GregorianCalendar(year, month, day).time
                    sendResult(Activity.RESULT_OK, date)
                }
            })
            .create();
    }

    private fun sendResult(resultCode: Int, date: Date){
        targetFragment?.let {
            var intent = Intent()
            intent.putExtra(EXTRA_DATE,date)
            it.onActivityResult(targetRequestCode,resultCode,intent)
        }
    }
}