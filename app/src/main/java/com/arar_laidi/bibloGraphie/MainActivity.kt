package com.arar_laidi.bibloGraphie

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

const val PERSON_KEY = "person"
class MainActivity : AppCompatActivity() {

    lateinit var person : Person
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadLocale()
        person = if (savedInstanceState == null){
            getRandomPerson()
        }else {
            savedInstanceState[PERSON_KEY]  as Person
        }

        name_txt_view.text = getString(person.name)
        content_txt_view.text = getString(person.description)
        Picasso.get().load(person.image).into(img_view)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(PERSON_KEY , person)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu , menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.lang -> {
                val prefs = getSharedPreferences("Settings", MODE_PRIVATE)
                val selectedItem: Int = when (prefs.getString("language", "")) {
                    "ar" -> 1
                    else -> 0
                }
                val listItems = arrayOf("Français", "العربية")
                val languages = arrayOf("", "ar")
                val mBuilder = AlertDialog.Builder(this@MainActivity)
                mBuilder.setTitle(getString(R.string.choose_language))
                mBuilder.setSingleChoiceItems(listItems, selectedItem) { dialogInterface, i ->
                    setLocale(languages[i])
                    recreate()
                    dialogInterface.dismiss()
                }
                // Set the neutral/cancel button click listener
                mBuilder.setNeutralButton(getString(R.string.cancel)) { dialog, which ->
                    // Do something when click the neutral button
                    dialog.cancel()
                }

                val mDialog = mBuilder.create()
                mDialog.show()
            }
        }
        return false
    }
    private fun setLocale(language: String) {
        val config = Configuration(baseContext.resources.configuration)
        config.setLocale(Locale(language))
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        val editor = getSharedPreferences("Settings", MODE_PRIVATE).edit()
        editor.putString("language", language)
        editor.apply()
    }

    private fun loadLocale() {
        val prefs = getSharedPreferences("Settings", MODE_PRIVATE)
        val language = prefs.getString("language", "")
        setLocale(language!!)
    }

    private fun getArrayPerson()  = listOf(
        Person(R.string.hitler , R.string.hitler_description,"https://www.francetvinfo.fr/pictures/yhv_UhWJRbWhqG4JxTTBkWNnsFE/1200x900/2019/04/12/adolf_hitler_-_1933_-_sipa.jpg") ,
        Person(R.string.salahAugrout , R.string.salah_description,"https://static.ennaharonline.com/wp-content/uploads/fly-images/935528/-%D8%A7%D9%84%D8%B9%D8%A7%D8%B4%D8%B1-1500x9999-c.jpg") ,
        Person(R.string.benzema , R.string.benzema_description,"https://upload.wikimedia.org/wikipedia/commons/thumb/d/d8/CSKA-RM18_%2811%29.jpg/420px-CSKA-RM18_%2811%29.jpg") ,
        Person(R.string.boutef , R.string.boutef_description,"https://upload.wikimedia.org/wikipedia/commons/thumb/5/5b/Abdelaziz_Bouteflika_casts_his_ballot_in_May_10th%27s_2012_legislative_election_%28cropped%29.jpg/220px-Abdelaziz_Bouteflika_casts_his_ballot_in_May_10th%27s_2012_legislative_election_%28cropped%29.jpg") ,)

    private fun getRandomPerson() : Person {
        val arr = getArrayPerson()
        return arr[rand(0, arr.size)]
    }

    private fun rand(start: Int = 0, end: Int): Int {
        require(start <= end) { "Illegal Argument" }
        return ((Math.random() *(end - start + 1)).toInt() + start) % end
    }
}
