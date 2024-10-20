package com.jihaddmz.simplifiedrequests

import android.net.http.HttpException
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.jihaddmz.simplified_requests.SimplifiedRequests
import com.jihaddmz.simplifiedrequests.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        SimplifiedRequests.setUpApi(
            "http://192.168.0.175:8080",
            headers = hashMapOf("Authorization" to "Bearer sh_8u458345834jfjdjfjdsfn")
        )

        fetchPersons()
//        fetchPerson()
//        addPerson()
//        updatePerson()
//        deletePerson()

    }

    private fun deletePerson() {
        SimplifiedRequests.callDelete<SimpleMessageDTO>(
            "person/Jihad",
            onSuccess = {
                runOnUiThread {
                    binding.tv.text = it.message
                }
            },
            onFailed = {
                binding.tv.text = it.toString()
            }
        )
    }

    private fun updatePerson() {
        SimplifiedRequests.callPut<SimpleMessageDTO>(
            "person/Jihadmz",
            PersonDTO("Jihad", "Mahfouz"),
            onSuccess = {
                runOnUiThread {
                    binding.tv.text = it.toString()
                }
            },
            onFailed = {
                runOnUiThread {
                    binding.tv.text = it.toString()
                }
            }
        )
    }


    private fun addPerson() {
        SimplifiedRequests.callPost<SimpleMessageDTO>(
            "persons",
            PersonDTO("Jihadmz", "Mahfouz"),
            mapOf("Authorization" to "sh_kdsjfksdjfkdjfkjfksjfkjd"),
            onSuccess = {
                runOnUiThread {
                    binding.tv.text = it.toString()
                }
            },
            onFailed = {
                runOnUiThread {
                    binding.tv.text = it.toString()
                }
            }
        )
    }

    private fun fetchPerson() {
        SimplifiedRequests.callGet<FilterPersonDTO>(
            "person",
            hashMapOf("firstName" to "Jihadmz"),
            onSuccess = {
                runOnUiThread {
                    binding.tv.text = it.toString()
                }
            },
            onFailed = {
                runOnUiThread {
                    binding.tv.text = it.toString()
                }
            }
        )
    }

    private fun fetchPersons() {
        SimplifiedRequests.callGet<ArrayList<PersonDTO>>(
            "persons",
            null,
            onSuccess = {
                runOnUiThread {
                    binding.tv.text = it.toString()
                }
            },
            onFailed = {
                runOnUiThread {
                    binding.tv.text = it.message
                }
            })
    }
}