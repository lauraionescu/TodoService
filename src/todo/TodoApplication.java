package todo;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import todo.resources.TodoResource;
import todo.health.TemplateHealthCheck;

public class TodoApplication extends Application<TodoConfiguration> {
    public static void main(String[] args) throws Exception {
        new TodoApplication().run(args);
    }

    @Override
    public String getName() {
        return "todo";
    }

    @Override
    public void initialize(Bootstrap<TodoConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(TodoConfiguration configuration,
                    Environment environment) {
        final TodoResource resource = new TodoResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
        environment.jersey().register(resource);

        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(resource);
    }
}