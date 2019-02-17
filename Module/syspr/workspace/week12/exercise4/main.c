#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <unistd.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/sem.h>
#include <sys/wait.h>

#define DATA_SIZE 10

int lock_section(int semid, struct sembuf *semaphore) {
    semaphore->sem_op = -1;
    return semop(semid, semaphore, 1);
}

int unlock_section(int semid, struct sembuf *semaphore) {
    semaphore->sem_op = 1;
    return semop(semid, semaphore, 1);
}

void prepare_unsorted_data(int *data) {
    int i = 0;
    int fakedata[DATA_SIZE] = {1, 4, 2, 7, 9, 5, 8, 3, 0, 6};
    for (i = 0; i < DATA_SIZE; i++) {
        data[i] = fakedata[i];
    }
}

void sort_data_array(int semid, struct sembuf *semaphore, int *data) {
    int i = 0, j = 0, n = DATA_SIZE;
    for (i = 0; i < (n - 1); i++) {
        for (j = 0; j < n - i - 1; j++) {
            if (data[j] > data[j + 1]) {
                lock_section(semid, semaphore);
                int swap = data[j];
                data[j] = data[j + 1];
                data[j + 1] = swap;
                unlock_section(semid, semaphore);
                sleep(1);
            }
        }
    }
}

bool is_data_array_sorted(int *data) {
    int i = 0, previous = 0;
    for (i = 0; i < DATA_SIZE; i++) {
        if (i != 0 && data[i] <= previous) {
            return false;
        }
        previous = data[i];
    }
    return true;
}

void print_data_array(int semid, struct sembuf *semaphore, char *message, int *data) {
    lock_section(semid, semaphore);
    int i = 0;
    printf("%s: ", message);
    for (i = 0; i < DATA_SIZE; i++) {
        printf("%d ", data[i]);
    }
    printf("\n");
    unlock_section(semid, semaphore);
}

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
        if (lock_section(semid, &semaphore) == -1) {
            perror("Parent is unable to enter the critical section");
            return EXIT_FAILURE;
        }
        printf("Parent just entered the critical section\n");

        // Create the shared memory
        int shmid = shmget(key, DATA_SIZE, IPC_CREAT | 0666);
        if (shmid == -1) {
            perror("Parent is unable to create the shared memory page");
            return EXIT_FAILURE;
        }

        // Attach the shared memory and cast it
        int *memory = (int *) shmat(shmid, 0, 0);
        if (memory == (int *) -1) {
            perror("Parent is unable to attach the shared memory");
            return EXIT_FAILURE;
        }

        // Write the unsorted data array
        prepare_unsorted_data(memory);

        // Leave the critical section
        printf("Parent is going to leave the critical section\n");
        if (unlock_section(semid, &semaphore) == -1) {
            perror("Parent is unable to leave the critical section");
            return EXIT_FAILURE;
        }

        // Sort the array
        sort_data_array(semid, &semaphore, memory);

        // Detach the shared memory
        if (shmdt(memory) == -1) {
            perror("Parent is unable to detach the shared memory");
            return EXIT_FAILURE;
        }

        // Wait for all children (Otherwise we kill it before the child can even execute the read process)
        wait(NULL);

        // Delete the semaphore
        if (semctl(semid, 0, IPC_RMID, 0) == -1) {
            perror("Parent is unable to delete the semaphore");
            return EXIT_FAILURE;
        }

        // Delete the shared memory
        if (shmctl(shmid, IPC_RMID, 0) == -1) {
            perror("Parent is unable to delete the shared memory");
            return EXIT_FAILURE;
        }

        printf("Parent semaphore work finished\n");
    } else { // Child
        // Wait for the semaphore
        sleep(1);

        // Get the shared memory
        int shmid = shmget(key, DATA_SIZE, 0666);
        if (shmid == -1) {
            perror("Child is unable to get the shared memory page");
            return EXIT_FAILURE;
        }

        // Attach the shared memory
        int *memory = (int *) shmat(shmid, 0, 0);
        if (memory == (int *) -1) {
            perror("Child is unable to attach the shared memory");
            return EXIT_FAILURE;
        }

        // Read and print the data
        while (!is_data_array_sorted(memory)) {
            print_data_array(semid, &semaphore, "Child unsorted data", memory);
        }
        print_data_array(semid, &semaphore, "Child sorted data", memory);

        // Detach the shared memory
        if (shmdt(memory) == -1) {
            perror("Child is unable to detach the shared memory");
            return EXIT_FAILURE;
        }

        printf("Child semaphore work finished\n");
    }
    return EXIT_SUCCESS;
}
