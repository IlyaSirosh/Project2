var server = "http://localhost:8080/";
var app = "";

var prefix = server+app;

// Ingredient type

var addIngredientType = function(){

   var name= $('#ingredient_type.add#input').getPropertyValue();

    isIngredientTypeNameExists(name);
}




var isIngredientTypeNameExists = function(name){

  $.ajax({
      type: 'GET',
      url: prefix + "/ingredient/type/" + name + "/exists",
      async: true,
      success: function (result) {
          alert(result);
      },
      error: function (jqXHR, textStatus, errorThrown) {

          alert(jqXHR.status + ' ' + jqXHR.responseText);

      }
  })

}
