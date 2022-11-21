# MapStruct + Gradle + Kotlin + Spring Boot

[Source](https://mapstruct.org/documentation/installation/)

---

## _1. Initialization_

---

___At first we need to add [kapt](https://kotlinlang.org/docs/reference/kapt.html) to plugins section and  [MapStruct processor](https://mvnrepository.com/artifact/org.mapstruct/mapstruct-processor) to dependencies section of build.gradle.kts file___

___`mapstruct-processor` is required to generate the mapper implementation during build-time, while `kapt` is the Kotlin Annotation Processing Tool, and it is used to reference the generated code from Kotlin___
```groovy
plugins {
    kotlin("kapt") version "1.3.72"
}

dependencies {
    kapt("org.mapstruct:mapstruct-processor:1.5.3.Final")
}

```

```groovy
dependencies {
    implementation("org.mapstruct:mapstruct:1.5.3.Final")
}
```

----

## _2. Preparing necessary classes_

---

- ___So, Now we need to create data classes for working with data directly___

```kotlin
// MODELS

data class Book( // Main class which we want to use
    val id: Int,
    val name: String,
    val author: Author
)

data class Author( // Nested class
    val id:Int,
    val name:String
)

data class BookDTO( // DTO class for transfer data
    val id:Int,
    val name:String,
    val authorName:String
)
```

---

- ___Class for working with responses___
```kotlin
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Response<T>(
    val success: Boolean = true,
    val data: T? = null,
    val message: String? = null
) {

    constructor(data: T) : this(true, data)
    constructor(message: String) : this(success = false, message = message)

}
```

---

- __Mapper__

___`unmappedTargetPolicy = ReportingPolicy.IGNORE` makes build successfully in spite of fact that some fileds of target class are remaining without value___ 

```kotlin
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface BookMapper {


    @Mappings(
        Mapping(source = "authorName", target = "author.name")
    )
    fun bookDTOToBook(bookDTO: BookDTO):Book

    @Mappings(
        Mapping(source = "author.name", target = "authorName")
    )
    fun bookToBookDTO(book: Book):BookDTO
}
```

---

- ___Service class___

```kotlin

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class BookService(
    private val mapper: BookMapper
) {
    fun create(dto: BookDTO): ResponseEntity<Response<BookDTO>> {
        val book = mapper.bookDTOToBook(dto)

        val bookDTO = mapper.bookToBookDTO(book)

        return ResponseEntity
            .ok(Response(bookDTO))
    }
}
```

---

- ___Controller class___

```kotlin
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/book")
class BookController(private val service: BookService) {

    @PostMapping
    fun create(@RequestBody dto:BookDTO):ResponseEntity<Response<BookDTO>>{
        return service.create(dto)
    }

}
```
