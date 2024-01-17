package com.example.savecoll

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import android.telephony.TelephonyManager
import android.widget.Toast

class CallReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action

        if (action != null && action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE)

            if (phoneState != null && phoneState == TelephonyManager.EXTRA_STATE_RINGING) {
                val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                val contactName = getContactName(context, incomingNumber)
               MainActivity.number= incomingNumber.toString()
                if (contactName != null) {
                    if (incomingNumber != null) {
                        saveContact(context, contactName, incomingNumber)
                    }

                    Toast.makeText(context, "Contact saved: $contactName", Toast.LENGTH_SHORT).show()
//                    val i = Intent(context, MainActivity::class.java)
//                    i.putExtra("incomingNumber", incomingNumber)
//                    context.startActivity(i)

                } else {
                    Toast.makeText(context, "Contact name not found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("Range")
    private fun getContactName(context: Context, phoneNumber: String?): String? {
        // Здесь вы должны реализовать логику поиска имени контакта по номеру телефона
        // В примере используется простой вариант через контент-провайдер, что может не работать во всех случаях
        // На практике, вам следует использовать соответствующие методы для получения имени контакта

        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
        val selection = "${ContactsContract.CommonDataKinds.Phone.NUMBER} = ?"
        val selectionArgs = arrayOf(phoneNumber)

        context.contentResolver.query(uri, projection, selection, selectionArgs, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            }
        }

        return null
    }

    private fun saveContact(context: Context, name: String, phoneNumber: String) {
        // Здесь вы должны реализовать логику сохранения контакта
        // В примере используется простой вариант, который может не подходить для реального приложения
        // На практике, вам следует использовать соответствующие методы для добавления контакта

        val values = ContentValues().apply {
            put(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, name)
            put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber)
        }

        context.contentResolver.insert(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, values)
    }
}



