package ch.fhnw.vesys.serverservlet;

import ch.fhnw.vesys.shared.api.BankDriver;
import ch.fhnw.vesys.shared.core.Task;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class RequestHandler extends HttpServlet {

    private final BankDriver bankdriver;

    RequestHandler(BankDriver bankdriver) {
        this.bankdriver = bankdriver;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletOutputStream outputstream = response.getOutputStream();
        outputstream.write("Please use the servlet sender implementation and POST HTTP method to access this servlet!".getBytes());
        outputstream.flush();
        outputstream.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (ObjectInputStream inputstream = new ObjectInputStream(request.getInputStream()); ObjectOutputStream outputstream = new ObjectOutputStream(response.getOutputStream())) {
            Task task = (Task) inputstream.readObject();
            task.executeHandledTask(bankdriver);
            outputstream.writeObject(task);
            response.getOutputStream().close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
