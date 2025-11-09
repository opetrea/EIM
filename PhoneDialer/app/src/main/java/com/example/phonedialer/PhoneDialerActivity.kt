package com.example.phonedialer

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.phonedialer.ui.theme.PhoneDialerTheme
import android.Manifest
import android.widget.Toast


class PhoneDialerActivity : ComponentActivity() {
    private lateinit var phoneNumberEditText: EditText

    companion object {
        const val CONTACTS_MANAGER_REQUEST_CODE = 201
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_dialer)

        // Leg EditText-ul din XML cu variabila din cod
        phoneNumberEditText = findViewById(R.id.editText)

        // Lista cu butoanele numerice si simboluri
        val buttonIds = listOf(
            R.id.button0, R.id.button1, R.id.button2,
            R.id.button3, R.id.button4, R.id.button5,
            R.id.button6, R.id.button7, R.id.button8,
            R.id.button9, R.id.buttonn1, R.id.buttonn2
        )

        // Creez o instanta a clasei interne care trateaza evenimentele de click
        val listener = ButtonClickListener()

        // Acelasi listener pentru toate butoanele din interfata
        for (id in buttonIds) {
            findViewById<Button>(id).setOnClickListener(listener)
        }

        // Butonul de stergere
        findViewById<View>(R.id.imageButton).setOnClickListener(listener)

        // Butonul de apel
        findViewById<View>(R.id.phone).setOnClickListener(listener)

        // Butonul de anulare
        findViewById<View>(R.id.cirlce).setOnClickListener(listener)

        findViewById<View>(R.id.agenda).setOnClickListener(listener)
    }

    // Clasa internacare trateaza click-ul pe butoanele numerice
    inner class ButtonClickListener: View.OnClickListener{
        override fun onClick(view: View?) {
            when(view?.id) {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                    R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                    R.id.button8, R.id.button9, R.id.buttonn1, R.id.buttonn2
                        -> {
                            val button = view as Button
                            val currentText = phoneNumberEditText.text.toString()
                            phoneNumberEditText.setText(currentText + button.text)
                }

                R.id.imageButton -> {
                    val text = phoneNumberEditText.text.toString()
                    if (text.isNotEmpty()) {
                        phoneNumberEditText.setText(text.substring(0, text.length - 1))

                    }
                }

                R.id.phone -> {
                    makePhoneCall()
                }

                R.id.cirlce -> {
                    finish()
                }

                R.id.agenda -> {
                    val phoneNumber = phoneNumberEditText.text.toString()
                    if (phoneNumber.isNotEmpty()) {
                        // Intentia care lanseaza aplicatia ContactsManager
                        val intent = Intent("ro.pub.cs.systems.eim.lab04.contactsmanager.intent.action.ContactsManagerActivity")
                        intent.putExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY", phoneNumber)
                        startActivityForResult(intent, CONTACTS_MANAGER_REQUEST_CODE)
                    } else {
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.phone_error),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        private fun makePhoneCall() {
            val number = phoneNumberEditText.text.toString()

            if (number.isNotEmpty()) {
                // Verifică permisiunea CALL_PHONE
                if (ContextCompat.checkSelfPermission(
                        this@PhoneDialerActivity,
                        Manifest.permission.CALL_PHONE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // Cere permisiunea la runtime
                    ActivityCompat.requestPermissions(
                        this@PhoneDialerActivity,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        1 // cod de cerere
                    )
                } else {
                    // Creează intenția de apel efectiv
                    val intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse("tel:$number")
                    startActivity(intent)
                }
            }
        }
    }
}