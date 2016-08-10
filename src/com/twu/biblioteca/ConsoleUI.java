package com.twu.biblioteca;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;


class ConsoleUI {

    private final LibraryUiWrapper books;
    private final LibraryUiWrapper movies;

    private final Menu menu;

    private final Scanner scanner;

    private boolean stopInputLoop = false;


    private class LibraryUiWrapper {
        private ILibrary library;
        private String itemKind;

        public LibraryUiWrapper(ILibrary library, String itemKind) {
            this.library = library;
            this.itemKind = itemKind;
        }

        public ILibrary getLibrary() {
            return library;
        }

        public String getItemKind() {
            return itemKind;
        }
    }


    ConsoleUI(InputStream input, SecureLibrary books, Library movies) {
        this.books = new LibraryUiWrapper(books, "book");
        this.movies = new LibraryUiWrapper(movies, "movie");
        this.scanner = new Scanner(input);
        this.menu = new Menu();
        buildMenu();
    }

    public ConsoleUI(SecureLibrary books, Library movies) {
        this(System.in, books, movies);
    }

    private void buildMenu() {
        menu.addOption(Labels.LIST_BOOKS, () -> listItemsFromLibrary(books))
        .addOptionThatRequiresAuthentication(Labels.CHECK_OUT_BOOK, () -> tryToCheckoutItemFromLibrary(books))
        .addOptionThatRequiresAuthentication(Labels.RETURN_BOOK, () -> tryToReturnItemFromLibrary(books))
        .addOption(Labels.LIST_MOVIES, () -> listItemsFromLibrary(movies))
        .addOption(Labels.CHECK_OUT_MOVIE, () -> tryToCheckoutItemFromLibrary(movies))
        .addOption(Labels.RETURN_MOVIE, () -> tryToReturnItemFromLibrary(movies))
        .addOptionThatIsHiddenWhenAuthenticated(Labels.LOGIN, this::tryToLogin)
        .addOptionThatRequiresAuthentication(Labels.LOGOUT, this::logoutAndShowMainMenu)
        .addOptionThatRequiresAuthentication(Labels.SHOW_USER_INFORMATION, this::showUserInformation)
        .addOption(Labels.LIST_ACCOUNTABILITIES_FOR_BOOKS, () -> showAccountabilityListForLibrary(books))
        .addOption(Labels.QUIT, this::quit);
    }

    private void showAccountabilityListForLibrary(LibraryUiWrapper uiLibrary) {
        Map<Integer, UserSession> accountabilities = uiLibrary.getLibrary().getAccountabilities();
        for(Integer itemId : accountabilities.keySet())
            showAccountabiliy(itemId, accountabilities.get(itemId));
    }

    private void showAccountabiliy(int itemId, UserSession accountability) {
        System.out.println("Item with ID " + (itemId+1) + " was checked out by " + accountability);
    }

    private void showUserInformation() {
        System.out.println(UserSession.getSession());
    }

    private void logoutAndShowMainMenu() {
        UserSession.logout();
        showMainMenu();
    }

    private void tryToLogin() {
        try {
            String libraryId = readLibraryId();
            String password = readPassword();
            UserSession.login(libraryId, password);
            showMainMenu();
        } catch(IllegalArgumentException | IllegalStateException ex) {
            showLoginError(ex.getMessage());
        }
    }

    private void showLoginError(String errorMsg) {
        System.out.println(errorMsg);
    }

    private String readPassword() {
        return readStringInputOfKind("Password");
    }

    private String readLibraryId() {
        return readStringInputOfKind("Library ID");
    }

    private String readStringInputOfKind(String inputKind) {
        demandInputOfKind(inputKind);
        return readLineFromScanner();
    }

    private String readLineFromScanner() {
        if(!scanner.hasNextLine())
            throw new IllegalStateException("No input was given.");
        return scanner.nextLine();
    }

    private void demandInputOfKind(String inputKind) {
        System.out.println(inputKind + ": ");
    }

    private void listItemsFromLibrary(LibraryUiWrapper uiLibrary) {
        System.out.print(uiLibrary.getLibrary().toString());
    }

    private void tryToCheckoutItemFromLibrary(LibraryUiWrapper uiLibrary) {
        String itemKind = uiLibrary.getItemKind();
        try {
            int input = readIdOfItemOfKind(itemKind);
            uiLibrary.getLibrary().checkoutItemWithId(input - 1);
            reportSuccessfulCheckoutOfItemOfKind(itemKind);
        } catch(IllegalStateException | IllegalArgumentException ex) {
            reportUnsuccessfulCheckoutOfItemOfKind(itemKind);
        }
    }

    private void reportSuccessfulCheckoutOfItemOfKind(String itemKind) {
        System.out.println(Labels.SUCCESSFUL_CHECKOUT.apply(itemKind));
    }

    private void reportUnsuccessfulCheckoutOfItemOfKind(String itemKind) {
        System.out.println();
        System.out.println(Labels.UNSUCCESSFUL_CHECKOUT.apply(itemKind));
    }

    private void tryToReturnItemFromLibrary(LibraryUiWrapper uiLibrary) {
        String itemKind = uiLibrary.getItemKind();
        try {
            int input = readIdOfItemOfKind(itemKind);
            uiLibrary.getLibrary().returnItemWithId(input - 1);
            reportSuccessfulReturnOfItemOfKind(itemKind);
        } catch(IllegalStateException | IllegalArgumentException ex) {
            reportUnsuccessfulReturnOfItemOfKind(itemKind);
        }
    }

    private void reportSuccessfulReturnOfItemOfKind(String itemKind) {
        System.out.println(Labels.SUCCESSFUL_RETURN.apply(itemKind));
    }

    private void reportUnsuccessfulReturnOfItemOfKind(String itemKind) {
        System.out.println();
        System.out.println(Labels.UNSUCCESSFUL_RETURN.apply(itemKind));
    }

    private int readIdOfItemOfKind(String itemKind) {
        demandIdOfItemOfKind(itemKind);
        return readIntegerFromScanner();
    }

    private int readIntegerFromScanner() {
        if(!scanner.hasNextLine())
            throw new IllegalStateException("No input was given.");
        return Integer.parseInt(scanner.nextLine());
    }

    private void demandIdOfItemOfKind(String itemKind) {
        System.out.print(Labels.SELECT_ITEMID.apply(itemKind));
    }

    public void show() {
        showWelcomeMessage();
        showMainMenu();
        while(!stopInputLoop) {
            readAndProcessInput();
        }
    }

    private void showWelcomeMessage() {
        System.out.println(Labels.WELCOME);
    }

    private void showMainMenu() {
        System.out.println();
        System.out.print(menu);
    }

    private void readAndProcessInput() {
        System.out.println();
        trySelectMenuOption();
    }

    private void trySelectMenuOption() {
        try {
            char optionId = readMenuOption();
            applyMenuOption(optionId);
        } catch(IOException | IllegalStateException ex) {
            showInvalidOptionMessage();
        }
    }

    private void showInvalidOptionMessage() {
        System.out.println(Labels.INVALID_OPTION);
    }

    private char readMenuOption() throws IOException {
        showPleaseSelectOptionMessage();
        char optionId = readMenuOptionIdFromScanner();
        return optionId;
    }

    private char readMenuOptionIdFromScanner() throws IOException {
        if(!scanner.hasNextLine())
            throw new IOException("Empty input.");
        String input = scanner.nextLine();
        if(input.length() != 1)
            throw new IOException("Input must consist of exactly one character.");
        return input.charAt(0);
    }

    private void showPleaseSelectOptionMessage() {
        System.out.print("Please select an option: ");
    }

    private void applyMenuOption(char optionId) throws IOException, IllegalStateException {
        menu.applyOptionWithId(optionId);
    }

    private void quit() {
        stopInputLoop = true;
    }

}
