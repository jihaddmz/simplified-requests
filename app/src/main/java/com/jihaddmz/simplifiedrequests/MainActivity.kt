package com.jihaddmz.simplifiedrequests

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jihaddmz.simplified_requests.SimplifiedRequests
import com.jihaddmz.simplifiedrequests.databinding.ActivityMainBinding

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

//        fetchPersons()
//        fetchPerson()
//        addPerson()
//        updatePerson()
//        deletePerson()

    }

    private suspend fun deletePerson() {
        SimplifiedRequests.callDelete<SimpleMessageDTO>(
            "person/Jihad",
            onSuccess = {
                binding.tv.text = it.message
            },
            onFailed = {
                binding.tv.text = it.toString()
            }
        )
    }

    private suspend fun updatePerson() {
        SimplifiedRequests.callPut<SimpleMessageDTO>(
            "person/Jihadmz",
            PersonDTO("Jihad", "Mahfouz"),
            onSuccess = {
                binding.tv.text = it.toString()
            },
            onFailed = {
                binding.tv.text = it.toString()
            }
        )
    }


    private suspend fun addPerson() {
        SimplifiedRequests.callPost<SimpleMessageDTO>(
            "persons",
            null,
            PersonDTO("Jihad", "Mahfouz"),
            mapOf("Authorization" to "sh_kdsjfksdjfkdjfkjfksjfkjd"),
            onSuccess = {
                binding.tv.text = it.toString()
            },
            onFailed = {
                binding.tv.text = it.toString()
            }
        )
    }

    private suspend fun fetchPerson() {
        SimplifiedRequests.callGet<FilterPersonDTO>(
            "person",
            hashMapOf("firstName" to "Jihadmz"),
            onSuccess = {
                binding.tv.text = it.toString()
            },
            onFailed = {
                binding.tv.text = it.toString()
            }
        )
    }

    private suspend fun fetchPersons() {
        SimplifiedRequests.callGet<ArrayList<PersonDTO>>(
            "persons",
            null,
            onSuccess = {
                binding.tv.text = it.toString()
            },
            onFailed = {
                binding.tv.text = it
            })
    }
}