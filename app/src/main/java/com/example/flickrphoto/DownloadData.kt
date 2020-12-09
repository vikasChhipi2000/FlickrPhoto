package com.example.flickrphoto

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

enum class DownloadStatus{
    OK , ERROR , PERMISSION_ERROR , FAILED_TO_DOWNLOAD , IDLE , NOT_INITIALISE
}
class DownloadData(private val listener:OnDownloadComplete):AsyncTask<String,Void,String>() {
    private var downloadStatus = DownloadStatus.IDLE

    interface OnDownloadComplete{
        fun onDownloadComplete(data : String,status: DownloadStatus)
    }

    override fun doInBackground(vararg params: String?): String {
        Log.d("DownloadData","doInBackground called")
        if(params[0]==null){
            downloadStatus = DownloadStatus.NOT_INITIALISE
            return "No url provide"
        }
        try {
            downloadStatus = DownloadStatus.OK
            return URL(params[0]).readText()
        }catch (e:Exception){
            return when(e){
                 is MalformedURLException -> {
                     downloadStatus = DownloadStatus.NOT_INITIALISE
                     "Download data: url is not correct ${e.message}"
                 }
                is IOException -> {
                    downloadStatus = DownloadStatus.FAILED_TO_DOWNLOAD
                    "Download data : error in downloading file  ${e.message}"
                }
                is SecurityException -> {
                    downloadStatus = DownloadStatus.PERMISSION_ERROR
                    "Download data : intenet permission is not given ${e.message}"
                }
                else ->{
                    downloadStatus = DownloadStatus.ERROR
                    "Downlod data : unknown error ${e.message}"
                }
             }
        }
    }

    override fun onPostExecute(result: String) {
        Log.d("DownloadData","onPostExecute called")
        super.onPostExecute(result)
        listener.onDownloadComplete(result,downloadStatus)
        Log.d("DownloadData","onPostExecute finish")
    }
}