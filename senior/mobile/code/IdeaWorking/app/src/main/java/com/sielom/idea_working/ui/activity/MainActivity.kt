package com.sielom.idea_working.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sielom.idea_working.R
import com.sielom.idea_working.ui.fragment.*

private const val SH_TITLE = "com.sielom.idea_work"
private const val SH_KEY_USER_ID = "user_id"

class MainActivity : AppCompatActivity(), GuestFragment.Callbacks,
    IdeaInformationFragment.Callbacks, InformationAboutPersonsFragment.Callbacks,
    ExampleWorkFragment.Callbacks, EndSliderFragment.Callbacks, SignUpFragment.Callbacks,
    SignInFragment.Callbacks, PersonProjectsFragment.Callbacks, PersonIdeasFragment.Callbacks,
    PersonDataFragment.Callbacks, ProjectsFragment.Callbacks {

    private val sharedPreferences by lazy {
        getSharedPreferences(SH_TITLE, MODE_PRIVATE)
    }

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.myInformationMenuItem -> replaceFragment(
                    PersonProjectsFragment.newInstance(
                        getUserId()
                    )
                )
                R.id.projectsMenuItem -> replaceFragment(ProjectsFragment.newInstance(getUserId()))
                R.id.ideasMenuItem -> replaceFragment(IdeasFragment.newInstance(getUserId()))
                R.id.specialistsMenuItem -> replaceFragment(
                    SpecialistsFragment.newInstance(
                        getUserId()
                    )
                )
            }

            true
        }

        val userId = sharedPreferences.getInt(SH_KEY_USER_ID, -1)

        var visibleBottomNavigationView: Int = View.GONE
        val fragment = if (userId != -1) {
            visibleBottomNavigationView = View.VISIBLE
            PersonProjectsFragment.newInstance(userId)
        } else {
            GuestFragment()
        }

        replaceFragment(fragment)
        bottomNavigationView.visibility = visibleBottomNavigationView
    }

    override fun onSignUpButtonClick() {
        replaceFragment(SignUpFragment())
        bottomNavigationView.visibility = View.GONE
    }

    override fun onSignInButtonClick() {
        replaceFragment(SignInFragment())
        bottomNavigationView.visibility = View.GONE
    }

    override fun onStartSliderButtonClick() {
        replaceFragmentWithAddToBackStack(IdeaInformationFragment())
        bottomNavigationView.visibility = View.GONE
    }

    override fun onGoToInformationAboutPersonsButtonClick() {
        replaceFragmentWithAddToBackStack(InformationAboutPersonsFragment())
        bottomNavigationView.visibility = View.GONE
    }

    override fun onGoToExampleWorkButtonClick() {
        replaceFragmentWithAddToBackStack(ExampleWorkFragment())
        bottomNavigationView.visibility = View.GONE
    }

    override fun onGoToEndSliderButtonClick() {
        replaceFragmentWithAddToBackStack(EndSliderFragment())
        bottomNavigationView.visibility = View.GONE
    }

    override fun onOkButtonClick() {
        clearBackStack()
        replaceFragment(SignUpFragment())
        bottomNavigationView.visibility = View.GONE
    }

    override fun onSignInTextViewClick() {
        replaceFragment(SignInFragment())
        bottomNavigationView.visibility = View.GONE
    }

    override fun onSuccessfulSignUp(id: Int) {
        saveUserId(id)
        clearBackStack()
        replaceFragment(PersonProjectsFragment.newInstance(id))
    }

    override fun onCreateAccountTextView() {
        replaceFragment(SignUpFragment())
    }

    override fun onSuccessfulSignIn(userId: Int) {
        saveUserId(userId)
        clearBackStack()
        replaceFragment(PersonProjectsFragment.newInstance(userId))
    }

    override fun onPersonDataButtonClick() {
        val userId = getUserId()

        if (userId == -1) {
            logOut()
            Toast.makeText(applicationContext, "Не удалось авторизироваться", Toast.LENGTH_SHORT)
                .show()
            return
        }

        replaceFragment(PersonDataFragment.newInstance(userId))
    }

    override fun onPersonIdeasButtonClick() {
        val userId = getUserId()
        if (userId == -1) {
            logOut()
            Toast.makeText(applicationContext, "Не удалось авторизироваться", Toast.LENGTH_SHORT)
                .show()
            return
        }

        replaceFragment(PersonIdeasFragment.newInstance(userId))
    }

    override fun onEnterProjectButtonClickFromPersonProjectFragment(projectId: Int) {
        val userId = getUserId()
        if (userId == -1) {
            logOut()
            Toast.makeText(applicationContext, "Не удалось авторизироваться", Toast.LENGTH_SHORT)
                .show()
            return
        }

        replaceFragmentWithAddToBackStack(ProjectFragment.newInstance(userId, projectId))
    }

    override fun onPersonProjectsButtonClickFromPersonData() {
        val userId = getUserId()
        if (userId == -1) {
            logOut()
            Toast.makeText(applicationContext, "Не удалось авторизироваться", Toast.LENGTH_SHORT)
                .show()
            return
        }

        replaceFragment(PersonProjectsFragment.newInstance(userId))
    }

    override fun onPersonIdeasButtonClickFromPersonData() {
        val userId = getUserId()
        if (userId == -1) {
            logOut()
            Toast.makeText(applicationContext, "Не удалось авторизироваться", Toast.LENGTH_SHORT)
                .show()
            return
        }

        replaceFragment(PersonIdeasFragment.newInstance(userId))
    }

    override fun onLogOutButtonClick() {
        logOut()
    }

    override fun onPersonProjectsButtonFromPersonIdea() {
        val userId = getUserId()
        if (userId == -1) {
            logOut()
            Toast.makeText(applicationContext, "Не удалось авторизироваться", Toast.LENGTH_SHORT)
                .show()
            return
        }

        replaceFragment(PersonProjectsFragment.newInstance(userId))
    }

    override fun onPersonDataButtonButtonFromPersonIdea() {
        val userId = getUserId()
        if (userId == -1) {
            logOut()
            Toast.makeText(applicationContext, "Не удалось авторизироваться", Toast.LENGTH_SHORT)
                .show()
            return
        }

        replaceFragment(PersonDataFragment.newInstance(userId))
    }

    override fun onEnterProjectButtonClickFromProjectsFragment(projectId: Int) {
        val userId = getUserId()
        if (userId == -1) {
            logOut()
            Toast.makeText(applicationContext, "Не удалось авторизироваться", Toast.LENGTH_SHORT)
                .show()
            return
        }

        replaceFragmentWithAddToBackStack(ProjectFragment.newInstance(userId, projectId))
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()

        bottomNavigationView.visibility = View.VISIBLE
    }

    private fun replaceFragmentWithAddToBackStack(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, fragment)
            .commit()

        bottomNavigationView.visibility = View.VISIBLE
    }

    private fun clearBackStack() {
        for (i in (0..supportFragmentManager.backStackEntryCount)) {
            supportFragmentManager.popBackStack(i, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    private fun logOut() {
        clearUserId()
        clearBackStack()
        bottomNavigationView.visibility = View.GONE
        replaceFragment(GuestFragment())
    }

    private fun saveUserId(id: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(SH_KEY_USER_ID, id)
        editor.apply()
    }

    private fun getUserId(): Int {
        return sharedPreferences.getInt(SH_KEY_USER_ID, -1)
    }

    private fun clearUserId() {
        val editor = sharedPreferences.edit()
        editor.remove(SH_KEY_USER_ID)
        editor.apply()
    }


}