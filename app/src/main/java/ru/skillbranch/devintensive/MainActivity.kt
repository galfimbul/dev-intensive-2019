package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity(), View.OnClickListener{

    lateinit var benderImage: ImageView
    lateinit var textTxt: TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn: ImageView
    lateinit var benderObj: Bender

    /**
     * Вызывается при первом создании или запуске Activity.
     * Здесь задается внешний вид активности (UI) через setContentView().
     * Инициализируются представления и связываются с необходимыми данными и ресурсами.
     * Связываются данные со списками.
     *
     * Так же метод предоставляет Bundle с ранее сохраненным состоянием, если оно было.
     *
     * Всегда сопровождается вызовом onStart().
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //benderImage = findViewById(R.id.iv_bender)
        benderImage = iv_bender
        textTxt = tv_text
        messageEt = et_message
        sendBtn = iv_send

        val status = savedInstanceState?.getString("STATUS") ?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString("QUESTION") ?: Bender.Question.NAME.name
        benderObj = Bender(Bender.Status.valueOf(status), Bender.Question.valueOf(question))
        Log.d("M_MainActivity","onCreate $status $question")

        val (r,g,b) = benderObj.status.color
        benderImage.setColorFilter(Color.rgb(r,g,b),PorterDuff.Mode.MULTIPLY)
        textTxt.text = benderObj.askQuestion()
        sendBtn.setOnClickListener(this)
        messageEt.setOnEditorActionListener{ v, actionId, event ->
            when(actionId){
            EditorInfo.IME_ACTION_DONE ->{
                onClick(v)
                false
            }
            else -> false
        }
        }

    }

    /**
     * Если Activity возвращается в приоритетный режим после вызова onStop(),
     * то тогда вызывается onRestart().
     * То есть после того как пользователь сам вернулся в приложение.
     * Всегда сопровоздается вызовом onStart().
     *
     * используется для действий которые должны выполняться только при повторном старте Activity.
     */

    override fun onRestart() {
        super.onRestart()
        Log.d("M_MainActivity","onRestart")
    }

    /**
     * UI еще не виден пользователю, но вскоре будет виден,
     * вызывается непосредственно перед тем, как Activity становится видимой пользователю.
     * чтение из базы данных
     * запуск сложной анимации
     * запуск потоков, отслеживания показаний датчиков, запросов к GPS, сервисов или
     * других процессов, которые нужны исключительно для обновления пользовательского интерфейса
     *
     * Затем идет onResume(), если Activity выходит на передний план.
     */

    override fun onStart() {
        super.onStart()
        Log.d("M_MainActivity","onStart")
    }

    /**
     * вызывается, когда Activity начнет взаимодействовать с пользователем.
     * запуск воспроизведения анимации, аудио и видео
     * регистрации любых BroadcastReceiver или других процессов, которые вы
     * освободили/приостановили в onPause()
     * выполнение любых другие инициализации, которые должны происходить, когда Activity вновь активна.
     * Должен быть максимально легкий и быстрый код для сохранения быстродействия и отзывчивости приложения.
     */

    override fun onResume() {
        super.onResume()
        Log.d("M_MainActivity","onResume")
    }

    /**
     * вызывается после сворачивания текущей активности или перехода к новому.
     * От onPause() можно перейти к вызову либо onResume(), либо onStop().
     * остановка анимации, аудио и видео
     * сохранение состояния пользовательского ввода (легкие процессы)
     * сохранение в DB если данные должны быть доступны в новой Activity
     * остановка сервисов, подписок, BroadcastReceiver
     * Должен быть максимально легкий и быстрый код для сохранения быстродействия и отзывчивости приложения.
     */
    override fun onPause() {
        super.onPause()
        Log.d("M_MainActivity","onPause")
    }

    /**
     * вызывается, когда Activity становится невидимым для пользователя.
     * Это может произойти при её уничтожении, или если была запущена другая Activity
     * (существующая или новая) перекрывшая окно текущей Activity.
     * запись в базу данных
     * приостановка сложной анимации
     * приостановка потоков, отслеживания показаний датчиков, запросов к GPS, таймеров,
     * сервисов или других процессов, которые нужны исключительно для обновления
     * пользовательского интерфейса
     * Не вызывается при вызове onFinish у Activity.
     */
    override fun onStop() {
        super.onStop()
        Log.d("M_MainActivity","onStop")
    }

    /**
     *  вызывается по окончании работы Activity (пользователь закрывает
     * приложение через клавишу back, или удаляет из списка активных приложений),
     * при вызове метода finish()
     * высвобождение ресурсов
     * дополнительная перестраховка если ресурсы не были выгружены или процессы не были остановлены ранее
     */

    override fun onDestroy() {
        super.onDestroy()
        Log.d("M_MainActivity","onDestroy")
    }

    /**
     * Этот метод сохраняет состояние представления в Bundle.
     * Для API Level < 28 (Android P) этот метод будет выполняться до onStop()  и нет никаких гарантий произойдет ли это
     * до или после onPause().
     * Для API Level >= 28 будет вызван после onStop()
     * Не будет вызван если Activity будет явно закрыто пользователем через системную клавишу Back
     */

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString("STATUS",benderObj.status.name)
        outState?.putString("QUESTION",benderObj.question.name)
        Log.d("M_MainActivity","onSaveInstanceState${benderObj.status.name} ${benderObj.question.name}")
    }
    override fun onClick(v: View?) {
        if(v?.id == R.id.iv_send ||v?.id == R.id.et_message){
            val (phrase, color) = benderObj.listenAnswer(messageEt.text.toString())
            messageEt.setText("")
            val (r,g,b) = color
            benderImage.setColorFilter(Color.rgb(r,g,b),PorterDuff.Mode.MULTIPLY)
            textTxt.text = phrase
        }
    }
}
