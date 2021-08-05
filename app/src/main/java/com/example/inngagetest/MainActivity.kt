package com.example.inngagetest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.com.inngage.sdk.InngageIntentService
import br.com.inngage.sdk.InngageUtils
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.installations.FirebaseInstallations
import java.lang.NullPointerException


class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "INNGAGE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        handleSubscription()
        handleNotification()
    }

    private fun handleSubscription() {

        // caso o app possua campos customizáveis, descomente estas linhas, cheque-os na plataforma, substitua-os abaixo e adicione os valores de acordo com a sua aplicação
//        JSONObject jsonCustomField = new JSONObject();
//
//        try {
//
//            jsonCustomField.put("nome", user.name);
//            jsonCustomField.put("email", user.email);
//            jsonCustomField.put("telefone", "");
//            jsonCustomField.put("dataRegistro", "");
//            jsonCustomField.put("dataNascimento", "");
//
//
//      } catch (JSONException e) {
//
//            e.printStackTrace();
//      }
//        // caso possua campos customizáveis, adicione o "jsonCustomField" como na chamada abaixo
//        InngageIntentService.startInit(
//                this,
//                InngageConstants.inngageAppToken,
//                "Identifier", //Seu identificador
//                InngageConstants.inngageEnvironment,
//                InngageConstants.googleMessageProvider,
//    jsonCustomField);

        // caso não possua campos customizáveis até então:
        InngageIntentService.startInit(
            this,
            InngageConstants.inngageAppToken,
            "jean.ferreira.santini@gmail.com",  //Seu identificador
            InngageConstants.inngageEnvironment,
            InngageConstants.googleMessageProvider
        )
    }

    private fun handleNotification() {
        var notifyID = ""
        var title = ""
        var body = ""
        var url = ""
        val bundle = intent.extras
        if (intent.hasExtra("EXTRA_NOTIFICATION_ID")) {
            notifyID = bundle!!.getString("EXTRA_NOTIFICATION_ID")!!
        }
        if (intent.hasExtra("EXTRA_TITLE")) {
            title = bundle!!.getString("EXTRA_TITLE")!!
        }
        if (intent.hasExtra("EXTRA_BODY")) {
            body = bundle!!.getString("EXTRA_BODY")!!
        }
        if (intent.hasExtra("EXTRA_URL")) {
            url = bundle!!.getString("EXTRA_URL")!!
        }

        if (url.isEmpty()) {
            if ("" != notifyID || "" != title || "" != body) {
                Log.d(TAG, "no link: $url")
                InngageUtils.showDialog(
                    title,
                    body,
                    notifyID,
                    InngageConstants.inngageAppToken,
                    InngageConstants.inngageEnvironment,
                    this
                )
            }
        } else if ("" != notifyID || "" != title || "" != body) {
            Log.d(TAG, "Link: $url")
            InngageUtils.showDialogwithLink(
                title,
                body,
                notifyID,
                InngageConstants.inngageAppToken,
                InngageConstants.inngageEnvironment,
                url,
                this
            )
        }
    }
}