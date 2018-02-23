package com.thejuki.kformmaster.observable

/**
 * Created by jivie on 2/22/16.
 */
abstract class ObservablePropertyBase<T>() : MutableObservableProperty<T> {

    @Transient
    private val list = ArrayList<(T) -> Unit>()
    override val size: Int get() = list.size
    override fun contains(element: (T) -> Unit): Boolean = list.contains(element)
    override fun containsAll(elements: Collection<(T) -> Unit>): Boolean = list.containsAll(elements)
    override fun isEmpty(): Boolean = list.isEmpty()
    override fun clear() = list.clear()
    override fun iterator(): MutableIterator<(T) -> Unit> = list.iterator()
    override fun remove(element: (T) -> Unit): Boolean = list.remove(element)
    override fun removeAll(elements: Collection<(T) -> Unit>): Boolean = list.removeAll(elements)
    override fun retainAll(elements: Collection<(T) -> Unit>): Boolean = list.retainAll(elements)
    override fun add(element: (T) -> Unit): Boolean = list.add(element)
    override fun addAll(elements: Collection<(T) -> Unit>): Boolean = list.addAll(elements)

    fun update(value: T) {
        for (listener in list) {
            listener(value)
        }
    }
}