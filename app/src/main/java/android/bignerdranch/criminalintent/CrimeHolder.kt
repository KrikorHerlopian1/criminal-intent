package android.bignerdranch.criminalintent

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_crime.view.*

class CrimeHolder(itemView: View): RecyclerView.ViewHolder(itemView), ViewHolder{

    override fun bind(crime: Crime, itemClick: (Crime) -> Unit) {
        itemView.crime_title.text = crime.title
        itemView.crime_date.text = android.text.format.DateFormat
            .format("EEEE, MMM d, yyyy", crime.date)
        itemView.crime_solved.visibility = if (crime.isSolved) View.VISIBLE else View.INVISIBLE
        itemView.setOnClickListener { itemClick(crime) }
    }
}

interface ViewHolder {
    fun bind(crime: Crime, itemClick: (Crime) -> Unit)
}