package com.movicom.informativeapplicationcovid19.utils

import android.content.Context
import android.widget.Toast

class UIUtils {

    companion object {
        /**
         * Mostrar un mensaje en forma de Toast.
         *
         * @param message mensaje a mostrar.
         */
        fun showMessage(context:Context, message: String){
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}