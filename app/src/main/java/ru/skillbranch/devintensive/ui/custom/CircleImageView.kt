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
import ru.skillbranch.devintensive.utils.Utils
import android.graphics.RectF


/**
 * Created by Alexander Shvetsov on 31.07.2019
 */
class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {
    companion object {
        private const val DEFAULT_BORDER_COLOR: Int = Color.WHITE
    }

    private var borderColor = DEFAULT_BORDER_COLOR
    private var borderWidth = Utils.convertDpToPixels(2)

    init {
        if (attrs != null) {
            val attrsValues =
                context.obtainStyledAttributes(attrs, ru.skillbranch.devintensive.R.styleable.CircleImageView)
            borderColor = attrsValues.getColor(
                ru.skillbranch.devintensive.R.styleable.CircleImageView_cv_borderColor,
                DEFAULT_BORDER_COLOR
            )
            borderWidth = attrsValues.getDimensionPixelSize(
                ru.skillbranch.devintensive.R.styleable.CircleImageView_cv_borderWidth,
                borderWidth
            )
            attrsValues.recycle()
        }
        //createSimpleAvatar(context.theme)
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


    fun createSimpleAvatar(theme: Resources.Theme): Bitmap {
        val cy = layoutParams.height
        val cx = layoutParams.width
        val image = Bitmap.createBitmap(cx, cy, Bitmap.Config.ARGB_8888)
        val color = TypedValue()
        theme.resolveAttribute(ru.skillbranch.devintensive.R.attr.colorAccent, color, true)
        val canvas = Canvas(image)
        canvas.drawColor(Color.RED)
        canvas.drawBitmap(image, 0f, 0f, null)
        return image
    }

    private fun createCircleAvatar(bitmap: Bitmap): Bitmap {
        val cy = layoutParams.height
        val cx = layoutParams.width
        val image = Bitmap.createBitmap(cy, cy, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(image)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.GREEN
        canvas.drawCircle(cy / 2F, cy / 2F, cy / 2F, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        return image
    }

    private fun createBorderedAvatar(bitmap: Bitmap): Bitmap {
        val cy = layoutParams.height
        val cx = layoutParams.width
        val image = Bitmap.createBitmap(cy, cy, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(image)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val paintBitmap = Paint(Paint.ANTI_ALIAS_FLAG)
        //paint.color = borderColor
        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = Utils.convertDpToPixels(borderWidth).toFloat()
        canvas.drawBitmap(bitmap, 0f, 0f, paintBitmap)
        canvas.drawCircle(cy / 2F, cy / 2F, cy / 2F - borderWidth / 2, paint)
        return image
    }

    private fun createTextAvatar(text: String, size: Int, theme: Resources.Theme): Bitmap {
        val cy = layoutParams.height
        val paintText = Paint(Paint.ANTI_ALIAS_FLAG)
        val image = createSimpleAvatar(theme)
        val canvas = Canvas(image)
        paintText.textAlign = Paint.Align.CENTER
        paintText.textSize = size.toFloat()
        paintText.color = Color.WHITE
        val textBounds = Rect()
        paintText.getTextBounds(text, 0, text.length, textBounds)
        val backgroundBounds = RectF()
        backgroundBounds.set(0f, 0f, cy.toFloat(), cy.toFloat())

        val textBottom = backgroundBounds.centerY() - textBounds.exactCenterY()
        val paintBitmap = Paint(Paint.ANTI_ALIAS_FLAG)
        canvas.drawBitmap(image, 0f, 0f, paintBitmap)
        canvas.drawText(text, backgroundBounds.centerX(), textBottom, paintText)
        return image
    }


    fun createAvatar(text: String?, size: Int, theme: Resources.Theme) {
        val image = if (text.isNullOrEmpty()) {
            createSimpleAvatar(theme)
        } else
            createTextAvatar(text!!, size, theme)
        setImageBitmap(image)
    }

    override fun onDraw(canvas: Canvas) {
        var image = getBitmapFromDrawable() ?: return
        image = createCircleAvatar(image)
        if (borderWidth > 0) {
            image = createBorderedAvatar(image)
        }
        canvas.drawBitmap(image, 0F, 0F, null) // рисуем получившийся битмап на канве главной вью
    }

    private fun getBitmapFromDrawable(): Bitmap? {
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

    fun setInitials(initials: String) {
        createTextAvatar(initials,20,context.theme)

    }

}


/*val bitmapCanvas = Canvas(bitmap)
        paintBitmap.color = Color.GREEN
        bitmapCanvas.drawCircle(200F,200F,100F,paintBitmap) // рисуем круг на битмапе который создали
        paintBitmap.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        */
/** заставляем кисть вписать в нарисованный круг следующий нарисованный объект, отрезав все что не входит в границы круга
 **//*
        bitmapCanvas.drawRect(Rect(350,310,90,90),paintBitmap) // рисуем следующий объект
        paintBorder.style = Paint.Style.STROKE // кисть рисует рамку
        paintBorder.strokeWidth = 10F // толщина рамки. Половина внутри, половина снаружи
        paintBorder.color = Color.WHITE
        bitmapCanvas.drawCircle(200F,200F,95F,paintBorder)*/
/**
чтобы рамка слезла вся, рисуем круг радиусом на половину толщины рамки меньше**//*
        paintBitmap.textSize = Utils.convertDpToPixels(30).toFloat() // задаем размер текста
        paintBitmap.textAlign = Paint.Align.CENTER // выравниваем его
        paintBitmap.color = Color.WHITE // меняем цвет кисти на нужный. Все что раньше - не меняет цвета
        bitmapCanvas.drawText("WORK!!",200F,220F,paintBitmap)*/ // рисуем текст в координатах круга*/

