package uz.learn.mapstruct.service

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import uz.learn.mapstruct.dto.BookDTO
import uz.learn.mapstruct.dto.response.Response
import uz.learn.mapstruct.mapper.BookMapper

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
