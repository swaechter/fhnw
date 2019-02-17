package ch.swaechter.apmservlet;

import ch.swaechter.apmservlet.storages.local.LocalStorage;
import ch.swaechter.apmservlet.sdk.Storage;
import org.jtwig.web.servlet.JtwigRenderer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class ApmServlet extends HttpServlet {

    private JtwigRenderer renderer;

    private Storage storage;

    @Override
    public void init() {
        renderer = JtwigRenderer.defaultRenderer();
        storage = new LocalStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the ID
        Integer parameterId = getIdFromRequest(request, "id");

        // Replace the hostname
        request.setAttribute("hostname", request.getLocalAddr() + ":" + request.getServerPort());

        // Replace the ID and the text
        if (isIdPresent(request)) {
            request.setAttribute("id", parameterId != null ? parameterId.toString() : "Invalid");
            request.setAttribute("text", parameterId != null ? storage.load(parameterId) : "Invalid");
        } else {
            request.setAttribute("id", "");
            request.setAttribute("text", "");
        }

        // Forward the request to the template engine
        renderer.dispatcherFor("/WEB-INF/templates/index.twig.html").render(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer idParameter = getIdFromRequest(request, "newid");
        String textParameter = getTextFromRequest(request);

        // Check the ID
        if (idParameter != null) {
            storage.store(idParameter, textParameter);
            response.sendRedirect("/?id=" + idParameter);
        } else {
            response.sendRedirect("/");
        }
    }

    private boolean isIdPresent(HttpServletRequest request) {
        String valueString = request.getParameter("id");
        return valueString != null && !valueString.isEmpty();
    }

    private Integer getIdFromRequest(HttpServletRequest request, String key) {
        String idParameterString = request.getParameter(key);
        if (idParameterString != null && !idParameterString.isEmpty()) {
            Integer idParameter = Integer.parseInt(idParameterString);
            return idParameter >= 1000 && idParameter <= 65535 ? idParameter : null;
        } else {
            return null;
        }
    }

    private String getTextFromRequest(HttpServletRequest request) {
        String textParameterString = request.getParameter("text");
        return textParameterString != null && !textParameterString.isEmpty() ? textParameterString : null;
    }
}
