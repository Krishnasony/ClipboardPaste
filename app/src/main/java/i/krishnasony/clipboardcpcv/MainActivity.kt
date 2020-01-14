package i.krishnasony.clipboardcpcv

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * checkClipboardValidation(clipboard: ClipboardManager) is used to check clipboard manager
 * checkDataValidation():Boolean is used to validate clipboard clip data
 *getClipBoardData() is used to get clipboard clipData
 */

class MainActivity : AppCompatActivity() {
    private var clipData:CharSequence ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        clipData = getClipBoardData()
        clipData?.let {
            if (checkDataValidation()){
                pnrText.setText(clipData.toString())
                this.showToast(message = "$it Pasted Successfully !")
            }
        }
    }


    private fun getClipBoardData():CharSequence?{
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        return if (checkClipboardValidation(clipboard)){
            clipboard.primaryClip?.getItemAt(0)?.text
        }else null
    }

    private  fun checkClipboardValidation(clipboard: ClipboardManager):Boolean {
        return when {
            !clipboard.hasPrimaryClip() -> {
                false
            }
            (clipboard.primaryClipDescription?.mimeTypeCount) == 0 -> {
                false
            }
            else -> {
                true
            }
        }
    }
    private fun checkDataValidation():Boolean{
        var validation = true
        clipData?.let {
            if (it.isEmpty()){
                validation = false
                Log.e("Validation:","Clipboard is empty")
            }else if (!TextUtils.isDigitsOnly(it.toString().trim())){
                validation = false
                Log.e("Validation:","Clipboard doesn't contain only digit")
            }else if (TextUtils.getTrimmedLength(it)!=10){
                validation = false
                Log.e("Validation:","Clipboard doesn't contain 10 digit")
            }else validation = true
        }
        return validation
    }

    private fun Context.showToast(message:String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()

    }

}
