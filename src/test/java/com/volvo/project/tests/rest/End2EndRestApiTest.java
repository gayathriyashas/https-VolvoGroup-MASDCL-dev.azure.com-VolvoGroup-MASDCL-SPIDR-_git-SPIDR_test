package com.volvo.project.tests.rest;

import com.volvo.project.base.TestBase;
import com.volvo.project.components.rest.models.bookstore.Book;
import com.volvo.project.components.rest.requests.BookStoreRest;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("rest api implementation")
@Story("check book features by rest")
public class End2EndRestApiTest extends TestBase {
    private final String userID = "2be6930a-6689-48f1-b285-3742f0066aeb";
    private final String userName = "TOOLSQA-Test";
    private final String password = "Test@@123";

    @Test(groups = {"rest", "passAPI"})
    public void bookstoreRestTestSteps() {
        BookStoreRest bookStoreRest = new BookStoreRest();
        bookStoreRest.authorizeUser(userName, password);

        List<Book> availableBooks = bookStoreRest.getAvailableBooks();

        Book bookToAdd = step("Select book to add", () -> availableBooks.get(0));

        List<Book> usersBooks = step("Add book to users books if not exists", () -> {
            List<Book> usersBooksBeforeAddingBook = bookStoreRest.getUsersBooks(userID);
            if (usersBooksBeforeAddingBook.stream().noneMatch(book -> book.isbn.equals(bookToAdd.isbn))) {
                bookStoreRest.addBookToUsersList(userID, bookToAdd.isbn);
                List<Book> usersBooksAfterAddingBook = bookStoreRest.getUsersBooks(userID);
                return usersBooksAfterAddingBook;
            }
            return usersBooksBeforeAddingBook;
        });

        step(
                "Check if book is in users books list",
                () -> assertThat(usersBooks).usingElementComparatorOnFields("isbn").contains(bookToAdd)
        );

        bookStoreRest.removeBookFromUsersList(userID, bookToAdd.isbn);

        step(
                "Check if book is removed",
                () -> {
                    List<Book> usersBooksAfterRemoval = bookStoreRest.getUsersBooks(userID);
                    assertThat(usersBooksAfterRemoval).usingElementComparatorOnFields("isbn").doesNotContain(bookToAdd);
                }
        );
    }
}
