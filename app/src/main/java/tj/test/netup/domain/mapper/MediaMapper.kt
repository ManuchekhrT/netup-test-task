package tj.test.netup.domain.mapper

import tj.test.netup.data.model.MediaDto
import tj.test.netup.data.model.MediaEntity
import tj.test.netup.presentation.MediaUI

class MediaMapper : Mapper<MediaUI, MediaDto, MediaEntity>() {

    override fun dtoToDomain(dto: MediaDto) = with(dto) {
        MediaUI(
            name = this.name,
            url = this.url
        )
    }

    override fun entityToDomain(entity: MediaEntity): MediaUI {
        return MediaUI(
            name = entity.name,
            url = entity.url
        )
    }

    override fun domainToDto(domain: MediaUI): MediaDto {
        TODO("Not yet implemented")
    }
}