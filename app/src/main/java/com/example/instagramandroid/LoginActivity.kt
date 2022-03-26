package com.example.instagramandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //check the user is logged in
        if (ParseUser.getCurrentUser() != null) {
            gotoMainActivity()
        }

        findViewById<Button>(R.id.login_button).setOnClickListener {
            val username = findViewById<EditText>(R.id.et_username).text.toString()
            val password = findViewById<EditText>(R.id.et_password).text.toString()
            loginUser(username, password)
        }

        findViewById<Button>(R.id.signupBtn).setOnClickListener {
            val username = findViewById<EditText>(R.id.et_username).text.toString()
            val password = findViewById<EditText>(R.id.et_password).text.toString()
            signUpUser(username, password)
        }
    }

    private fun loginUser (username: String, password: String) {
        ParseUser.logInInBackground(username, password, ({ user, e ->
            if (user != null) {
                Log.i(TAG, "Login")
                gotoMainActivity()
            } else {
                e.printStackTrace()
                Toast.makeText(this, "Error Log In", Toast.LENGTH_SHORT).show()
            }})
        )
    }

    private fun signUpUser (username: String, password: String) {
        // Create the ParseUser
        val user = ParseUser()

        // Set fields for the user to be created
        user.setUsername(username)
        user.setPassword(password)

        user.signUpInBackground { e ->
            if (e == null) {
                // Hooray! Let them use the app now.
                Log.i(TAG, "Sign Up")
                gotoMainActivity()
            } else {
                // Sign up didn't succeed. Look at the ParseException
                // to figure out what went wrong
                e.printStackTrace()
                Toast.makeText(this, "Error Sign Up", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun gotoMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        const val TAG = "LoginActivity"
    }
}