package mx.edu.beecker.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class SessionInformation {
    public static String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    public static String getRole() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().get().getAuthority().split("_")[1];
    }
}
