export const generateSlug = (name) => {
    if (typeof name !== 'string') {
      console.warn('Expected a string but received:', name);
      return ''; // Hoặc trả về một giá trị slug mặc định
    }
  
    return name
      .toLowerCase()
      .normalize('NFD') // Chuyển ký tự có dấu thành dạng tổ hợp
      .replace(/[\u0300-\u036f]/g, '') // Loại bỏ các dấu thanh
      .replace(/đ/g, 'd') // Chuyển đ thành d
      .replace(/[^a-z0-9\s-]/g, '') // Loại bỏ các ký tự đặc biệt
      .trim() // Loại bỏ khoảng trắng ở đầu và cuối chuỗi
      .replace(/\s+/g, '-') // Thay khoảng trắng bằng dấu gạch ngang
      .replace(/-+/g, '-'); // Loại bỏ các dấu gạch ngang dư thừa
};
  
export const findCategoryIdBySlug = (categories, slug) => {
    return categories.find(category => generateSlug(category.name) === slug)?.id
}

export const findBookIdBySlug = (book, slug) => {
    console.log(book)
    if (book) {
        return generateSlug(book.title) === slug ? book.id : null;
    }
    return null;
}