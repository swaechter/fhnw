#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/sem.h>
#include <sys/wait.h>

#define MAX_MEMORY 10

int main(void) {
    // Create the key
    key_t key = ftok("/bin/ls", 100);
    if (key == -1) {
        perror("Unable to create the key");
        return EXIT_FAILURE;
    }

    // Create a semaphore
    int semid = semget(key, 1, IPC_CREAT | IPC_EXCL | 0666);
    if (semid == -1) {
        perror("Unable to create the semaphore");
        return EXIT_FAILURE;
    }

    // Set the semaphore counter
    if (semctl(semid, 0, SETVAL, 1) == -1) {
        perror("Unable to set the semaphore count");
        return EXIT_FAILURE;
    }

    // Create the semaphore
    struct sembuf semaphore;
    semaphore.sem_num = 0;
    semaphore.sem_flg = 0;

    // Fork the application because I am lazy and don't want to run two separate applications
    int pid = fork();
    if (pid == -1) {
        perror("Unable to fork the application");
        return EXIT_FAILURE;
    }
    else if (pid > 0) { // Parent
        // Enter the critical section
        semaphore.sem_op = -1;
        if (semop(semid, &semaphore, 1) == -1) {
            perror("Parent is unable to enter the critical section");
            return EXIT_FAILURE;
        }
        printf("Parent just entered the critical section\n");

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

        // Write data
        for (int i = 0; i < MAX_MEMORY; i++) {
            memory[i] = (char) ('A' + i);
        }

        // Detach the shared memory
        if (shmdt(memory) == -1) {
            perror("Parent is unable to detach the shared memory");
            return EXIT_FAILURE;
        }

        // Leave the critical section
        printf("Parent is going to leave the critical section\n");
        semaphore.sem_op = 1;
        if (semop(semid, &semaphore, 1) == -1) {
            perror("Parent is unable to leave the critical section");
            return EXIT_FAILURE;
        }

        // Wait for all children (Otherwise we kill it before the child can even execute the read process)
        wait(NULL);

        // Delete the semaphore
        if (semctl(semid, 0, IPC_RMID, 0) == -1) {
            perror("Parent is unable to delete the semaphore");
            return EXIT_FAILURE;
        }

        printf("Parent semaphore work finished\n");
    } else { // Child
        // Wait for the semaphore
        sleep(1);

        // Enter the critical section
        semaphore.sem_op = -1;
        if (semop(semid, &semaphore, 1) == -1) {
            perror("Child is unable to enter the critical section");
            return EXIT_FAILURE;
        }
        printf("Child just entered the critical section\n");

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

        // Leave the critical section
        printf("Child is going to leave the critical section\n");
        semaphore.sem_op = 1;
        if (semop(semid, &semaphore, 1) == -1) {
            perror("Child is unable to leave the critical section");
            return EXIT_FAILURE;
        }

        printf("Child semaphore work finished\n");
    }
    return EXIT_SUCCESS;
}
