package uz.learn.mapstruct.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import uz.learn.mapstruct.dto.BookDTO
import uz.learn.mapstruct.model.Book


@Mapper(componentModel = "spring")
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