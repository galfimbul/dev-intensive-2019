package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import ru.skillbranch.devintensive.App
import ru.skillbranch.devintensive.R
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

        val bitmapCanvas = Canvas(bitmap)
        paintBitmap.color = Color.GREEN
        bitmapCanvas.drawCircle(200F,200F,100F,paintBitmap) // рисуем круг на битмапе который создали
        paintBitmap.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        /** заставляем кисть вписать в нарисованный круг следующий нарисованный объект, отрезав все что не входит в границы круга
 **/
        bitmapCanvas.drawRect(Rect(350,310,90,90),paintBitmap) // рисуем следующий объект
        paintBorder.style = Paint.Style.STROKE // кисть рисует рамку
        paintBorder.strokeWidth = 10F // толщина рамки. Половина внутри, половина снаружи
        paintBorder.color = Color.WHITE
        bitmapCanvas.drawCircle(200F,200F,95F,paintBorder)/**
 чтобы рамка слезла вся, рисуем круг радиусом на половину толщины рамки меньше**/
        paintBitmap.textSize = Utils.convertDpToPixels(30).toFloat() // задаем размер текста
        paintBitmap.textAlign = Paint.Align.CENTER // выравниваем его
        paintBitmap.color = Color.WHITE // меняем цвет кисти на нужный. Все что раньше - не меняет цвета
        bitmapCanvas.drawText("WORK!!",200F,220F,paintBitmap) // рисуем текст в координатах круга
        canvas.drawBitmap(bitmap,0F,0F,null) // рисуем получившийся битмап на канве главной вью
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

/*class CircleImageView @JvmOverloads constructor (
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ImageView(context, attrs, defStyleAttr) {
    companion object {
        private const val DEFAULT_BORDER_COLOR: Int = Color.WHITE
    }

    private var borderColor = DEFAULT_BORDER_COLOR
    private var borderWidth = Utils.convertDpToPixels(2)
    private var text: String? = null
    private var bitmap: Bitmap? = null

    init {
        if (attrs != null) {
            val attrVal = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
            borderColor = attrVal.getColor(R.styleable.CircleImageView_cv_borderColor, DEFAULT_BORDER_COLOR)
            borderWidth = attrVal.getDimensionPixelSize(R.styleable.CircleImageView_cv_borderWidth, borderWidth)
            attrVal.recycle()
        }
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

    override fun onDraw(canvas: Canvas) {
        var bitmap = getBitmapFromDrawable() ?: return
        if (width == 0 || height == 0) return

        bitmap = getScaledBitmap(bitmap, width)
        bitmap = getCenterCroppedBitmap(bitmap, width)
        bitmap = getCircleBitmap(bitmap)

        if (borderWidth > 0)
            bitmap = getStrokedBitmap(bitmap, borderWidth, borderColor)

        canvas.drawBitmap(bitmap, 0F, 0F, null)
    }

    fun generateAvatar(text: String?, sizeSp: Int, theme: Resources.Theme){
        *//* don't render if initials haven't changed *//*
        if (bitmap == null || text != this.text){
            val image =
                if (text == null) {
                    generateDefAvatar(theme)
                }
                else generateLetterAvatar(text, sizeSp, theme)

            this.text = text
            bitmap = image
            //setImageBitmap(bitmap)
            invalidate()
        }
    }

    private fun generateLetterAvatar(text: String, sizeSp: Int, theme: Resources.Theme): Bitmap {
        val image = generateDefAvatar(theme)

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

    private fun generateDefAvatar(theme: Resources.Theme): Bitmap {
        val image = Bitmap.createBitmap(layoutParams.height, layoutParams.height, Bitmap.Config.ARGB_8888)
        val color = TypedValue()
        theme.resolveAttribute(R.attr.colorAccent, color, true)


        val canvas = Canvas(image)
        canvas.drawColor(color.data)

        return image
    }

    private fun getStrokedBitmap(squareBmp: Bitmap, strokeWidth: Int, color: Int): Bitmap {
        val inCircle = RectF()
        val strokeStart = strokeWidth / 2F
        val strokeEnd = squareBmp.width - strokeWidth / 2F

        inCircle.set(strokeStart , strokeStart, strokeEnd, strokeEnd)

        val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        strokePaint.color = color
        strokePaint.style = Paint.Style.STROKE
        strokePaint.strokeWidth = strokeWidth.toFloat()

        val canvas = Canvas(squareBmp)
        canvas.drawOval(inCircle, strokePaint)

        return squareBmp
    }

    private fun getCenterCroppedBitmap(bitmap: Bitmap, size: Int): Bitmap {
        val cropStartX = (bitmap.width - size) / 2
        val cropStartY = (bitmap.height - size) / 2

        return Bitmap.createBitmap(bitmap, cropStartX, cropStartY, size, size)
    }

    private fun getScaledBitmap(bitmap: Bitmap, minSide: Int) : Bitmap {
        return if (bitmap.width != minSide || bitmap.height != minSide) {
            val smallest = min(bitmap.width, bitmap.height).toFloat()
            val factor = smallest / minSide
            Bitmap.createScaledBitmap(bitmap, (bitmap.width / factor).toInt(), (bitmap.height / factor).toInt(), false)
        } else bitmap
    }

    private fun getBitmapFromDrawable(): Bitmap? {
        if (bitmap != null)
            return bitmap

        if (drawable == null)
            return null

        if (drawable is BitmapDrawable)
            return (drawable as BitmapDrawable).bitmap

        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

    private fun getCircleBitmap(bitmap: Bitmap): Bitmap {
        val smallest = min(bitmap.width, bitmap.height)
        val outputBmp = Bitmap.createBitmap(smallest, smallest, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(outputBmp)

        val paint = Paint()
        val rect = Rect(0, 0, smallest, smallest)

        paint.isAntiAlias = true
        paint.isFilterBitmap = true
        paint.isDither = true
        canvas.drawARGB(0, 0, 0, 0)
        canvas.drawCircle(smallest / 2F, smallest / 2F, smallest / 2F, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)

        return outputBmp
    }
}*/
