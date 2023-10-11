package test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import main.Main;
import main.entity.Transaction;
import main.entity.Player;
import main.service.WalletService;

public class WalletServiceTest {
    private WalletService walletService;

    @BeforeEach
    public void setUp() {
        walletService = new WalletService();
    }

    @Test
    public void testRegisterPlayer() {
        assertTrue(walletService.registerPlayer("newUser", "newPassword"));
        assertFalse(walletService.registerPlayer("newUser", "newPassword"));
    }

    @Test
    public void testAuth() {
        assertEquals(walletService.login("newUser", "newPassword"),walletService.login("newUser", "newPassword"));
        assertEquals(null, walletService.login("newUser", "newPassword"));
    }

    @Test
    public void testGetBalance() {
        assertTrue(walletService.registerPlayer("newUser", "newPassword"));
        Player loggedInPlayer = walletService.login("newUser", "newPassword");
        assertEquals(0.0, walletService.getBalance(loggedInPlayer));
    }


    @Test
    public void testCredit() {
        assertTrue(walletService.registerPlayer("newUser", "newPassword"));
        Player loggedInPlayer = walletService.login("newUser", "newPassword");
        walletService.creditPlayer(loggedInPlayer, "0", 100.0);
        assertEquals(100.0, walletService.getBalance(loggedInPlayer));
    }

    @Test
    public void testDebit() throws Exception {
        assertTrue(walletService.registerPlayer("newUser", "newPassword"));
        Player loggedInPlayer = walletService.login("newUser", "newPassword");
        assertTrue(walletService.creditPlayer(loggedInPlayer, "1", 100.0));
        assertFalse(walletService.debitPlayer(loggedInPlayer, "1", 111.0));

    }




}
