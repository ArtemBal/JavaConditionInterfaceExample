import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageQueue<T> {
    private Queue<T> queue = new LinkedList<>();
    private Lock lock = new ReentrantLock();
    private Condition hasMessages = lock.newCondition();

    public void publish(T message) {
        lock.lock();
        try {
            queue.offer(message);
            hasMessages.signal();
        } finally {
            lock.unlock();
        }
    }

    public T receive() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                hasMessages.await();
            }
            return queue.poll();
        } finally {
            lock.unlock();
        }
    }

}