package i.krishnasony.clipboardcpcv

import android.content.ClipDescription.MIMETYPE_TEXT_HTML
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        paste.setOnClickListener {
            pastePnrFromClipboard()
        }
    }


    private fun pastePnrFromClipboard(){
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (clipboard.hasPrimaryClip() && clipboard.primaryClipDescription != null&&(clipboard.primaryClipDescription?.hasMimeType(MIMETYPE_TEXT_HTML)!! || clipboard.primaryClipDescription?.hasMimeType(MIMETYPE_TEXT_PLAIN)!!)){
            val pasteData:CharSequence = clipboard.primaryClip!!.getItemAt(0).text
            if (pasteData.isNotEmpty()&&TextUtils.getTrimmedLength(pasteData)==10&&TextUtils.isDigitsOnly(pasteData.toString().trim()))
            {
                pnrText.setText( pasteData.toString().trim())
                this.showToast("$pasteData")
            }else  this.showToast("Clipboard data is not contain digit or may be short length")

        }else{
            this.showToast("No data found in clipboard")
        }

    }

    private fun Context.showToast(message:String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()

    }

}
