$("#newCategory").removeAttr("name");
$("#categoryButton").click(function() {
    $("#oldCategories, #categoryButton, #or, #categories").hide();
    $("#enter, #newCategory, #p").css("display", "inline-block");
    $("select").removeAttr("name");
    $("#newCategory").attr("value", "").attr("name", "category.name");
});
$("#newIngredient").click(function() {
    var ingredientCount = $(".ingredient-row").length;
    var div = '<div class="ingredient-row" id="ingredientNumber' + ingredientCount + '">';
        div = div + '<div class="prefix-20 grid-30">';
        div = div + '<p><input id="ingredients' + ingredientCount + '.name" name="ingredients[';
        div = div + ingredientCount + '].name"></p></div>';
        div = div + '<div class="grid-10 suffix-10">';
        div = div + '<p><input class="quantity" style="width: 214%" id="ingredients' + ingredientCount;
        div = div + '.quantity" name="ingredients[' + ingredientCount + '].quantity"></p></div>';
        div = div + '<div class="grid-30"><p><input style="width: 80%" id="ingredients';
        div = div + ingredientCount + '.condition" name="ingredients[';
        div = div + ingredientCount + '].condition"></p></div></div>';
    $("#newIngredientButton").before(div);
});
$("#newStep").click(function() {
    var stepCount = $(".step-row").length;
    var div = '<div class="step-row"><div class="prefix-20 grid-80"><p><input style="margin-left: 147px"';
        div = div + ' id="instructions' + stepCount + '" name="instructions[' + stepCount +']"></p></div></div>';
    $("#newStepButton").before(div);
});
$("#saveRecipe").click(function() {
    var ingredientCount = $('.ingredient-row').length;
    for(var i = 0; i<ingredientCount; i++) {
        var $div = $('#ingredientNumber' + i);
        if(!$div.find(".quantity").val()) {
            $div.children().remove();
            $div.remove();
        }
    }
    document.getElementById("recipeForm").submit();
});
$("#signupButton").click(function(){
    var password1 = $("#firstPassword").val();
    var password2 = $("#secondPassword").val();
    if(password1 == password2) {
        document.getElementById("signupForm").submit();
    } else {
        alert("Passwords don't match! Try re-typing them.");
    }
});

$("#category-select").change(function(event) {
    var value = $(event.target).val();
    window.location = "/index?category=" + value;
});
