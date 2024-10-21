export const ROUTERS = {
    USER: {
        HOME: "",
        HOT_BOOK: "/books/hot-book",
        BEST_SELLER_BOOK: "/books/best-seller-book",
        SIGNIN: "/login",
        REGISTER: "/register",
        PRODUCT_DETAILS: "/details/:id",
        CARTITEMS: "/cart-items",
        CONFIRMORDER: "/confirm-order/:id/:quantity",
        PRODUCT_LISTING: "/listing/:slug",
        PRODUCT_SEARCH: "/books",
    },

    ADMIN: {
        DASHBOARD: "/admin",
        ADDBOOK: "/admin/add-book",
        LISTBOOK: "/admin/list-book/:slug",
        LISTCATEGORY: "/admin/list-category",
        LISTORDER: "/admin/list-order",
    }
}