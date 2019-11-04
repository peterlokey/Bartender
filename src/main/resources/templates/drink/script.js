let button = document.getElementById("add_ingredient_form");
button.addEventListener("click", function(event){
    let form = document.querySelector("ingredients_wrapper");
    form.innerHTML += "<h1>ITS WORKING</h1>" + `<span>
<label th:for="measurement">Amount</label>
<input class="measurementInput" name="measurement3"/>
<label th:for="ingredient">Ingredient</label>
<select name="ingredient3">
    <option th:each="ingredient : ${ingredientList}"
            th:value = "${ingredient.id}"
            th:text = "${ingredient.name}">

    </option>
</select>
</span>`
});