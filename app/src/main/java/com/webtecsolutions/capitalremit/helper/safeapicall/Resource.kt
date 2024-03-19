package com.webtecsolutions.capitalremit.helper.safeapicall

sealed class Resource<out T> {
    class Success<T>(val value: T) : Resource<T>()
    class Failure(val errorMsg: String, val isUnknownError: Boolean) : Resource<Nothing>()
}
