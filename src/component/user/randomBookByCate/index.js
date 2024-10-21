import React, { useEffect, useState } from 'react';

import { getBookRandomByCategory } from 'services/user/bookService';
import BookSection from '../book_section';

const BookRandomByCate  = ({bookID}) => {
    //
    const [books, setBooks] = useState([]);

    useEffect(() => {
        const fetchBooks = async () => {
            const data = await getBookRandomByCategory(bookID);
            setBooks(data.data);
        };

        fetchBooks();
    }, [bookID]);

    return (
        <BookSection
            title="Sách cùng thể loại"
            books={books}
            type="randomBookByCate"
        />
    );
}

export default BookRandomByCate ;