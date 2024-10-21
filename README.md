# Simplified Requests

Simplified Requests is an Android library for dealing with api requests using retrofit. It speeds the process of setting up the api and start calling http requests.

## Installation

1. In settings.gradle.kts file, add the jetpack repository
```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}
```

2. Add these two dependencies in the app build.gradle.kts file
```kotlin
implementation("com.google.code.gson:gson:Tag") // to have access to Expose SerializedName when defining DTO classes
implementation("com.github.jihaddmz:simplified-requests:Tag")
```
## Usage

See detailed usage about each api call in the app module.

```kotlin
SimplifiedRequests.setUpApi(
            "http://192.168.0.175:8080",
            headers = hashMapOf("Authorization" to "Bearer sh_8u458345834jfjdjfjdsfn")
)

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
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

## License

[MIT](https://choosealicense.com/licenses/mit/)
