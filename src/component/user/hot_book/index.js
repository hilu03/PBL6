import React, { useEffect, useState } from 'react';
import { getHotBooks } from 'services/user/bookService';

import BookSection from '../book_section';

const HotBook = () => {
    const [books, setBooks] = useState([]);

    useEffect(() => {
        const fetchBooks = async () => {
            const data = await getHotBooks();
            setBooks(data.data);
        };

        fetchBooks();
    }, []);

    return (
        <BookSection
            title="Hot Books"
            books={books}
            type="hot-book"
        />
    );
}

export default HotBook;