package ua.`in`.khol.oleh.githobbit.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ua.`in`.khol.oleh.githobbit.domain.usecase.GetSubsImpl
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val getSubs: GetSubsImpl) : ViewModel()
