package ua.`in`.khol.oleh.githobbit.di

object ApplicationInjector {
    private lateinit var applicationComponent: ApplicationComponent

    fun init() {
        applicationComponent = DaggerApplicationComponent.create()
    }

    fun get() = applicationComponent
}