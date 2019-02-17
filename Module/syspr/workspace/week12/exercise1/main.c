#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/wait.h>

#define MESSAGE_TYPE 1

#define MESSAGE_LENGTH 100

struct message_buffer {
    long type;
    char text[MESSAGE_LENGTH];
};

typedef struct message_buffer message_buffer_t;

int main(void) {
    // Create the key
    key_t key = ftok("/bin/ls", 100);
    if (key == -1) {
        perror("Unable to create the key");
        return EXIT_FAILURE;
    }

    // Fork the application because I am lazy and don't want to run two separate applications
    int pid = fork();
    if (pid == -1) {
        perror("Unable to fork the application");
        return EXIT_FAILURE;
    }
    else if (pid > 0) { // Parent
        // Create the message queue
        int msgid = msgget(key, IPC_CREAT | 0666);
        if (msgid == -1) {
            perror("Parent is unable to create the message queue");
            return EXIT_FAILURE;
        }

        message_buffer_t message;

        // Read a message
        if (msgrcv(msgid, &message, sizeof(message.text), MESSAGE_TYPE, 0) == -1) {
            perror("Parent is unable to read the message");
            return EXIT_FAILURE;
        }
        printf("Parent received a message: %s\n", message.text);

        // Reply to a message
        message.type = MESSAGE_TYPE;
        sprintf(message.text, "%s", "A hello back from the server!");
        if (msgsnd(msgid, &message, sizeof(message.text), 0) == -1) {
            perror("Parent is unable to reply to the message");
            return EXIT_FAILURE;
        }

        // Delete the message queue
        if (msgctl(msgid, IPC_RMID, 0) == -1) {
            perror("Parent is unable to delete the message queue");
            return EXIT_FAILURE;
        }

        printf("Data read and replied\n");

        // Wait for all children (Otherwise we kill it before the child can even execute the read process)
        wait(NULL);
    } else { // Child
        // Wait for the message queue
        sleep(2);

        // Get the message queue
        int msgid = msgget(key, 0666);
        if (msgid == -1) {
            perror("Child is unable to get the message queue");
            return EXIT_FAILURE;
        }

        message_buffer_t message;

        // Send a message
        message.type = MESSAGE_TYPE;
        sprintf(message.text, "%s", "Hello from the child!");
        if (msgsnd(msgid, &message, sizeof(message.text), 0) == -1) {
            perror("Child is unable to send the message");
            return EXIT_FAILURE;
        }

        // Read the reply
        if (msgrcv(msgid, &message, sizeof(message.text), MESSAGE_TYPE, 0) == -1) {
            perror("Child is unable to read the reply from the server");
            return EXIT_FAILURE;
        }

        printf("Child send data and received answer: %s\n", message.text);
    }

    return EXIT_SUCCESS;
}
