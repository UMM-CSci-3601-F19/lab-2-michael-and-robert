/**
 * Function to get all the users!
 */
function getAllTodos() {
  console.log("Getting all the users.");

  get("/api/todos", function (returned_json) {
    document.getElementById('jsonDump').innerHTML = returned_json;
  });
}

function getAllTodosByAge() {
  console.log("Getting all the users.");

  get("/api/todos?age=" + document.getElementById("age").value, function (returned_json) {
    document.getElementById('jsonDump').innerHTML = returned_json;
  });
}
