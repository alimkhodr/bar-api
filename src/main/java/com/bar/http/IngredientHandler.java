package com.bar.http;

import com.bar.model.Drink;
import com.bar.model.Ingredient;
import com.bar.service.AssociationService;
import com.bar.service.CrudService;

public class IngredientHandler extends CrudHandler<Ingredient> {

    private final AssociationService<Drink, Ingredient> recipes;

    public IngredientHandler(CrudService<Ingredient> ingredients, AssociationService<Drink, Ingredient> recipes) {
        super(ingredients, Ingredient.class);
        this.recipes = recipes;
    }

    @Override
    protected Response nested(Request request, long id) {
        if (request.size() == 2 && request.segment(1).equals("drinks")) {
            return get(request, () -> recipes.findOwners(id));
        }
        return super.nested(request, id);
    }
}
