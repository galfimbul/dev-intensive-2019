package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.provider.ContactsContract
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import ru.skillbranch.devintensive.App
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.utils.Utils
import java.lang.Math.min

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
    private var borderWidth = Utils.convertDpToPixels(5)
    private var initials:String? = null

    init {
        if(attrs != null){
            val attrsValues = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
            borderColor = attrsValues.getColor(R.styleable.CircleImageView_cv_borderColor, DEFAULT_BORDER_COLOR)
            borderWidth = attrsValues.getDimensionPixelSize(R.styleable.CircleImageView_cv_borderWidth,borderWidth)
            attrsValues.recycle()
        }
    }

    fun getBorderWidth(): Int = Utils.convertPixelsToDp(borderWidth)

    fun getBorderColor(): Int = borderColor

    fun setBorderColor(hex: String) {
        borderColor = Color.parseColor(hex)
        this.invalidate()
    }

    fun setBorderColor(@ColorRes colorId: Int) {
        borderColor = ContextCompat.getColor(App.applicationContext(), colorId)
        this.invalidate()
    }
    fun setBorderWidth(dp: Int) {
        borderWidth = Utils.convertDpToPixels(dp)
        this.invalidate()
    }



    private fun createSimpleAvatar(theme: Resources.Theme) :Bitmap{
        val cy = layoutParams.height
        val cx = layoutParams.width
        val image = Bitmap.createBitmap(cy, cy, Bitmap.Config.ARGB_8888)
        val color = TypedValue()
        theme.resolveAttribute(R.attr.colorAccent, color, true)
        val canvas = Canvas(image)
        canvas.drawColor(Color.WHITE)
        return image
    }
    private fun createCircleAvatar(bitmap: Bitmap):Bitmap{
        val cy = layoutParams.height
        val cx = layoutParams.width
        val image = Bitmap.createBitmap(cy,cy, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(image)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.GREEN
        canvas.drawCircle(cy/2F,cy/2F,cy/2F,paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap,0f,0f,paint)
        return image
    }
    private fun createBorderedAvatar(bitmap:Bitmap):Bitmap{
        val cy = layoutParams.height
        val cx = layoutParams.width
        val image = Bitmap.createBitmap(cy,cy, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(image)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val paintBitmap = Paint(Paint.ANTI_ALIAS_FLAG)
        //paint.color = borderColor
        paint.color = Color.GREEN
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = Utils.convertDpToPixels(borderWidth).toFloat()
        canvas.drawBitmap(bitmap,0f,0f,paintBitmap)
        canvas.drawCircle(cy/2F,cy/2F,cy/2F-borderWidth/2,paint)
        return image
    }
    private fun createTextAvatar(text: String,size: Int,theme: Resources.Theme):Bitmap{
        val cy = layoutParams.height
        val cx = layoutParams.width
        //val image = Bitmap.createBitmap(cy,cy, Bitmap.Config.ARGB_8888)
        val image = createSimpleAvatar(theme)
        val canvas = Canvas(image)
        val paintText = Paint(Paint.ANTI_ALIAS_FLAG)
        paintText.textAlign = Paint.Align.CENTER
        //paintText.textSize = Utils.convertSpToPx(App.applicationContext(),size).toFloat()
        paintText.textSize = 200F
        paintText.color = Color.BLACK
        val paintBitmap = Paint(Paint.ANTI_ALIAS_FLAG)
        canvas.drawBitmap(image,0f,0f,paintBitmap)
        canvas.drawText(text,cx/2f,cy/2f+cy/4f,paintText)
        return image
    }


    fun createAvatar(text:String?,size:Int,theme:Resources.Theme){
        val image =
            if (text == null) {
                createSimpleAvatar(theme)
            }
            else generateLetterAvatar(text, size, theme)
        setImageBitmap(image)
    }



    private fun generateLetterAvatar(text: String, sizeSp: Int, theme: Resources.Theme): Bitmap {
        val image = createSimpleAvatar(theme)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.textSize = sizeSp.toFloat()
        paint.color = Color.WHITE
        paint.textAlign = Paint.Align.CENTER

        val textBounds = Rect()
        paint.getTextBounds(text, 0, text.length, textBounds)

        val backgroundBounds = RectF()
        backgroundBounds.set(0f, 0f, layoutParams.height.toFloat(), layoutParams.height.toFloat())

        val textBottom = backgroundBounds.centerY() - textBounds.exactCenterY()
        val canvas = Canvas(image)
        canvas.drawText(text, backgroundBounds.centerX(), textBottom, paint)

        return image
    }



    override fun onDraw(canvas: Canvas) {
        val theme = App.applicationContext().theme
        var image = createSimpleAvatar(theme)
        //val initials = Utils.toInitials("Vasa","Pupkin")
        image = createCircleAvatar(image)
        if (borderWidth>0) {
            image = createBorderedAvatar(image)
        }
        //image = createTextAvatar(image,initials!!,10)
        canvas.drawBitmap(image,0F,0F,null) // рисуем получившийся битмап на канве главной вью
    }

}


/*val bitmapCanvas = Canvas(bitmap)
        paintBitmap.color = Color.GREEN
        bitmapCanvas.drawCircle(200F,200F,100F,paintBitmap) // рисуем круг на битмапе который создали
        paintBitmap.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        *//** заставляем кисть вписать в нарисованный круг следующий нарисованный объект, отрезав все что не входит в границы круга
 **//*
        bitmapCanvas.drawRect(Rect(350,310,90,90),paintBitmap) // рисуем следующий объект
        paintBorder.style = Paint.Style.STROKE // кисть рисует рамку
        paintBorder.strokeWidth = 10F // толщина рамки. Половина внутри, половина снаружи
        paintBorder.color = Color.WHITE
        bitmapCanvas.drawCircle(200F,200F,95F,paintBorder)*//**
чтобы рамка слезла вся, рисуем круг радиусом на половину толщины рамки меньше**//*
        paintBitmap.textSize = Utils.convertDpToPixels(30).toFloat() // задаем размер текста
        paintBitmap.textAlign = Paint.Align.CENTER // выравниваем его
        paintBitmap.color = Color.WHITE // меняем цвет кисти на нужный. Все что раньше - не меняет цвета
        bitmapCanvas.drawText("WORK!!",200F,220F,paintBitmap)*/ // рисуем текст в координатах круга*/

