package booking.service.impl;

import booking.BaseServiceTest;
import booking.domain.Account;
import booking.domain.User;
import booking.exception.BookingException;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class AccountServiceImplTest extends BaseServiceTest {

    @Test
    public void create() {
        User user = to.createJohn();
        Account expAccount = accountService.getByUserId(user.getId());
        Account actAccount = accountService.getById(expAccount.getId());
        assertThat(actAccount, equalTo(expAccount));

        User user2 = to.createJohn();
        Account expAccount2 = accountService.getByUserId(user2.getId());
        Account actAccount2 = accountService.getById(expAccount2.getId());
        assertThat(actAccount2, equalTo(expAccount2));

        assertThat(actAccount, not(equalTo(actAccount2)));
    }

    @Test(expected = BookingException.class)
    public void useAlreadyHasAccount() {
        User user = to.createJohn();
        accountService.create(new Account(user, BigDecimal.valueOf(1000)));
        accountService.create(new Account(user, BigDecimal.valueOf(1000)));
    }

    @Test
    public void delete() {
        User user = to.createJohn();
        Account account = accountService.getByUserId(user.getId());
        assertThat(accountService.getById(account.getId()), equalTo(account));
        assertThat(account.getUser(), equalTo(user));

        accountService.delete(account.getId());
        assertThat(accountService.getAll(), emptyIterable());
    }

    @Test
    public void getAll() {
        assertThat(accountService.getAll(), emptyIterable());

        Account account1 = to.createAccount();
        Account account2 = to.createAccount();
        List<Account> accounts = accountService.getAll();

        assertThat(accounts, containsInAnyOrder(account1, account2));
    }

    @Test
    public void getById() {
        Account account = to.createAccount();
        assertThat(accountService.getById(account.getId()), equalTo(account));
    }

    @Test(expected = BookingException.class)
    public void getByIdNotExists() {
        int notExistsAccountId = 12345;
        assertNull(accountService.getById(notExistsAccountId));
    }

    @Test
    public void getByUserId() {
        Account account = to.createAccount();
        User user = account.getUser();
        assertThat(accountService.getByUserId(user.getId()), equalTo(account));
    }

    @Test
    public void withdrawal() {
        Account oldAccount = to.createAccount();
        BigDecimal withdrawalAmount = BigDecimal.valueOf(300);
        Account actAccount = accountService.withdrawal(oldAccount, withdrawalAmount);
        assertThat(actAccount.getAmount(), closeTo(BigDecimal.valueOf(9700), BigDecimal.ZERO));
    }

    @Test
    public void refill() {
        Account oldAccount = to.createAccount();
        BigDecimal refillAmount = BigDecimal.valueOf(300);
        Account actAccount = accountService.refill(oldAccount, refillAmount);
        assertThat(actAccount.getAmount(), closeTo(BigDecimal.valueOf(10300), BigDecimal.ZERO));
    }
}
