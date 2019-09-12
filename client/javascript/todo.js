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

  get("/api/todo?owner=" + document.getElementById("owner"), function (returned_json) {
    document.getElementById('jsonDump').innerHTML = returned_json;
  });
}
