package carrental.carrentalweb.parameter_resolvers;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.springframework.beans.factory.annotation.Value;

import carrental.carrentalweb.services.DatabaseService;

/*
 * https://www.baeldung.com/junit-5-parameters
 */
public class DatabaseParameterResolver implements ParameterResolver {

    private static String url = System.getenv("JDBCUrl_test");
    private static String username = System.getenv("JDBCUsername");
    private static String password = System.getenv("JDBCPassword");

    /*
     * Set the Database service class as a supported argument
     * that can be injected into test classes.
     */
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, 
      ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == DatabaseService.class;
    }
  
    /*
     * Creates the DatabaseService instance used to inject into test classes.
     */
    @Override
    public Object resolveParameter(ParameterContext parameterContext, 
      ExtensionContext extensionContext) throws ParameterResolutionException {
        return new DatabaseService(url, username, password);
    }
}