package sbs.pros.historyplaces

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class NoQRDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage("Вы не дали разрешение на использование камеры, чтобы сканировать QR адреса")
            .setPositiveButton("Ok") { _,_ -> }
            .create()

    companion object {
        const val TAG = "NoQRDialog"
    }
}

class ReasonDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage("Разрешение на использование камеры необходимо, чтобы сканировать QR адреса")
            .setPositiveButton("Ok") { _,_ -> }
            .create()

    companion object {
        const val TAG = "ReasonDialog"
    }
}

class PleaseCameraDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage("Пожалуйста, дайте разрешение на использование камеры, чтобы отсканировать QR адреса")
            .setPositiveButton("Ok") { _,_ -> }
            .create()

    companion object {
        const val TAG = "PleaseCameraDialog"
    }
}