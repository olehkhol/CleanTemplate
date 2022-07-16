package ua.`in`.khol.oleh.cleantemplate.domain.usecase

import ua.`in`.khol.oleh.cleantemplate.domain.repository.contract.GitRepository
import javax.inject.Inject

class GetSubsUseCase @Inject constructor(private val repository: GitRepository)