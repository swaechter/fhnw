//File: mod_hello.c
#include <linux/module.h>

 /* Needed by all modules */
MODULE_LICENSE("GPL");
MODULE_AUTHOR("Darth Vader <anakin.skywalker@orbit.com>");
MODULE_DESCRIPTION("The ultimative linux kernel module");

static int __init hello_start(void)
{
    printk("mod_hello: Loading hello module...\n");
    printk("mod_hello: Hello universe!\n");
    return 0;
}

static void __exit hello_end(void)
{
    printk("mod_hello: see you.\n");
}

module_init(hello_start);
module_exit(hello_end);
