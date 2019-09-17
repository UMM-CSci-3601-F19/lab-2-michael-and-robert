/**
 * Function to get all the users!
 */
function getAllTodos() {
  console.log("Getting all the todos.");

  get("/api/todo", function (returned_json) {
    document.getElementById('jsonDump').innerHTML = returned_json;
  });
}

function getAllTodosByOwner() {
  console.log("Getting all the todos by owner.");

  get("/api/todo?owner=" + document.getElementById("owner").value, function (returned_json) {
    document.getElementById('jsonDump').innerHTML = returned_json;
  });
}
function getAllTodosByCategory() {
  console.log("Getting all the todos by category.")
  get("/api/todo?category=" + document.getElementById("category").value, function (returned_json) {
    document.getElementById('jsonDump').innerHTML = returned_json;
  });
}
function getAllTodosByStatus() {
    console.log("Getting all the todos by status.")
    get("/api/todo?status=" +document.getElementById("status"), function (returned_json) {
      document.getElementById('jsonDump').innerHTML = returned_json;
    });
}
