import React, { useEffect, useState } from 'react';

import { getBestSellerBooks } from 'services/user/bookService';
import BookSection from '../book_section';

const BestSellerBooks  = () => {
    //
    const [books, setBooks] = useState([]);

    useEffect(() => {
        const fetchBooks = async () => {
            const data = await getBestSellerBooks();
            setBooks(data.data);
        };

        fetchBooks();
    }, []);

    return (
        <BookSection
            title="Sách siêu giảm giá"
            books={books}
            type="best-seller-book"
        />
    );
}

export default BestSellerBooks ;