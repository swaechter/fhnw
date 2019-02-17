#include <stdio.h>
#include <string.h>

struct address {
    char name[50];
    char street[100];
    short number;
    long zip;
    char town[50];
};

typedef struct address address_t;

void display_address(address_t *address) {
    printf("Name %s\n", address->name);
    printf("Street %s\n", address->street);
    printf("Number %d\n", address->number);
    printf("Zip %ld\n", address->zip);
    printf("Town %s\n", address->town);
}

int main(void) {
    // Define the address values
    char *name = "Simon Wächter";
    char *street = "Somewhere Street";
    char *town = "Basel";
    short number = 42;
    long zip = 4053;

    // Create an address struct via memcpy
    struct address address1;
    memcpy(address1.name, name, strlen(name));
    memcpy(address1.street, street, strlen(street));
    memcpy(address1.town, town, strlen(town));
    address1.name[strlen(name)] = '\0';
    address1.street[strlen(street)] = '\0';
    address1.town[strlen(town)] = '\0';
    address1.number = number;
    address1.zip = zip;

    // Create a typedef address via strcpy
    address_t address2;
    strcpy(address2.name, name);
    strcpy(address2.street, street);
    strcpy(address2.town, town);
    address2.number = number;
    address2.zip = zip;

    // Display the addresses
    display_address(&address1);
    display_address(&address2);

    // Create a text without a zero terminator
    char text[50] = {};
    char *p = text;
    for (int i = 0; i < sizeof(text); i++) {
        *p = '-';
        p++;
    }

    // Set the name and display the address. Let's hope for garbage in the output because there is no zero terminator that printf is able to use
    strcpy(address2.name, text);
    display_address(&address2);

    return 0;
}
