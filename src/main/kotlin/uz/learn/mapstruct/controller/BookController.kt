package uz.learn.mapstruct.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import uz.learn.mapstruct.dto.BookDTO
import uz.learn.mapstruct.dto.response.Response
import uz.learn.mapstruct.service.BookService

@RestController
@RequestMapping("/api/book")
class BookController(private val service: BookService) {

    @PostMapping
    fun create(@RequestBody dto:BookDTO):ResponseEntity<Response<BookDTO>>{
        return service.create(dto)
    }

}