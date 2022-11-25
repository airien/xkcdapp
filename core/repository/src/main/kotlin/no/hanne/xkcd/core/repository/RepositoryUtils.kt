package no.hanne.xkcd.core.repository

import android.content.res.Resources
import no.hanne.xkcd.core.exception.DataSourceException
import no.hanne.xkcd.core.exception.RepositoryException

object RepositoryUtils {
    fun handleDataSourceException(exception: DataSourceException, resources: Resources): RepositoryException {
        val errorMessage = when (exception) {
            is DataSourceException.ConnectivityError -> {
                resources.getString(no.hanne.xkcd.core.text.R.string.error_no_connection)
            }
            else -> { // if server error, client error or other unknown error, just send generic error msg
                resources.getString(no.hanne.xkcd.core.text.R.string.general_error_message)
            }
        }
        return RepositoryException(errorMessage, exception)
    }
}
