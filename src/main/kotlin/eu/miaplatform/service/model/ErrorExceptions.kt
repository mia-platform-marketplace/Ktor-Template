package eu.miaplatform.service.model

data class BadRequestException(val code: Int, val errorMessage: String): Exception(errorMessage)
data class InternalServerErrorException(val code: Int, val errorMessage: String): Exception(errorMessage)
data class NotFoundException(val code: Int, val errorMessage: String): Exception(errorMessage)
data class UnauthorizedException(val code: Int, val errorMessage: String): Exception(errorMessage)