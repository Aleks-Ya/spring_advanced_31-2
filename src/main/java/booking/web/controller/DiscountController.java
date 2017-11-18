package booking.web.controller;

import booking.beans.models.Event;
import booking.beans.models.User;
import booking.beans.services.DiscountService;
import booking.beans.services.EventService;
import booking.beans.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Aleksey Yablokov
 */
@Controller
@SuppressWarnings("unused")
@RequestMapping(value = DiscountController.ENDPOINT)
public class DiscountController {
    static final String ENDPOINT = "/discount";
    static final String DISCOUNT_KEY = "discount";
    private static final String DISCOUNT_FTL = "discount/discount";

    private final DiscountService discountService;

    private final UserService userService;

    private final EventService eventService;

    @Autowired
    public DiscountController(DiscountService discountService, UserService userService, EventService eventService) {
        this.discountService = discountService;
        this.userService = userService;
        this.eventService = eventService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getDiscount(@RequestParam Long userId, @RequestParam Long eventId, @ModelAttribute("model") ModelMap model) {
        User user = userService.getById(userId);
        Event event = eventService.getById(eventId);
        double discount = discountService.getDiscount(user, event);
        model.put(DISCOUNT_KEY, discount);
        return DISCOUNT_FTL;
    }
}
