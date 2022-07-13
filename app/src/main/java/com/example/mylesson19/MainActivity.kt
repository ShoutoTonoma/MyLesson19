package com.example.mylesson19

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.example.mylesson19.constance.Constance
import com.example.mylesson19.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityMainBinding
    private var login: String = "empty"
    private var password: String = "empty"
    private var name: String = "empty"
    private var name2: String = "empty"
    private var name3: String = "empty"
    private var avatarImageId: Int = 0

//    Альтернатива onActivityResult
    private var signInLauncher:ActivityResultLauncher<Intent>? = null
    private var signUpLauncher:ActivityResultLauncher<Intent>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
            if (result.resultCode == RESULT_OK){
                val l = result.data?.getStringExtra(Constance.LOGIN)
                val p = result.data?.getStringExtra(Constance.PASSWORD)
                if(login == l && password == p){
                    bindingClass.imAvatar.visibility = View.VISIBLE
                    bindingClass.imAvatar.setImageResource(avatarImageId)
                    val textInfo = "$name $name2 $name3"
                    bindingClass.tvInfo.text = textInfo
                    bindingClass.bHide.visibility = View.GONE
                    bindingClass.bExit.text = "Выйти"

                } else {
                    bindingClass.imAvatar.visibility = View.VISIBLE
                    bindingClass.imAvatar.setImageResource(R.drawable.error_auth)
                    bindingClass.tvInfo.text = "Такого аккаунта не существует"
                }
            }
        }

        signUpLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
            login = result.data?.getStringExtra(Constance.LOGIN)!!
            password = result.data?.getStringExtra(Constance.PASSWORD)!!
            name = result.data?.getStringExtra(Constance.NAME)!!
            name2 = result.data?.getStringExtra(Constance.NAME2)!!
            name3 = result.data?.getStringExtra(Constance.NAME3)!!
            avatarImageId = result.data!!.getIntExtra(Constance.AVATAR_ID, 0)
            bindingClass.imAvatar.visibility = View.VISIBLE
            bindingClass.imAvatar.setImageResource(avatarImageId)
            val textInfo = "$name $name2 $name3"
            bindingClass.tvInfo.text = textInfo
            bindingClass.bHide.visibility = View.GONE
            bindingClass.bExit.text = "Выйти"
        }
    }




//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if(requestCode == Constance.REQUEST_CODE_SIGN_IN){
//            val l = data?.getStringExtra(Constance.LOGIN)
//            val p = data?.getStringExtra(Constance.PASSWORD)
//            if(login == l && password == p){
//                bindingClass.imAvatar.visibility = View.VISIBLE
//                bindingClass.imAvatar.setImageResource(avatarImageId)
//                val textInfo = "$name $name2 $name3"
//                bindingClass.tvInfo.text = textInfo
//                bindingClass.bHide.visibility = View.GONE
//                bindingClass.bExit.text = "Выйти"
//
//            } else {
//                bindingClass.imAvatar.visibility = View.VISIBLE
//                bindingClass.imAvatar.setImageResource(R.drawable.error_auth)
//                bindingClass.tvInfo.text = "Такого аккаунта не существует"
//            }
//
//        } else if(requestCode == Constance.REQUEST_CODE_SIGN_UP){
//
//            login = data?.getStringExtra(Constance.LOGIN)!!
//            password = data.getStringExtra(Constance.PASSWORD)!!
//            name = data.getStringExtra(Constance.NAME)!!
//            name2 = data.getStringExtra(Constance.NAME2)!!
//            name3 = data.getStringExtra(Constance.NAME3)!!
//            avatarImageId = data.getIntExtra(Constance.AVATAR_ID, 0)
//            bindingClass.imAvatar.visibility = View.VISIBLE
//            bindingClass.imAvatar.setImageResource(avatarImageId)
//            val textInfo = "$name $name2 $name3"
//            bindingClass.tvInfo.text = textInfo
//            bindingClass.bHide.visibility = View.GONE
//            bindingClass.bExit.text = "Выйти"
//
//
//        }
//    }

    fun onClickSignIn(view: View){
        if(bindingClass.imAvatar.isVisible && bindingClass.tvInfo.text.toString() != "Такого аккаунта не существует"){

            bindingClass.imAvatar.visibility = View.INVISIBLE
            bindingClass.tvInfo.text = ""
            bindingClass.bHide.visibility = View.VISIBLE
            bindingClass.bExit.text = getString(R.string.sign_in)


        } else {
            val intent = Intent(this, SignInUpAct::class.java)
            intent.putExtra(Constance.SIGN_STATE, Constance.SIGN_IN_STATE)
            signInLauncher?.launch(intent)
//            val intent = Intent(this, SignInUpAct::class.java)
//            startActivityForResult(intent, Constance.REQUEST_CODE_SIGN_IN)
        }
    }

    fun onClickSignUp(view: View){
        val intent = Intent(this, SignInUpAct::class.java)
        intent.putExtra(Constance.SIGN_STATE, Constance.SIGN_UP_STATE)
        signUpLauncher?.launch(intent)

//        val intent = Intent(this, SignInUpAct::class.java)
//        startActivityForResult(intent, Constance.REQUEST_CODE_SIGN_UP)
    }
}