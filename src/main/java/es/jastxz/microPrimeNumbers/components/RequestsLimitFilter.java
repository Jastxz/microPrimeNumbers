package es.jastxz.microPrimeNumbers.components;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import es.jastxz.microPrimeNumbers.model.GlobalRequest;
import es.jastxz.microPrimeNumbers.model.UserRequest;
import es.jastxz.microPrimeNumbers.repo.GlobalRequestRepo;
import es.jastxz.microPrimeNumbers.repo.UserRequestRepo;

import java.io.IOException;
import java.util.List;

@Component
public class RequestsLimitFilter implements Filter {

    private static final int MAX_REQUESTS_GLOBAL_PER_MINUTE = 10;
    private static final int MAX_REQUESTS_USER_PER_MINUTE = 2;
    private static final long TIME_WINDOW_MILLIS = 60_000; // 1 minuto

    private GlobalRequestRepo globalRequestRepo;
    private UserRequestRepo userRequestRepo;

    public RequestsLimitFilter(GlobalRequestRepo globalRequestRepo, UserRequestRepo userRequestRepo) {
        this.globalRequestRepo = globalRequestRepo;
        this.userRequestRepo = userRequestRepo;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String clientId = request.getRemoteAddr(); // Obtener IP del cliente
        
        // Inicializamos la entidad global
        List<GlobalRequest> globalList = globalRequestRepo.findAll();
        GlobalRequest globalLog = globalList.isEmpty() ? new GlobalRequest() : globalList.getFirst();
        
        // Inicializamos la entidad del usuario
        UserRequest userLog = userRequestRepo.findById(clientId).orElse(new UserRequest());

        // Inicializamos el tiempo local
        long currentTime = System.currentTimeMillis();

        // Revisamos el tiempo tanto para control global como de usuario
        boolean globalTimeExpired = globalLog.getLastRequestTimestamp() == 0 || currentTime - globalLog.getLastRequestTimestamp() > TIME_WINDOW_MILLIS;
        boolean userTimeExpired = userLog.getLastRequestTimestamp() == 0 || currentTime - userLog.getLastRequestTimestamp() > TIME_WINDOW_MILLIS;
        
        // Reiniciar contador si la ventana de tiempo ha expirado
        if (globalTimeExpired && userTimeExpired) {
            globalLog.setRequestCount(1);
            globalLog.setLastRequestTimestamp(currentTime);
            userLog.setRequestCount(1);
            userLog.setLastRequestTimestamp(currentTime);
        } else {
            // Incrementar contadores
            globalLog.setRequestCount(globalLog.getRequestCount() + 1);
            userLog.setRequestCount(userLog.getRequestCount() + 1);
        }

        boolean globalRequestReached = globalLog.getRequestCount() > MAX_REQUESTS_GLOBAL_PER_MINUTE;
        boolean userRequestReached = userLog.getRequestCount() > MAX_REQUESTS_USER_PER_MINUTE;
        if (globalRequestReached || userRequestReached) {
            ((HttpServletResponse) response).setStatus(429); // Too Many Requests
            response.getWriter().write("Rate limit exceeded. Try again later.");
            return;
        }

        // Guardar en la base de datos en memoria
        globalRequestRepo.save(globalLog);
        userRequestRepo.save(userLog);

        // Continuar con la petición
        chain.doFilter(request, response);
    }
}