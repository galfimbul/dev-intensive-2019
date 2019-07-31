package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import ru.skillbranch.devintensive.App
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.utils.Utils

/**
 * Created by Alexander Shvetsov on 31.07.2019
 */
class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {
    companion object{
        private const val DEFAULT_BORDER_COLOR: Int = Color.WHITE
    }
    private var borderColor = DEFAULT_BORDER_COLOR
    private var borderWidth = Utils.convertDpToPixels(2)
    private var initials:String? = null

    init {
        if(attrs != null){
            val attrsValues = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
            borderColor = attrsValues.getColor(R.styleable.CircleImageView_cv_borderColor, DEFAULT_BORDER_COLOR)
            borderWidth = attrsValues.getDimensionPixelSize(R.styleable.CircleImageView_cv_borderWidth,borderWidth)
            attrsValues.recycle()
        }
    }


    val paintBorder = Paint(Paint.ANTI_ALIAS_FLAG)
    val paintBitmap = Paint(Paint.ANTI_ALIAS_FLAG)
    val bitmap = Bitmap.createBitmap(Utils.convertDpToPixels(150),Utils.convertDpToPixels(150),Bitmap.Config.ARGB_8888)
    override fun onDraw(canvas: Canvas) {
        paintBorder.color = Color.GREEN
        paintBorder.style = Paint.Style.STROKE
        paintBorder.strokeWidth = 10F
        paintBitmap.color = Color.WHITE
        paintBitmap.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC)
        paintBorder.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawRect(Rect(350,310,90,90),paintBitmap)
        canvas.drawCircle(240F,240F,60F,paintBorder)
    }

    private fun createSimpleAvatar(canvas: Canvas,width:Int,height:Int) {


    }

    private fun createTextAvatar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getBorderWidth(): Int = Utils.convertPixelsToDp(borderWidth)

    fun setBorderWidth(dp: Int) {
        borderWidth = Utils.convertDpToPixels(dp)
        this.invalidate()
    }

    fun getBorderColor(): Int = borderColor

    fun setBorderColor(hex: String) {
        borderColor = Color.parseColor(hex)
        this.invalidate()
    }

    fun setBorderColor(@ColorRes colorId: Int) {
        borderColor = ContextCompat.getColor(App.applicationContext(), colorId)
        this.invalidate()
    }
}