package android.bignerdranch.criminalintent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class SingleFragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        var fm = supportFragmentManager
        var fragment: Fragment? = fm.findFragmentById(R.id.fragment_container)

        // let execute code if a given value is not null
        fragment?.let {
        } ?: run {
            fragment = createFragment()
            fm.beginTransaction().add(R.id.fragment_container, fragment!!).commit()
        }
    }
    protected abstract fun createFragment(): Fragment
}