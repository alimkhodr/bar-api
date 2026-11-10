package com.bar.http;

import com.bar.model.Drink;
import com.bar.model.Ingredient;
import com.bar.service.AssociationService;
import com.bar.service.CrudService;

public class DrinkHandler extends CrudHandler<Drink> {

    private final AssociationService<Drink, Ingredient> recipes;

    public DrinkHandler(CrudService<Drink> drinks, AssociationService<Drink, Ingredient> recipes) {
        super(drinks, Drink.class);
        this.recipes = recipes;
    }

    @Override
    protected Response nested(Request request, long id) {
        if (request.segment(1).equals("ingredients")) {
            return association(request, id, recipes);
        }
        return super.nested(request, id);
    }
}
