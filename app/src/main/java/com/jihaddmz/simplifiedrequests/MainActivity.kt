package com.jihaddmz.simplifiedrequests

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
        SimplifiedRequests.setUpApi("http://192.168.0.175:8080", headers = hashMapOf("Authorization" to "Bearer in main activity"))
//        SimplifiedRequests.setUpApi("https://jsonplaceholder.typicode.com", hashMapOf("Authorization" to "Bearer 8484889484948943"))

//        fetchPersons()
//        fetchPerson()
        addPerson()
//        updatePerson()
//        deletePerson()

    }

    private fun deletePerson() {
        lifecycleScope.launch(Dispatchers.IO) {
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
    }

    private fun updatePerson() {
        lifecycleScope.launch(Dispatchers.IO) {
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
    }


    private fun addPerson() {
        lifecycleScope.launch(Dispatchers.IO) {
            SimplifiedRequests.callPost<SimpleMessageDTO>(
                "persons",
                PersonDTO("Jihadmz", "Mahfouz"),
                mapOf("Authorization" to "this is  the new one"),
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
    }

    private fun fetchPerson() {
        lifecycleScope.launch(Dispatchers.IO) {
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
    }

    private fun fetchPersons() {
        lifecycleScope.launch(Dispatchers.IO) {
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
}