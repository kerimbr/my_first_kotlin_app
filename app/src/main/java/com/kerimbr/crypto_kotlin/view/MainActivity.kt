package com.kerimbr.crypto_kotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kerimbr.crypto_kotlin.R
import com.kerimbr.crypto_kotlin.adapter.CryptoListViewAdapter
import com.kerimbr.crypto_kotlin.databinding.ActivityMainBinding
import com.kerimbr.crypto_kotlin.model.CryptoModel
import com.kerimbr.crypto_kotlin.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.*
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val baseUrl: String = "https://raw.githubusercontent.com/"
    private var cryptoList: ArrayList<CryptoModel>? = null

    private var cryptoListViewAdapter : CryptoListViewAdapter? = null


    // Disposable
    private var compositeDisposable : CompositeDisposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init compositeDisposable
        compositeDisposable = CompositeDisposable()

        // Recycler View
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        recView.layoutManager = layoutManager

        // Load Data
        getData()
    }


    private fun getData(): Unit {

        // Retrofit Instance
        val retrofit : Retrofit = Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        // create service
        val service = retrofit.create(CryptoAPI::class.java)

        compositeDisposable?.add(
            service.getCryptoData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse)
        );



       /*
        val call = service.getCryptoData()

        call.enqueue(object : Callback<List<CryptoModel>>{
            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                println("---------------------------- On Response ---------------------------------")
                if (response.isSuccessful) {
                    response.body()?.let {
                        cryptoList = ArrayList(it)

                        cryptoList?.let {

                            for(cryptoModel : CryptoModel in  cryptoList!!) {
                                println(cryptoModel.currency)
                                println(cryptoModel.price)
                            }

                            cryptoListViewAdapter = CryptoListViewAdapter(cryptoList!!, object : CryptoListViewAdapter.Listener {
                                override fun onClick(cryptoModel: CryptoModel) {
                                    Toast.makeText(
                                        applicationContext,
                                        "${cryptoModel.currency} - ${cryptoModel.price}",
                                        Toast.LENGTH_LONG).show()
                                }
                            })
                            recView.adapter = cryptoListViewAdapter
                        }

                    }
                }else{
                    println("---------------------------- Response Error ---------------------------------")
                    println(response.code())

                }
            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })

*/
    }

    private fun handleResponse(list: List<CryptoModel>) {

        cryptoList = ArrayList(list)

        cryptoList?.let {

            for (cryptoModel: CryptoModel in cryptoList!!) {
                println(cryptoModel.currency)
                println(cryptoModel.price)
            }

            cryptoListViewAdapter =
                CryptoListViewAdapter(cryptoList!!, object : CryptoListViewAdapter.Listener {
                    override fun onClick(cryptoModel: CryptoModel) {
                        Toast.makeText(
                            applicationContext,
                            "${cryptoModel.currency} - ${cryptoModel.price}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            recView.adapter = cryptoListViewAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }

}