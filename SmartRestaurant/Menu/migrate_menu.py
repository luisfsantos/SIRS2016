from Menu.models import Meal, Ingredient
from django.core.files import File
# ingredient_list = ["pineapple", "chestnut", "bacon", "sesame-ginger salad dressing", "green onion", "kiwi",
#                    "golden apple", "raspberry", "strawberry", "white sugar", "brown sugar", "tortillas", "cinnamon sugar",
#                    "vegetable oil", "chicken breast", "red bell pepper", "corn", "black beans", "spinach", "jalapeno peppers",
#                    "parsley", "ground cumin", "chilly powder", "salt", "Monterey Jack cheese", "red wine vinegar",
#                    "lime", "ground black pepper", "garlic", "dried oregano", "tomato", "lettuce", "salsa", "spaghetti",
#                    "olive oil", "onion", "white wine", "eggs", "parmesan", "yeast", "flour", "tomato paste",
#                    "onion powder", "kosher salt","mozzarella", "mushrooms","milk chocolate","white chocolate", "shortening",
#                    "half-and-half cream","heavy cream","vanilla extract", "ground cinnamon", "graham crackers","butter",
#                    "cream cheese", "milk", "sour cream"]
#
# for ingredient in ingredient_list:
#     Ingredient.objects.create(name=ingredient)

#Meal.objects.create(meal_type=,name=, price=, description=,calories=,)
image = open('Menu/static_images/strawberry.jpg', 'rb')
django_file = File(image)

meal = Meal(meal_type="DE",name="Chocolate Covered Strawberries", price=6.99,
                    description="Eight fresh strawberries dipped in delicious milk chocolate (from Sweden) with white "
                                "chocolate drizzled finely over them!",calories=115)

meal.save()

meal.ingredients.add(Ingredient.objects.get(name="strawberry"))
meal.image.save("strawberry", django_file, save=True)
meal.save()