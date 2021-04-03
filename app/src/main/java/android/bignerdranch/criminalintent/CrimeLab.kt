package android.bignerdranch.criminalintent

import android.content.Context
import java.util.*

object CrimeLab {
    private var crimeLab: CrimeLab? = null
    var crimes = mutableListOf<Crime>()

    fun get(context: Context?): CrimeLab? =
        crimeLab ?: CrimeLab(context)

    private operator fun invoke(context: Context?): CrimeLab? {
        for(i in  0 until 100){
            var crime = Crime()
            val c = Crime()
            c.title = String.format("Crime #%d", i)
            c.isSolved = (i % 2 == 0)
            crimes.add(c)
        }
        crimeLab = this
        return crimeLab
    }

    fun genCrimes(): List<Crime> {
        return crimes
    }

    fun getCrime(uniqueIdentifier: UUID): Crime?{
        for(crime in crimes){
            if(crime.id.equals(uniqueIdentifier))
                return crime
        }
        return null
    }
}