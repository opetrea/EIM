package com.example.contactsmanager

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ContactsManager : AppCompatActivity() {

    private lateinit var showFields: Button
    private lateinit var name: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var email: EditText
    private lateinit var address: EditText
    private lateinit var job: EditText
    private lateinit var company: EditText
    private lateinit var website: EditText
    private lateinit var im: EditText
    private lateinit var save: Button
    private lateinit var cancel: Button
    private lateinit var container1: LinearLayout
    private lateinit var container2: LinearLayout

    private var funcListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.showFields -> {
                if (container2.visibility == View.GONE) {
                    container2.visibility = View.VISIBLE
                    showFields.text = "Hide Additional Fields"
                } else {
                    container2.visibility = View.GONE
                    showFields.text = "Show Additional Fields"
                }
            }

            R.id.cancel -> {
                finish()
            }

            R.id.save -> {
                val intent = Intent(ContactsContract.Intents.Insert.ACTION).apply {
                    setType(ContactsContract.RawContacts.CONTENT_TYPE)
                    putExtra(ContactsContract.Intents.Insert.NAME, name.text.toString())
                        putExtra(ContactsContract.Intents.Insert.PHONE, phoneNumber.text.toString())
                        putExtra(ContactsContract.Intents.Insert.EMAIL, email.text.toString())
                        putExtra(ContactsContract.Intents.Insert.POSTAL, address.text.toString())
                        putExtra(ContactsContract.Intents.Insert.JOB_TITLE, job.text.toString())
                        putExtra(ContactsContract.Intents.Insert.COMPANY, company.text.toString())
                }

                val contactData = ArrayList<ContentValues>().apply {
                    if (website.text.toString().isNotEmpty()) {
                        add(ContentValues().apply {
                            put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE)
                            put(ContactsContract.CommonDataKinds.Website.URL, website.text.toString())
                        })
                    }

                    if (im.text.toString().isNotEmpty()) {
                        add(ContentValues().apply {
                            put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE)
                            put(ContactsContract.CommonDataKinds.Im.DATA, im.text.toString())
                        })
                    }
                }

                intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contacts_manager)

        showFields = findViewById(R.id.showFields)
        name = findViewById(R.id.name)
        phoneNumber = findViewById(R.id.phoneNumber)
        email = findViewById(R.id.email)
        address = findViewById(R.id.address)
        job = findViewById(R.id.job)
        company = findViewById(R.id.company)
        website = findViewById(R.id.website)
        im = findViewById(R.id.im)
        save = findViewById(R.id.save)
        cancel = findViewById(R.id.cancel)
        container1 = findViewById(R.id.container1)
        container2 = findViewById(R.id.container2)

        showFields.setOnClickListener(funcListener)
        save.setOnClickListener(funcListener)
        cancel.setOnClickListener(funcListener)




    }


}