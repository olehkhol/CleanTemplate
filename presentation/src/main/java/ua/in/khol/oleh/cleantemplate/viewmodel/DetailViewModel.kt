package ua.`in`.khol.oleh.cleantemplate.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ua.`in`.khol.oleh.cleantemplate.domain.usecase.GetSubsUseCase
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val getSubs: GetSubsUseCase) : ViewModel()
