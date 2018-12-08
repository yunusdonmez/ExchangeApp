package com.example.yom.exchangeapp.ui

import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.yom.exchangeapp.R
import com.example.yom.exchangeapp.toast
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linkedin: Element

        val aboutPage: View = AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.icon128)
                .setDescription("Yasal Uyarı\nUygulama üzerindeki kur verileri anlık olarak yenilenmektedir.Güncel değişimlerin ugulamaya yansıması için sayfayı refresh etmeniz ya da sayfalar arası geçiş yapmanız gerekmektedir.Sadece burada yer alan bilgilere dayanarak yatırım kararları verilmemesi gerekmektedir.Bu uygulama sadece döviz kurlarını takip etmek için vardır.Bu yüzden uygulama üzerinden döviz alım satımı bulunmamaktadır.Uygulama üzerindeki veriler zaman zaman hatalı olabilir.Bu yüzden buradaki verilere dayanarak yapılan yatırımlardaki doğrudan veya dolaylı zararlardan uygulama sahipleri sorumlu tutulamaz.")
                .addItem(Element("Version 1.0", R.mipmap.ic_launcher))
                .addGroup("Connect with me")
                .addEmail("y.donmez.3237@gmail.com")
                .addGitHub("revios")
                .addItem(createCopyright())
                .create()
        setContentView(aboutPage)
    }

    private fun createCopyright(): Element? {
        val copyright = Element()
        val copyrigthtString = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String.format("Copyright %d by Genesis", Calendar.getInstance().get(Calendar.YEAR))
        } else {
            String.format("Copyright 2018 by Genesis")
        }
        copyright.title = copyrigthtString
        copyright.iconDrawable = R.mipmap.ic_launcher
        copyright.gravity = Gravity.CENTER
        copyright.onClickListener = View.OnClickListener { copyrigthtString toast (this@AboutActivity) }
        return copyright
    }

}
