package de.coronavirus.imis.api;

import java.util.Map;
import java.util.stream.Collectors;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/permissions")
public class PermissionQueryController {

  @Autowired
  private WebInvocationPrivilegeEvaluator permissionEval;

  @PostMapping
  public Map<String, Boolean> queryPermissions(@RequestBody Map<String, ApiFunctionSpec> items) {
    return items.entrySet().stream().collect(Collectors.toMap(
      entry -> entry.getKey(),
      entry -> {
        var val = entry.getValue();
        return checkAllowed(val.getMethod(), val.getPath());
      }
    ));
  }

  private boolean checkAllowed(String method, String path) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return permissionEval.isAllowed(null, path, method, auth);
  }

  @Data
  public static class ApiFunctionSpec {
    private String method;
    private String path;
  }
}
