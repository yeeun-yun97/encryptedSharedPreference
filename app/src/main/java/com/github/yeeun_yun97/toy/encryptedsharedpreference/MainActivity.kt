package com.github.yeeun_yun97.toy.encryptedsharedpreference

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.github.yeeun_yun97.toy.encryptedsharedpreference.ui.theme.EncryptedSharedPreferenceTheme

class MainActivity : ComponentActivity() {

    private val sharedPreferences: SharedPreferences by lazy {

        val masterKeyAlias = MasterKey
            .Builder(applicationContext, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()


        EncryptedSharedPreferences.create(
            applicationContext,
            "data.xml",
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EncryptedSharedPreferenceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting(sharedPreferences)
                }
            }
        }
    }
}

@Composable
fun Greeting(sharedPreferences: SharedPreferences) {
    val key = "text"

    var text by remember{ mutableStateOf(sharedPreferences.getString(key,"")!!)}

    Column{
        TextField(text,{ text = it})
        Button({sharedPreferences.edit().putString(key,text).apply()}){
            Text("저장")
        }
    }

}
