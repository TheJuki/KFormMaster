package com.thejuki.kformmaster.observable

open class StandardObservableProperty<T>(
        initValue: T
) : ObservablePropertyBase<T>() {

    override var value: T = initValue
        set(value) {
            field = value
            update(value)
        }
}
