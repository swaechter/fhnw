#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

struct command {
    const char **argv;
};

int spawn_process(int in, int out, struct command *singlecommand) {
    pid_t pid;
    if ((pid = fork()) == 0) {
        if (in != 0) {
            dup2(in, 0);
            close(in);
        }
        if (out != 1) {
            dup2(out, 1);
            close(out);
        }
        return execvp(singlecommand->argv[0], (char *const *) singlecommand->argv);
    }
    return pid;
}

int fork_pipes(int n, struct command *commands) {
    int i, in = 0;
    int fd[2];

    for (i = 0; i < n - 1; ++i) {
        pipe(fd);
        spawn_process(in, fd[1], commands + i);
        close(fd[1]);
        in = fd[0];
    }

    if (in != 0) {
        dup2(in, 0);
    }

    return execvp(commands[i].argv[0], (char *const *) commands[i].argv);
}

int main(void) {
    const char *cat[] = {"cat", "/etc/passwd", 0};
    const char *grep[] = {"grep", "root", 0};
    struct command commands[] = {{cat},
                                 {grep}};
    return fork_pipes(2, commands);
}
