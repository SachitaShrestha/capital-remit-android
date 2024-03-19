package com.webtecsolutions.capitalremit.helper.safeapicall

interface ApiCallListener {
    fun onSuccess(response: String?)
    fun onError(errorMessage: String?)

}