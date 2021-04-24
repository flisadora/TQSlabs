package book;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class Library {
    private final List<Book> store = new ArrayList<Book>();

    public Library() {
    }

    public void addBook(final Book book){
        store.add(book);
        System.out.println("Book added: "+ book.getTitle());
    }

    public List<Book> findBooks(final LocalDateTime from, final LocalDateTime to){
        Date date = Date.from(to.atZone(ZoneId.systemDefault()).toInstant());
        Calendar end = Calendar.getInstance();
        end.setTime(date);
        end.roll(Calendar.YEAR, 1);

        Date begin = Date.from(from.atZone(ZoneId.systemDefault()).toInstant());

        List<Book> booksFound = store.stream().filter(book -> {
            return begin.before(book.getPublished()) && end.getTime().after(book.getPublished());
        }).sorted(Comparator.comparing(Book::getPublished).reversed()).collect(Collectors.toList());

        System.out.println(booksFound);
        return booksFound;
    }
}
