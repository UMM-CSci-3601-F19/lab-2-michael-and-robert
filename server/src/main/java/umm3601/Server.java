package umm3601;

import spark.Filter;
import spark.Request;
import spark.Response;
import umm3601.user.Database;
import umm3601.user.UserController;
import umm3601.todo.TodoDatabase;
import umm3601.todo.TodoController;

import java.io.IOException;

import static spark.Spark.*;

public class Server {

  public static final String CLIENT_DIRECTORY = "../client";
  public static final String USER_DATA_FILE = "src/main/data/users.json";
  public static final String TODO_DATA_FILE = "src/main/data/todos.json";
  private static Database userDatabase;
  private static TodoDatabase todoDatabase;

  public static void main(String[] args) {

    // Initialize dependencies
    UserController userController = buildUserController();
    TodoController todoController = buildTodoController();

    // Configure Spark
    port(4567);

    // Specify where client assets are stored
    // (all client-side HTML, CSS, JS, images, etc)
    staticFiles.externalLocation(CLIENT_DIRECTORY);

    // Simple example route
    get("/hello", (req, res) -> "Hello World");

    // Redirects to create simpler URLs
    redirect.get("/about", "/about.html");
    redirect.get("/users", "/users.html");
    redirect.get("/todo", "/todo.html");

    // API endpoints

    // Get specific user
    get("api/users/:id", userController::getUser);
    // List users, filtered using query parameters
    get("api/users", userController::getUsers);

    get("api/todo/:id", todoController::getTodo);
    get("api/todo", todoController::getTodos);

    // An example of throwing an unhandled exception so you can see how the
    // Java Spark debugger displays errors like this.
    get("api/error", (req, res) -> {
      throw new RuntimeException("A demonstration error");
    });

    // Called after each request to insert the GZIP header into the response.
    // This causes the response to be compressed _if_ the client specified
    // in their request that they can accept compressed responses.
    // There's a similar "before" method that can be used to modify requests
    // before they they're processed by things like `get`.
    after("*", addGzipHeader);
  }

  /***
   * Create a database using the json fie, use it as
   * data source for a new UserController
   *
   * Constructing the controller might throw an IOException if
   * there are problems reading from the JSON "database" file.
   * If that happens we'll print out an error message and shut
   * the server down.
   * @throws IOException if we can't open or read the user data file
   */
  private static UserController buildUserController() {
    UserController userController = null;

    try {
      userDatabase = new Database(USER_DATA_FILE);
      userController = new UserController(userDatabase);
    } catch (IOException e) {
      System.err.println("The server failed to load the user data; shutting down.");
      e.printStackTrace(System.err);

      // Shut the server down
      stop();
      System.exit(1);
    }

    return userController;
  }

  private static TodoController buildTodoController() {
    TodoController todoController = null;

    try {
      todoDatabase = new TodoDatabase(TODO_DATA_FILE);
      todoController = new TodoController(todoDatabase);
    } catch (IOException e) {
      System.err.println("The server failed to load the todo data; shutting down.");
      e.printStackTrace(System.err);

      // Shut the server down
      stop();
      System.exit(1);
    }

    return todoController;
  }

  // Enable GZIP for all responses
  private static Filter addGzipHeader = (Request request, Response response) -> {
    response.header("Content-Encoding", "gzip");
  };

}
