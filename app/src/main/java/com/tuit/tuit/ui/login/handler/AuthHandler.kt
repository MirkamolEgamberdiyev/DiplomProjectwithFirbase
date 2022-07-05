package com.tuit.tuit.ui.login.handler

import java.lang.Exception

interface AuthHandler {
    fun onSuccess(uid:String)
    fun onError(exception: Exception?)
}