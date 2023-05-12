public class MessageQueueTest {
    public static void main(String[] args) throws InterruptedException {

    MessageQueue<String> messageQueue = new MessageQueue<>();

        Thread publisher = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                String message = "Sending message num: " + i;
                System.out.println("Sending publisher: [{" + message + "}]");
                messageQueue.publish(message);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread worker1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    String message = messageQueue.receive();
                    System.out.println("Received worker1: [{" + message + "}]");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread worker2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    String message = messageQueue.receive();
                    System.out.println("Received worker2: [{" + message + "}]");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        publisher.start();
        worker1.start();
        worker2.start();

        publisher.join();
        worker1.join();
        worker2.join();

    }
}
