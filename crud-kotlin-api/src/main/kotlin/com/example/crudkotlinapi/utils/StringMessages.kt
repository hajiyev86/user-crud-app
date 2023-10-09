package com.example.crudkotlinapi.utils

import java.util.ResourceBundle

enum class StringMessages {

    ERR_USER_NOT_FOUND,
    ERR_ID_MUST_NOT_PRESENT_ON_CREATION,
    ERR_AGE_SERVICE_NOT_WORKING;

    private var msg: String? = null

    fun getMessage(): String {
        return msg ?: name
    }

    companion object {
        private var rb: ResourceBundle? = null

        fun getResourceBundle(): ResourceBundle? {
            return rb
        }

        fun setResourceBundle(resourceBundle: ResourceBundle?) {
            rb = resourceBundle
            if (resourceBundle != null) {
                for (sm in values()) {
                    sm.msg = resourceBundle.getString(sm.name)
                }
            }
        }
    }
}
