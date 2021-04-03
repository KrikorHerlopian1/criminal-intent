package android.bignerdranch.criminalintent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CrimeAdapter(var crimes: List<Crime>?,private val itemClick: (Crime) -> Unit) : RecyclerView.Adapter<CrimeHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_crime, parent, false)
        return CrimeHolder(view)
    }

    override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
        crimes?.get(position)?.let { crime ->
            holder.bind(crime, itemClick)
        }
    }

    override fun getItemCount(): Int {
        return crimes?.size ?: 0
    }
}