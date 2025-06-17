package com.handleGod.filter;

import com.handleGod.util.FilterInterface;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class IpBlockFilter extends OncePerRequestFilter implements FilterInterface {

    private final List<String> ipAddressList = new ArrayList<>();
    private boolean enabled = false;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        if (!enabled) {
            filterChain.doFilter(request, response);
            return;
        }

        String address = request.getRemoteAddr();
        if (ipAddressList.contains(address)) {
            response.addHeader("Unauthorized Request Of Ip Address", address);
            response.setStatus(HttpStatus.FORBIDDEN.value());
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void reset() {
        ipAddressList.clear();
    }

    @Override
    public void toggle() {
        enabled = !this.enabled;
    }

    @Override
    public boolean isFilterEnabled() {
        return enabled;
    }
}
