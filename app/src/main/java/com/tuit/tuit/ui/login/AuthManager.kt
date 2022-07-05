package com.tuit.tuit.ui.login

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.tuit.tuit.ui.login.handler.AuthHandler

class AuthManager {
    companion object {
        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

        fun isSignIn(): Boolean {
            return currentUser() != null
        }

        fun currentUser(): FirebaseUser? {
            return firebaseAuth.currentUser
        }

        fun signIn(email: String, password: String, handler: AuthHandler) {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val uid = currentUser()!!.uid
                    handler.onSuccess(uid)
                } else {
                    handler.onError(task.exception)
                }
            }
        }
    }
}