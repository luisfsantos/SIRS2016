from Menu.models import Meal, Ingredient
from django.core.files import File
ingredient_list = ["pineapple", "chestnut", "bacon", "sesame-ginger salad dressing", "green onion", "kiwi",
                   "golden apple", "raspberry", "strawberry", "white sugar", "brown sugar", "tortillas", "cinnamon sugar",
                   "vegetable oil", "chicken breast", "red bell pepper", "corn", "black beans", "spinach", "jalapeno peppers",
                   "parsley", "ground cumin", "chili powder", "salt", "Monterey Jack cheese", "red wine vinegar",
                   "lime", "ground black pepper", "black pepper", "garlic", "dried oregano", "tomato", "lettuce", "salsa", "spaghetti",
                   "olive oil", "onion", "white wine", "eggs", "Parmesan cheese", "yeast", "flour", "tomato paste",
                   "onion powder", "kosher salt","Mozzarella cheese", "green bell pepper", "mushrooms","milk chocolate","white chocolate", "shortening",
                   "half-and-half cream","heavy cream","vanilla extract", "ground cinnamon", "graham crackers","butter",
                   "cream cheese", "milk", "sour cream"]

for ingredient in ingredient_list:
    Ingredient.objects.create(name=ingredient)


meal = Meal(meal_type="AP",name="Easy Rumaki with Pineapple",
            price=4.99,
            description="This dish combines the crunchiness of bacon and water chestnuts"
                        " with the wonderful flavors of pineapple!",
            calories=190,
            image_url="https://luissantos.me/media/easy_rumaki_appetizer.jpg")
meal.save()
meal.ingredients.add(Ingredient.objects.get(name="pineapple"))
meal.ingredients.add(Ingredient.objects.get(name="chestnut"))
meal.ingredients.add(Ingredient.objects.get(name="bacon"))
meal.ingredients.add(Ingredient.objects.get(name="sesame-ginger salad dressing"))
meal.ingredients.add(Ingredient.objects.get(name="green onion"))
meal.save()


meal = Meal(meal_type="AP",name="Fruit Salsa and Cinnamon Chips",
            price=5.45,
            description="Tasty fruit salsa and cinnamon tortilla chips. Great as an appetizer "
                        "or a snack. Great for anytime!",
            calories=132,
            image_url="https://luissantos.me/media/fruit_salsa_appetizer.jpg")
meal.save()
meal.ingredients.add(Ingredient.objects.get(name="kiwi"))
meal.ingredients.add(Ingredient.objects.get(name="golden apple"))
meal.ingredients.add(Ingredient.objects.get(name="raspberry"))
meal.ingredients.add(Ingredient.objects.get(name="white sugar"))
meal.ingredients.add(Ingredient.objects.get(name="brown sugar"))
meal.ingredients.add(Ingredient.objects.get(name="tortillas"))
meal.ingredients.add(Ingredient.objects.get(name="cinnamon sugar"))
meal.save()

meal = Meal(meal_type="AP",name="Southwestern Egg Rolls",
            price=4.69,
            description="These aren't traditional egg rolls! Small flour tortillas are stuffed with an exciting blend "
                        "of Southwestern-style ingredients, then deep fried until golden brown.",
            calories=419,
            image_url="https://luissantos.me/media/egg_rolls_appetizer.jpg")
meal.save()
meal.ingredients.add(Ingredient.objects.get(name="vegetable oil"))
meal.ingredients.add(Ingredient.objects.get(name="chicken breast"))
meal.ingredients.add(Ingredient.objects.get(name="green onion"))
meal.ingredients.add(Ingredient.objects.get(name="red bell pepper"))
meal.ingredients.add(Ingredient.objects.get(name="corn"))
meal.ingredients.add(Ingredient.objects.get(name="black beans"))
meal.ingredients.add(Ingredient.objects.get(name="spinach"))
meal.ingredients.add(Ingredient.objects.get(name="jalapeno peppers"))
meal.ingredients.add(Ingredient.objects.get(name="parsley"))
meal.ingredients.add(Ingredient.objects.get(name="ground cumin"))
meal.ingredients.add(Ingredient.objects.get(name="chili powder"))
meal.ingredients.add(Ingredient.objects.get(name="salt"))
meal.ingredients.add(Ingredient.objects.get(name="Monterey Jack cheese"))
meal.ingredients.add(Ingredient.objects.get(name="tortillas"))
meal.save()


meal = Meal(meal_type="MC",name="Lime Chicken Soft Tacos", price=7.49,
                    description="Tortillas with sauteed chicken topped with lime, tomato, lettuce, cheese and salsa. "
                                "Flavorful but not overwhelming: it's perfect!",

            calories=204, image_url="https://luissantos.me/media/soft_tacos_main.jpg")
meal.save()
meal.ingredients.add(Ingredient.objects.get(name="chicken breast"))
meal.ingredients.add(Ingredient.objects.get(name="red wine vinegar"))
meal.ingredients.add(Ingredient.objects.get(name="lime"))
meal.ingredients.add(Ingredient.objects.get(name="white sugar"))
meal.ingredients.add(Ingredient.objects.get(name="salt"))
meal.ingredients.add(Ingredient.objects.get(name="ground black pepper"))
meal.ingredients.add(Ingredient.objects.get(name="onion"))
meal.ingredients.add(Ingredient.objects.get(name="garlic"))
meal.ingredients.add(Ingredient.objects.get(name="dried oregano"))
meal.ingredients.add(Ingredient.objects.get(name="tortillas"))
meal.ingredients.add(Ingredient.objects.get(name="tomato"))
meal.ingredients.add(Ingredient.objects.get(name="lettuce"))
meal.ingredients.add(Ingredient.objects.get(name="Monterey Jack cheese"))
meal.ingredients.add(Ingredient.objects.get(name="salsa"))
meal.save()

meal = Meal(meal_type="MC",name="Spaghetti Carbonara", price=6.29,
                    description="A super rich, classic 'bacon and egg' spaghetti dish. "
                                "This dish also makes an unusual brunch meal.",
            calories=240, image_url="https://luissantos.me/media/spaghetti_carbonara_main.jpg")
meal.save()
meal.ingredients.add(Ingredient.objects.get(name="spaghetti"))
meal.ingredients.add(Ingredient.objects.get(name="olive oil"))
meal.ingredients.add(Ingredient.objects.get(name="bacon"))
meal.ingredients.add(Ingredient.objects.get(name="onion"))
meal.ingredients.add(Ingredient.objects.get(name="garlic"))
meal.ingredients.add(Ingredient.objects.get(name="white wine"))
meal.ingredients.add(Ingredient.objects.get(name="eggs"))
meal.ingredients.add(Ingredient.objects.get(name="Parmesan cheese"))
meal.ingredients.add(Ingredient.objects.get(name="salt"))
meal.ingredients.add(Ingredient.objects.get(name="black pepper"))
meal.ingredients.add(Ingredient.objects.get(name="parsley"))
meal.save()

meal = Meal(meal_type="MC",name="Veggie Pizza", price=6.49,
                    description="Of course, you could just order out, but nothing beats a restaurant-made pizza. "
                                "The sauce is so easy and hearty everyone will think we were in the kitchen all day cooking this up!",
            calories=397,
            image_url="https://luissantos.me/media/veggie_pizza_main.jpg")
meal.save()
meal.ingredients.add(Ingredient.objects.get(name="yeast"))
meal.ingredients.add(Ingredient.objects.get(name="flour"))
meal.ingredients.add(Ingredient.objects.get(name="white sugar"))
meal.ingredients.add(Ingredient.objects.get(name="dried oregano"))
meal.ingredients.add(Ingredient.objects.get(name="garlic"))
meal.ingredients.add(Ingredient.objects.get(name="onion powder"))
meal.ingredients.add(Ingredient.objects.get(name="kosher salt"))
meal.ingredients.add(Ingredient.objects.get(name="black pepper"))
meal.ingredients.add(Ingredient.objects.get(name="Mozzarella cheese"))
meal.ingredients.add(Ingredient.objects.get(name="green bell pepper"))
meal.ingredients.add(Ingredient.objects.get(name="onion"))
meal.ingredients.add(Ingredient.objects.get(name="mushrooms"))
meal.save()


meal = Meal(meal_type="DE",name="Chocolate Covered Strawberries", price=5.49,
                    description="Eight fresh strawberries dipped in delicious milk chocolate (from Sweden) with white "
                                "chocolate drizzled finely over them!",
            calories=115,
            image_url="https://luissantos.me/media/chocolate_strawberry_dessert.jpg")
meal.save()
meal.ingredients.add(Ingredient.objects.get(name="strawberry"))
meal.ingredients.add(Ingredient.objects.get(name="milk chocolate"))
meal.ingredients.add(Ingredient.objects.get(name="white chocolate"))
meal.save()


meal = Meal(meal_type="DE",name="Cinnamon Ice Cream", price=5.89,
                    description="This is a delicious treat. Creamy, but not too sweet. "
                                "The cinnamon is not overpowering... You will love it!",
            calories=134,
            image_url="https://luissantos.me/media/cinnamon_icecream_dessert.jpg")
meal.save()
meal.ingredients.add(Ingredient.objects.get(name="white sugar"))
meal.ingredients.add(Ingredient.objects.get(name="half-and-half cream"))
meal.ingredients.add(Ingredient.objects.get(name="eggs"))
meal.ingredients.add(Ingredient.objects.get(name="heavy cream"))
meal.ingredients.add(Ingredient.objects.get(name="vanilla extract"))
meal.ingredients.add(Ingredient.objects.get(name="ground cinnamon"))
meal.save()

meal = Meal(meal_type="DE",name="New York Cheesecake", price=5.89,
                    description="The king of all cheesecakes! "
                                "This is the dessert you are looking for: thick and creamy! ",
            calories=116,
            image_url="https://luissantos.me/media/ny_cheesecake_dessert.jpg")
meal.save()
meal.ingredients.add(Ingredient.objects.get(name="raspberry"))
meal.ingredients.add(Ingredient.objects.get(name="graham crackers"))
meal.ingredients.add(Ingredient.objects.get(name="cream cheese"))
meal.ingredients.add(Ingredient.objects.get(name="white sugar"))
meal.ingredients.add(Ingredient.objects.get(name="vanilla extract"))
meal.ingredients.add(Ingredient.objects.get(name="milk"))
meal.ingredients.add(Ingredient.objects.get(name="eggs"))
meal.ingredients.add(Ingredient.objects.get(name="sour cream"))
meal.ingredients.add(Ingredient.objects.get(name="flour"))
meal.save()