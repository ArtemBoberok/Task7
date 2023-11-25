import java.util.concurrent.Semaphore
import kotlin.random.Random
class Philosopher(val id: Int, val leftFork: Semaphore, val rightFork: Semaphore) : Thread() {
    override fun run() {
        if (id % 2 == 0) {
            leftFork.acquire()
            rightFork.acquire()
        } else {
            rightFork.acquire()
            leftFork.acquire()
        }
        println("Филосов $id обедает")
        leftFork.release()
        rightFork.release()
        println("Филосов $id думает")
    }
}
fun main() {
    print("Введите кол-во философов: ")
    val numPhilosophers = readln().toInt()
    val forks = List(numPhilosophers) { Semaphore(1) }
    val philosophers = List(numPhilosophers) { id ->
        Philosopher(id, forks[id], forks[(id + 1) % numPhilosophers])
    }
    philosophers.forEach { it.start() }
    philosophers.forEach { it.join() }
}