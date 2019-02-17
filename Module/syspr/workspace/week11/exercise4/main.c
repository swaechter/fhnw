#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/wait.h>

#define MAX_MEMORY 10

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
        // Create the shared memory
        int shmid = shmget(key, MAX_MEMORY, IPC_CREAT | 0666);
        if (shmid == -1) {
            perror("Parent is unable to create the shared memory page");
            return EXIT_FAILURE;
        }

        // Attach the shared memory and cast it
        char *memory = (char *) shmat(shmid, 0, 0);
        if (memory == (char *) -1) {
            perror("Parent is unable to attach the shared memory");
            return EXIT_FAILURE;
        }

        // Disable read access
        memory[0] = 0x0;

        // Write data after the read guard
        for (int i = 1; i < MAX_MEMORY; i++) {
            memory[i] = (char) ('A' + i);
        }

        // Simulate some heavy load and enable read access
        sleep(5);
        memory[0] = 0x1;

        // Detach the shared memory
        if (shmdt(memory) == -1) {
            perror("Parent is unable to detach the shared memory");
            return EXIT_FAILURE;
        }

        printf("Shared memory created\n");

        // Wait for all children (Otherwise we kill it before the child can even execute the read process)
        wait(NULL);
    } else { // Child
        // Wait for the shared memory page
        sleep(1);

        // Get the shared memory
        int shmid = shmget(key, MAX_MEMORY, 0666);
        if (shmid == -1) {
            perror("Child is unable to get the shared memory page");
            return EXIT_FAILURE;
        }

        // Attach the shared memory
        char *memory = (char *) shmat(shmid, 0, 0);
        if (memory == (char *) -1) {
            perror("Child is unable to attach the shared memory");
            return EXIT_FAILURE;
        }

        // Wait until read access is available
        while (memory[0] == 0x0) {
            sleep(1);
        }

        // Read data
        for (int i = 0; i < MAX_MEMORY; i++) {
            printf("Value: %c\n", memory[i]);
        }

        // Detach the shared memory
        if (shmdt(memory) == -1) {
            perror("Child is unable to detach the shared memory");
            return EXIT_FAILURE;
        }

        // Delete the shared memory
        if (shmctl(shmid, IPC_RMID, 0) == -1) {
            perror("Child is unable to delete the shared memory");
            return EXIT_FAILURE;
        }

        printf("Data read\n");
    }

    return EXIT_SUCCESS;
}
