package com.android.testapp.core.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.android.testapp.R
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment(layout: Int) : Fragment(layout) {

    private lateinit var onBackPressedListener: () -> Unit

    fun onBackPressed(listener: () -> Unit = {}) {
        onBackPressedListener = listener
    }

    fun showToast(msg: String, toastLength: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), msg, toastLength).show()
    }

    fun showSnackBar(
        msg: String,
        view: View = requireActivity().window.decorView.rootView,
        snackBarLength: Int = Snackbar.LENGTH_SHORT
    ) {
        Snackbar.make(
            view, msg, snackBarLength
        ).show()
    }

    fun showDialog(
        title: String = "",
        message: String,
        onPositiveButtonClicked: () -> Unit = {},
        onNegativeButtonClicked: () -> Unit = {},
        positiveButtonText: Int = R.string.yes,
        negativeButtonText: Int = R.string.no,
    ) {
        val dialogBuilder = AlertDialog.Builder(requireContext()).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(
                positiveButtonText
            ) { dialog, id ->
                onPositiveButtonClicked.invoke()
                dialog.cancel()
            }
            setNegativeButton(
                negativeButtonText
            ) { dialog, id ->
                onNegativeButtonClicked.invoke()
                dialog.cancel()
            }
        }.create().show()
    }

    fun hideKeyboard(){
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        requireView().isFocusableInTouchMode = true
        requireView().requestFocus()
        requireView().setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && this@BaseFragment::onBackPressedListener.isInitialized) {
                        onBackPressedListener.invoke()
                        return true
                    }
                }
                return false
            }
        })
        super.onActivityCreated(savedInstanceState)
    }

}