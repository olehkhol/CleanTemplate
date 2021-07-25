package ua.`in`.khol.oleh.githobbit.domain.usecase

import ua.`in`.khol.oleh.githobbit.domain.repository.contract.GitRepository
import javax.inject.Inject

class GetSubsImpl @Inject constructor(private val repository: GitRepository)