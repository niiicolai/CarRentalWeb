package carrental.carrentalweb.parameter_resolvers;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import carrental.carrentalweb.services.DatabaseService;

/*
 * https://www.baeldung.com/junit-5-parameters
 */
public class DatabaseParameterResolver implements ParameterResolver {

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
        return new DatabaseService("jdbc:mysql://localhost/carrental_test", "carrental_test", "password");
    }
}