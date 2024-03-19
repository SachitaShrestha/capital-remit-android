package com.webtecsolutions.capitalremit.data.constants

import com.webtecsolutions.capitalremit.BuildConfig

object Config {
    const val BASE_URL = "https://sis.sabdamala.org.au/api/"

    const val PREF_NAME = "com.webtecsolutions.capitalremit.preferences"
    const val DATA_STORE_NAME = "CAPITAL REMIT DATASTORE"
    const val DATABASE_NAME = "${BuildConfig.APPLICATION_ID}.room.db"
    const val DATABASE_VERSION = 9


}