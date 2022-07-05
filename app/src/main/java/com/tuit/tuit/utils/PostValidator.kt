package com.tuit.tuit.utils

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.tuit.tuit.R

object PostValidator {

    fun validatePost(
        title: String,
        description: String,
        subjectName: String,
        fileUri: Uri?,
        context: Context
    ): Boolean {
        if (title.length < 5) {
            Toast.makeText(context, "Sarlavha kiriting", Toast.LENGTH_SHORT).show()
            return false
        }
        if (description.length < 20) {
            Toast.makeText(context, "To'liqroq tavsif kiriting", Toast.LENGTH_SHORT).show()
            return false
        }
        if (subjectName.isEmpty()) {
            Toast.makeText(context, "Fanni tanlang", Toast.LENGTH_SHORT).show()
            return false
        }
        if (fileUri == null) {
            Toast.makeText(context, "Fayl qo'shing", Toast.LENGTH_SHORT).show()
            return false

        }
        return true
    }

    fun isRegistrationValid(
        context: Context,
        textName: String,
        textEmail: String,
        textPassword: String,
        textReEnteredPassword: String,
    ): Boolean {
        if (textName.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.enterName), Toast.LENGTH_SHORT)
                .show()
            return false
        }

        if (!textEmail.isValidEmail()) {
            Toast.makeText(context, context.getString(R.string.enterValidEmail), Toast.LENGTH_SHORT)
                .show()
            return false
        }
        if (textReEnteredPassword.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.enterName), Toast.LENGTH_SHORT)
                .show()
            return false
        }
        if (textPassword.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.enterName), Toast.LENGTH_SHORT)
                .show()
            return false
        }
        if (textReEnteredPassword != textPassword) {
            Toast.makeText(
                context,
                context.getString(R.string.passwordsMustBeEqual),
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        if (textPassword.length < 6) {
            Toast.makeText(
                context,
                context.getString(R.string.passwordMustBeMoreThan6Char),
                Toast.LENGTH_SHORT
            ).show()
            return false
        }



        return true
    }

}