package com.dating.relations

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import com.dating.relations._core.BaseActivity
import com.dating.relations.ui.WebViewActivity
import com.google.firebase.database.DataSnapshot
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig


class ChooseAgeActivity : BaseActivity() {

    lateinit var radioGroup: RadioGroup
    lateinit var radioButton: RadioButton
    lateinit var toWebViewButton: ImageView
    lateinit var intent1: Intent
    private lateinit var dataSnapshot: DataSnapshot
    val APP_REFERENCES = "mysettings"
    val APP_REFERENCES_AGE = "empty"
    lateinit var mSettings: SharedPreferences
    lateinit var mEditor: SharedPreferences.Editor
    var sp: String? = null

    override fun setUI() {

        getValuesFromDatabase({
            dataSnapshot = it

            val taskUrl25 = dataSnapshot.child(TASK_URL_25).value as String
            val taskUrl2529 = dataSnapshot.child(TASK_URL_25_29).value as String
            val taskUrl30 = dataSnapshot.child(TASK_URL_30).value as String

            if (mSettings.contains(APP_REFERENCES_AGE)) {
                sp = mSettings.getString(APP_REFERENCES_AGE, "empty")
            }

            when (sp) {
                "меньше 25" -> {
                        intent1.putExtra(EXTRA_TASK_URL, taskUrl25)
                        startActivity(intent1)
                }
                "25-29" -> {
                    intent1.putExtra(EXTRA_TASK_URL, taskUrl2529)
                    startActivity(intent1)
                }
                "больше 30" -> {
                    intent1.putExtra(EXTRA_TASK_URL, taskUrl30)
                    startActivity(intent1)
                }
            }

            toWebViewButton.setOnClickListener {
                radioButton = findViewById(radioGroup.checkedRadioButtonId)

                when (radioButton.text.toString()) {

                    "меньше 25" -> {
                        mEditor.putString(APP_REFERENCES_AGE, "меньше 25")
                        mEditor.apply()
                        intent1.putExtra(EXTRA_TASK_URL, taskUrl25)
                        startActivity(intent1)
                    }
                    "25-29" -> {
                        mEditor.putString(APP_REFERENCES_AGE, "25-29")
                        mEditor.apply()
                        intent1.putExtra(EXTRA_TASK_URL, taskUrl2529)
                        startActivity(intent1)
                    }
                    "больше 30" -> {
                        mEditor.putString(APP_REFERENCES_AGE, "больше 30")
                        mEditor.apply()
                        intent1.putExtra(EXTRA_TASK_URL, taskUrl30)
                        startActivity(intent1)
                    }

                }
            }
        })

    }

    @SuppressLint("CommitPrefEdits")
    override fun initUI() {
        radioGroup = findViewById(R.id.ages_radio_group)
        toWebViewButton = findViewById(R.id.to_web_view_button)
        intent1 = Intent(this, WebViewActivity::class.java)
        mSettings = getSharedPreferences(APP_REFERENCES, Context.MODE_PRIVATE)
        val config = YandexMetricaConfig.newConfigBuilder("a193ae8e-7751-4d1f-9a73-13c1daf88c9f").build()
        YandexMetrica.activate(this, config)
        YandexMetrica.enableActivityAutoTracking(this.application)
        mEditor = mSettings.edit()
    }

    override fun getContentView(): Int = R.layout.activity_choose_age

}
