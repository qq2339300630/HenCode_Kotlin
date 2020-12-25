package com.example.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.app.entity.User
import com.example.app.widget.CodeView
import com.example.core.utils.CacheUtils
import com.example.lesson.LessonActivity
import toast

class MainActivity : AppCompatActivity() , View.OnClickListener {

    private lateinit var  et_username: EditText
    private lateinit var  et_password: EditText
    private lateinit var et_code: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        et_username = findViewById(R.id.et_username)
        et_password = findViewById(R.id.et_password)
        et_code = findViewById(R.id.et_code)

        et_username.setText(CacheUtils.get(usernameKey))
        et_password.setText(CacheUtils.get(passwordKey))

        val btn_login = findViewById<Button>(R.id.btn_login)
        val img_code = findViewById<CodeView>(R.id.code_view)
        btn_login.setOnClickListener(this)
        img_code.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if (p0 is CodeView) {
            p0.updateCode()
        } else if (p0 is Button) {
            login()
        }
    }

    private fun login() {
        val user = User(et_username.text.toString(), et_password.text.toString(), et_code.text.toString())
        if (verify(user)) {
            CacheUtils.save(usernameKey, et_username.text.toString())
            CacheUtils.save(passwordKey, et_password.text.toString())
            startActivity(Intent(this, LessonActivity::class.java))
        }
    }

    private fun verify(user: User): Boolean {

        if (user.username != null && user.username?.length!! < 4) {
            toast("用户名不合法")
            return false
        }
        if (user.password != null && user.password?.length!! < 4) {
            toast("密码不合法")
            return false
        }
        return true
    }

    companion object {
        private const val usernameKey = "username"
        private const val passwordKey = "password"
    }

}