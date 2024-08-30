 object OrderService {
    def make (
              userService: UserService,
              discountForProuduct: DiscountService,
              bonusForPurchase: BonusesService,
               productIdIsValid: ProductService
            ): OrderService
 }