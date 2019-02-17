#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

#define SERVER_ADDRESS "127.0.0.1"

#define SERVER_PORT 9000

#define SERVER_BACKLOG 20

#define MESSAGE_LENGTH 100

int create_client_socket(char *hostname, uint16_t port) {
    // Create the address
    struct sockaddr_in server_address;
    server_address.sin_family = AF_INET;
    server_address.sin_port = htons(port);
    server_address.sin_addr.s_addr = inet_addr(hostname);
    socklen_t server_address_length = sizeof(server_address);

    // Create the socket
    int socketid = socket(AF_INET, SOCK_STREAM, 0);

    // Connect the socket
    connect(socketid, (struct sockaddr *) &server_address, server_address_length);

    return socketid;
}

int create_server_socket(uint16_t port, int backlog) {
    // Create the address
    struct sockaddr_in server_address;
    server_address.sin_family = AF_INET;
    server_address.sin_addr.s_addr = htonl(INADDR_ANY);
    server_address.sin_port = htons(port);
    socklen_t server_address_length = sizeof(server_address);

    // Create the socket
    int socketid = socket(AF_INET, SOCK_STREAM, 0);

    // Bind the socket
    bind(socketid, (struct sockaddr *) &server_address, server_address_length);

    // Listen on the socket
    listen(socketid, backlog);

    return socketid;
}

int accept_client_socket(int socketid) {
    struct sockaddr_in client_address;
    socklen_t client_address_length = sizeof(client_address);
    return accept(socketid, (struct sockaddr *) &client_address, &client_address_length);
}

void read_client_ip(int socketid, char *buffer, socklen_t length) {
    struct sockaddr_in socket_address;
    socklen_t socket_address_length = sizeof(socket_address);
    getsockname(socketid, (struct sockaddr *) &socket_address, &socket_address_length);
    inet_ntop(AF_INET, &socket_address.sin_addr, buffer, length);
}

void close_socket(int socketid) {
    close(socketid);
}

void receive_socket_data(int socketid, char *buffer, size_t length) {
    recv(socketid, buffer, length, 0);
}

void send_socket_data(int socketid, char *buffer, size_t length) {
    send(socketid, buffer, length, 0);
}

int main(void) {
    // Fork the application because I am lazy and don't want to run two separate applications
    int pid = fork();
    if (pid == -1) {
        perror("Unable to fork the process");
        return EXIT_FAILURE;
    } else if (pid > 0) { // Parent
        // Create the server socket
        int serversocketid = create_server_socket(SERVER_PORT, SERVER_BACKLOG);
        printf("Parent socket created\n");

        // Accept requests
        int clientsocketid = accept_client_socket(serversocketid);
        printf("Parent request accepted\n");

        // Read client address
        char clientip[INET_ADDRSTRLEN] = {0};
        read_client_ip(clientsocketid, clientip, sizeof(clientip));
        printf("Parent accepted %s\n", clientip);

        // Receive data
        char requestbuffer[MESSAGE_LENGTH] = {0};
        receive_socket_data(clientsocketid, requestbuffer, sizeof(requestbuffer));
        printf("Parent has received: %s\n", requestbuffer);

        // Send data
        char responsebuffer[MESSAGE_LENGTH] = {"A warm welcome from the server!"};
        send_socket_data(clientsocketid, responsebuffer, sizeof(responsebuffer));
        printf("Parent has responded: %s\n", responsebuffer);

        // Close the sockets
        close(clientsocketid);
        close(serversocketid);

        // Wait for all children (Otherwise we kill it before the child can even execute the read process)
        wait(NULL);

        printf("Parent socket served\n");
    } else { // Child
        // Wait for the server socket
        sleep(2);

        // Create the socket
        int serversocketid = create_client_socket(SERVER_ADDRESS, SERVER_PORT);

        // Send data
        char requestdata[MESSAGE_LENGTH] = {"Hello from the child!"};
        send_socket_data(serversocketid, requestdata, sizeof(requestdata));

        // Receive data
        char responsedata[MESSAGE_LENGTH] = {0};
        receive_socket_data(serversocketid, responsedata, sizeof(responsedata));
        printf("Child received data: %s\n", responsedata);

        // Close the socket
        close_socket(serversocketid);

        printf("Client request done\n");
    }
    return EXIT_SUCCESS;
}
